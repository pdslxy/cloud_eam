package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.microservice.domain.UserGroupDomain;
import com.enerbos.cloud.eam.microservice.repository.jpa.UserGroupDomainRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.UserGroupDomainDao;
import com.enerbos.cloud.eam.microservice.service.UserGroupDomainService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.UserGroupDomainFilterVo;
import com.enerbos.cloud.eam.vo.UserGroupDomainVo;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/17.
 */

@Service
public class UserGroupDomainServiceImpl implements UserGroupDomainService {
    @Autowired
    private UserGroupDomainRepository userGroupDomainRepository;


    @Autowired
    private UserGroupDomainDao userGroupDomainDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<UserGroupDomainVo> getList(UserGroupDomainFilterVo filterVo) {
        if(StringUtil.isEmpty(filterVo.getOrgId())){
            filterVo.setOrgId(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
        }
        if(StringUtil.isEmpty(filterVo.getSiteId())){
            filterVo.setSiteId(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
        }
        PageHelper.startPage(filterVo.getPageNum(), filterVo.getPageSize());
        String word = filterVo.getKeyword();
        String sorts=filterVo.getSorts();
        Map<String, Object> filters=new HashMap<>();
        try{
            filters = EamCommonUtil.reModelToMap(filterVo);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(sorts)) {
            filters.put("sorts", sorts);
        }
        return new PageInfo<UserGroupDomainVo>(userGroupDomainDao.findUserGroupDimain(filters));
    }
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public UserGroupDomainVo save(UserGroupDomainVo vo) {
        UserGroupDomain userGroupDomain=new UserGroupDomain();
        BeanUtils.copyProperties(vo,userGroupDomain );
        userGroupDomain=userGroupDomainRepository.save(userGroupDomain);
        BeanUtils.copyProperties(userGroupDomain,vo);
        return vo;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(String ids[]) {
        for (String id : ids) {
            userGroupDomainRepository.delete(id);
        }
    }
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserGroupDomainVo findetail(String id){
        return  userGroupDomainDao.findOne(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public UserGroupDomainVo findUserGroupDomainByDomainValueAndDomainNum( String domainValue,String domainNum,String orgId,String siteId,String associationType) {
        Map<String, Object> filters=new HashMap<>();
        filters.put("domainValue",domainValue);
        filters.put("domainNum",domainNum);
        filters.put("associationType",associationType);
        if(StringUtil.isEmpty(orgId)){
            orgId="ALL";
        }
        if(StringUtil.isEmpty(siteId)){
            siteId="ALL";
        }
        filters.put("orgId",orgId);
        filters.put("siteId",siteId);

        return userGroupDomainDao.findUserGroupDomainByDomainValueAndDomainNum( filters);
    }

    /**
     * @param userGroupDomainVos
     */
    @Override
    public void saveBatchUserGroupDomain(List<UserGroupDomainVo> userGroupDomainVos) throws Exception {
        List<UserGroupDomain> userGroupDomains = new ArrayList<UserGroupDomain>();
        ReflectionUtils.copyProperties(userGroupDomainVos,userGroupDomains , new UserGroupDomain());
        userGroupDomainRepository.save(userGroupDomains);
    }
}
