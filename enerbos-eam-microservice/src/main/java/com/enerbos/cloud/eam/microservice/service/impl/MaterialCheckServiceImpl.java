package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.MaterialCheck;
import com.enerbos.cloud.eam.microservice.domain.MaterialCheckDetail;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialCheckDetailRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialCheckRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialInventoryRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialCheckDao;
import com.enerbos.cloud.eam.microservice.service.MaterialCheckService;
import com.enerbos.cloud.eam.vo.MaterialCheckDetailVoForList;
import com.enerbos.cloud.eam.vo.MaterialCheckVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialCheckVoForInventoryList;
import com.enerbos.cloud.eam.vo.MaterialCheckVoForList;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年7月14日 下午8:15:08
 * @Description
 */
@Service
public class MaterialCheckServiceImpl implements MaterialCheckService {

    @Autowired
    private MaterialCheckRepository materialCheckRepository;

    @Autowired
    private MaterialCheckDetailRepository materialCheckDetailRepository;

    @Autowired
    private MaterialCheckDao materialCheckDao;

    @Autowired
    private MaterialInventoryRepository materialInventoryRepository ;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialCheckVoForList> findMaterialCheck(MaterialCheckVoForFilter materialCheckVoForFilter) {

        PageHelper.startPage(materialCheckVoForFilter.getPageNum(), materialCheckVoForFilter.getPageSize());
        String word = materialCheckVoForFilter.getWord();
        Map<String, Object> filter = null;
        try {
            filter = ReflectionUtils.reflectionModelToMap(materialCheckVoForFilter);

            if (StringUtil.isNotEmpty(word)) {
                String[] words = word.split(" ");
                filter.put("words", words);
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return new PageInfo<MaterialCheckVoForList>(materialCheckDao.findMaterialCheck(filter));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialCheck saveMaterialCheck(MaterialCheck materialCheck) {
        return materialCheckRepository.save(materialCheck);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteMaterialCheck(String[] ids) {

        for (String id : ids) {
            materialCheckRepository.delete(id);
            materialCheckDetailRepository.deleteDetailBycheckId(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaterialCheck findMaterialCheckById(String id) {
        return materialCheckRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteMaterialCheckDetail(String[] ids) {

        for (String id : ids) {
            materialCheckDetailRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateMaterialCheckStatus(String[] ids, String status) {

        for (String id : ids) {
            MaterialCheck check = this.findMaterialCheckById(id);
            String statueLast = check.getStatus() ;
            if (check != null) {

//                logger.info("========{}",status);
                if(!check.getStatus().equals("WC")){
                    check.setStatus(status);
                    this.saveMaterialCheck(check);
                }

//                logger.info("========{},{},{}",status,statueLast,"WC".equals(status) && !statueLast.equals("WC"));

                if("WC".equals(status) && !statueLast.equals("WC")){
                    List<MaterialCheckDetailVoForList> materialCheckDetailVoForLists = materialCheckDao.findMaterialCheckDetail(id);
                    logger.info("=======///={}",materialCheckDetailVoForLists);
                    for (MaterialCheckDetailVoForList materialCheckDetailVoForList :materialCheckDetailVoForLists ){
                        String num = materialCheckDetailVoForList.getPhysicalInventory();
                        materialInventoryRepository.updateInventoryCheckByIntoryId(materialCheckDetailVoForList.getInventoryId(),Long.parseLong(num)) ;
                    }
                }

            } else {
                new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "无此id对应的记录");
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialCheckDetailVoForList> findMaterialCheckDetail(String id, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);


        return new PageInfo<MaterialCheckDetailVoForList>(materialCheckDao.findMaterialCheckDetail(id));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<MaterialCheckDetail> saveMaterialCheckDetail(List<MaterialCheckDetail> materialCheckDetails) {
        return materialCheckDetailRepository.save(materialCheckDetails);
    }

    @Override
    public PageInfo<MaterialCheckVoForInventoryList> findMaterialCheckByInvtoryId(String id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<MaterialCheckVoForInventoryList>(materialCheckDao.findMaterialCheckByInvtoryId(id));
    }

    @Override
    public MaterialCheckDetail findMaterialCheckDetailById(String id) {
        return materialCheckDetailRepository.findOne(id);
    }
}
