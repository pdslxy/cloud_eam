package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceive;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialGoodsReceiveRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialGoodsReceiveDao;
import com.enerbos.cloud.eam.microservice.service.MaterialGoodsReceiveService;
import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForList;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017 Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年3月31日 下午3:58:51
 * @Description 物资接口实现
 */
@Service
public class MaterialGoodsReceiveServiceImpl implements
        MaterialGoodsReceiveService {

    @Autowired
    private MaterialGoodsReceiveRepository materialGoodsReceiveRepository;

    @Autowired
    private MaterialGoodsReceiveDao materialGoodsReceiveDao;

    Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
     * (non-Javadoc)
	 * 
	 * @see
	 * com.enerbos.cloud.eam.microservice.inv.service.impl.RoService#findRos
	 * (java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialGoodsReceiveVoForList> findGoodsReceives(
            MaterialGoodsReceiveVoForFilter materialGoodsReceiveVoForFilter) {

        PageHelper.startPage(materialGoodsReceiveVoForFilter.getPageNum(),
                materialGoodsReceiveVoForFilter.getPageSize());

        String word = materialGoodsReceiveVoForFilter.getWord();
        Map<String, Object> filters = null;
        try {
            filters = ReflectionUtils.reflectionModelToMap(materialGoodsReceiveVoForFilter);

            if (StringUtil.isNotEmpty(word)) {
                String[] words = word.split(" ");
                filters.put("words", words);
            }
        } catch (Exception e) {

            logger.error(
                    "---MaterialGoodsReceiveServiceImpl.findGoodsReceives---",
                    e);

            return null;
        }

        return new PageInfo<MaterialGoodsReceiveVoForList>(
                materialGoodsReceiveDao.findGoodsReceives(filters));
    }

    /*
     * (non-Javadoc)
     *
     * @see com.enerbos.cloud.eam.microservice.inv.service.impl.RoService#saveRo
     * (com.enerbos.cloud.eam.inv.vo.RoVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialGoodsReceive saveGoodsReceive(
            MaterialGoodsReceive materialGoodsReceive) {

        return materialGoodsReceiveRepository.save(materialGoodsReceive);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.RoService#updateRo
     * (com.enerbos.cloud.eam.inv.vo.RoVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialGoodsReceive updateGoodsReceive(
            @RequestBody MaterialGoodsReceive ro) {

        return saveGoodsReceive(ro);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.RoService#deleteRo
     * (java.lang.String[])
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteGoodsReceive(String ids[]) {

        for (String id : ids) {
            materialGoodsReceiveRepository.delete(id);
            materialGoodsReceiveRepository.deleteDetailByReciveId(id);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.enerbos.cloud.eam.microservice.inv.service.impl.RoService#
     * findRoDetail(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaterialGoodsReceive findGoodsreceiveById(String id) {
        return materialGoodsReceiveRepository.findOne(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see com.enerbos.cloud.eam.microservice.inv.service.impl.RoService#
     * updateGoodsReceiveStatus(java.lang.String)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateGoodsReceiveStatus(MaterialGoodsReceive materialGoodsReceive) {

        saveGoodsReceive(materialGoodsReceive);

    }

}
