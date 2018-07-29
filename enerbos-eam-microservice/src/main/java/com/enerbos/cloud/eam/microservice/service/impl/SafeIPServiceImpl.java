package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.SafeIP;
import com.enerbos.cloud.eam.microservice.repository.jpa.SafeIPRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.SafeIPDao;
import com.enerbos.cloud.eam.microservice.service.SafeIPService;
import com.enerbos.cloud.eam.vo.SafeIPVoForFilter;
import com.enerbos.cloud.eam.vo.SafeIPVoForList;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Enerbos on 2016/10/17.
 */

@Service
public class SafeIPServiceImpl implements SafeIPService {
    @Autowired
    private SafeIPRepository safeIPRepository;


    @Autowired
    private SafeIPDao safeIPDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<SafeIPVoForList> getIPList(SafeIPVoForFilter safeIPVoForFilter) {

        PageHelper.startPage(safeIPVoForFilter.getPageNum(),
                safeIPVoForFilter.getPageSize());

        String word = safeIPVoForFilter.getWord();
        List<String> words = new ArrayList<>();
        if (StringUtil.isNotEmpty(word)) {
            String[] wordss = word.split(" ");
            words = Arrays.asList(wordss);
        }
        safeIPVoForFilter.setWordsList(words);
        return new PageInfo<SafeIPVoForList>(safeIPDao.findIPs(safeIPVoForFilter));

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SafeIP saveIP(SafeIP safeIP) {
        return safeIPRepository.save(safeIP);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SafeIP updateIP(SafeIP safeIP) {

        if (safeIP != null) {
            if (StringUtils.hasLength(safeIP.getId())) {
                SafeIP safeIPOld = safeIPDao.findOne(safeIP.getId());
                if (safeIPOld == null) {
                    throw new EnerbosException("102", "对象不存在");
                }
                BeanUtils.copyProperties(safeIP, safeIPOld, "creator", "createDate", "productId");
                return safeIPRepository.save(safeIPOld);
            } else {
                throw new EnerbosException("101", "对象id不能为空");
            }
        } else {
            throw new EnerbosException("100", "对象不能为空");
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteIP(String ids[]) {
        for (String id : ids) {
            safeIPRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public SafeIP findIPetail(String id) {
        return safeIPDao.findOne(id);
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean checkIP(String ip, String orgId, String siteId, String prod) {
        SafeIP safeIP=safeIPDao.findOneByIp(ip,orgId,siteId,prod);
        if(safeIP!=null){
            return true;
        }
        return false;
    }
}
