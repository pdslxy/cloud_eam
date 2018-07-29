package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MaterialRelease;
import com.enerbos.cloud.eam.microservice.domain.MaterialReleaseDetail;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialReleaseDetailRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialReleaseRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialReleaseDao;
import com.enerbos.cloud.eam.microservice.service.MaterialReleaseService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MaterialReleaseServiceImpl implements MaterialReleaseService {

    @Autowired
    private MaterialReleaseRepository materialReleaseRepository;

    @Autowired
    private MaterialReleaseDetailRepository materialReleaseDetailRepository;

    @Autowired
    private MaterialReleaseDao materialReleaseDao;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialReleaseVoForList> findMaterialRelease(
            MaterialReleaseVoForFilter materialReleaseVoForFilter) {

        PageHelper.startPage(materialReleaseVoForFilter.getPageNum(),
                materialReleaseVoForFilter.getPageSize());

        String word = materialReleaseVoForFilter.getWord();
        Map<String, Object> filter = null;
        try {
            filter = ReflectionUtils.reflectionModelToMap(materialReleaseVoForFilter);
            if (StringUtil.isNotEmpty(word)) {
                String[] words = word.split(" ");
                filter.put("words", words);
            }
        } catch (Exception e) {
            logger.error("MaterialReleaseServiceImpl.findMaterialRelease", e);
        }

        return new PageInfo<MaterialReleaseVoForList>(
                materialReleaseDao.findMaterialRelease(filter));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialRelease saveMaterialRelease(MaterialRelease invuse) {
        return materialReleaseRepository.save(invuse);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteMaterialRelease(String[] ids) {
        for (String id : ids) {
            materialReleaseRepository.delete(id);
            materialReleaseDetailRepository.deleteDetailByReleaseId(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaterialRelease findMaterialReleaseById(String id) {
        return materialReleaseRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialReleaseDetailVoForList> findMaterialReleaseDetail(
            MaterialReleaseDetailVoForFilter materialReleaseDetailVoForFilter) {
        PageHelper.startPage(materialReleaseDetailVoForFilter.getPageNum(),
                materialReleaseDetailVoForFilter.getPageSize());
        Map<String, Object> filter = null;
        try {
            filter = ReflectionUtils.reflectionModelToMap(materialReleaseDetailVoForFilter);
        } catch (Exception e) {
            logger.error("-findMaterialReleaseDetail---", e);
        }
        return new PageInfo<MaterialReleaseDetailVoForList>(
                materialReleaseDao.findMaterialReleaseDetail(filter));
    }

    @Override
    public List<MaterialReleaseDetail> saveMaterialReleaseDetail(List<MaterialReleaseDetail> materialReleaseDetailVoList) {

        return materialReleaseDetailRepository.save(materialReleaseDetailVoList);
    }

    @Override
    public void deleteMaterialReleaseDetail(String[] ids) {

        for (String id : ids) {
            materialReleaseDetailRepository.delete(id);
        }

    }

    @Override
    public MaterialReleaseDetail findMaterialReleaseDetailById(String id) {

        return materialReleaseDetailRepository.findOne(id);
    }

    @Override
    public PageInfo<MaterialInventoryVoForReleaseList> findItemInReleaseByorderId(String id, Integer pageSize, Integer pageNum) {

        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<MaterialInventoryVoForReleaseList>(
                materialReleaseDao.findItemInReleaseByorderId(id));
    }

}
