package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.MaterialStoreRoom;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialStoreRoomRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialInventoryDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialStoreRoomDao;
import com.enerbos.cloud.eam.microservice.service.MaterialStoreRoomService;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForList;
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
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
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
public class MaterialStoreRoomServiceImpl implements MaterialStoreRoomService {

    @Autowired
    private MaterialStoreRoomRepository storeRoomRepostory;

    @Autowired
    private MaterialStoreRoomDao storeRoomDao;

    @Autowired
    private MaterialInventoryDao materialInventoryDao;

    Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
     * (non-Javadoc)
	 * 
	 * @see
	 * com.enerbos.cloud.eam.microservice.inv.service.impl.StoreRoomService#
	 * findStoreRooms (java.lang.String, java.util.Map, java.lang.Integer,
	 * java.lang.Integer)
	 */

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialStoreRoomVoForList> findStoreRooms(
            MaterialStoreRoomVoForFilter materialStoreRoomVoForFilter) {

        PageHelper.startPage(materialStoreRoomVoForFilter.getPageNum(),
                materialStoreRoomVoForFilter.getPageSize());

        String word = materialStoreRoomVoForFilter.getWord();

        Map<String, Object> filters = null;
        try {
            filters = ReflectionUtils.reflectionModelToMap(materialStoreRoomVoForFilter);
            if (StringUtil.isNotEmpty(word)) {
                String[] words = word.split(" ");
                filters.put("words", words);
            }
        } catch (Exception e) {
            logger.error(
                    "--------MaterialStoreRoomServiceImpl.findStoreRooms---------",
                    e);
        }
        return new PageInfo<MaterialStoreRoomVoForList>(
                storeRoomDao.findStoreRooms(filters));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.StoreRoomService#
     * saveStoreRoom (com.enerbos.cloud.eam.inv.vo.StoreRoomVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialStoreRoom saveStoreRoom(
            @RequestBody MaterialStoreRoom storeRoom) {

        if (storeRoom.getIsdefault()) {
            storeRoomRepostory.updateDefault() ;
        }

        return storeRoomRepostory.save(storeRoom);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.StoreRoomService#
     * updateStoreRoom (com.enerbos.cloud.eam.inv.vo.StoreRoomVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void changeStroeRoomStatus(String[] ids, String status) {

        for (String id : ids) {

            MaterialStoreRoom materialStoreRoom = this.findStoreRoomDetail(id);
            if (materialStoreRoom != null) {
                materialStoreRoom.setStatus(status);
                this.saveStoreRoom(materialStoreRoom);
            } else {
                new EnerbosException(
                        HttpStatus.INTERNAL_SERVER_ERROR.toString(), "id不存在");
            }
        }

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.StoreRoomService#
     * deleteStoreRoom (java.lang.String[])
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteStoreRoom(String ids[]) {

        for (String id : ids) {

            if (materialInventoryDao.findStoreRoomInInventroy(id).size() > 0) {
                throw new RuntimeException("所选库房已经应用不能删除!!");
            }
            storeRoomRepostory.delete(id);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.StoreRoomService#
     * findStoreRoomDetail(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaterialStoreRoom findStoreRoomDetail(String id) {
        return storeRoomRepostory.findOne(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.StoreRoomService#
     * updateStoreRoom()
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialStoreRoom updateStoreRoom(MaterialStoreRoom materialStoreRoom) {

        return storeRoomRepostory.save(materialStoreRoom);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialStoreRoomVoForList> findUsableStoreRoom(String itemNum, String orgId, String siteId, Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("itemNum", itemNum);
        filter.put("orgId", orgId);
        filter.put("siteId", siteId);
        return new PageInfo<MaterialStoreRoomVoForList>(storeRoomDao.findUsableStoreRoom(filter));
    }

    @Override
    public boolean findhasdefault(String siteId, String orgId) {
        Map<String, Object> filter = new HashMap<String, Object>();
        filter.put("orgId", orgId);
        filter.put("siteId", siteId);

        List list = storeRoomDao.findhasdefault(filter);

        return list.size() > 0 ? true : false;
    }
}
