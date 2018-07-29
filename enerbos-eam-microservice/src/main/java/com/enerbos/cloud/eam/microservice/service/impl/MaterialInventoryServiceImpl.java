package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceive;
import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceiveDetail;
import com.enerbos.cloud.eam.microservice.domain.MaterialInventory;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialGoodsReceiveDetailRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialGoodsReceiveRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialInventoryRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialReleaseRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialInventoryDao;
import com.enerbos.cloud.eam.microservice.service.MaterialInventoryService;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForList;
import com.enerbos.cloud.eam.vo.MaterialInventoryVoForStoreroomList;
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

import java.util.HashMap;
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
public class MaterialInventoryServiceImpl implements MaterialInventoryService {

    @Autowired
    private MaterialInventoryRepository materialInventoryRepository;

    @Autowired
    private MaterialGoodsReceiveRepository materialGoodsReceiveRepository;

    @Autowired
    private MaterialGoodsReceiveDetailRepository materialGoodsReceiveDetailRepository;

    @Autowired
    private MaterialInventoryDao materialInventoryDao;

    @Autowired
    private MaterialReleaseRepository materialReleaseRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
     * (non-Javadoc)
	 * 
	 * @see
	 * com.enerbos.cloud.eam.microservice.service.implentoryService#
	 * findInventorys (java.lang.String, java.util.Map, java.lang.Integer,
	 * java.lang.Integer)
	 */

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialInventoryVoForList> findInventorys(
            MaterialInventoryVoForFilter materialInventoryVoForFilter) {
        PageHelper.startPage(materialInventoryVoForFilter.getPageNum(),
                materialInventoryVoForFilter.getPageSize());

        String word = materialInventoryVoForFilter.getWord();

        Map<String, Object> filters = null;
        try {
            filters = ReflectionUtils.reflectionModelToMap(materialInventoryVoForFilter);

            if (StringUtil.isNotEmpty(word)) {

                String[] words = word.split(" ");

                filters.put("words", words);
            }
        } catch (Exception e) {
            logger.error("---InventoryServiceImpl.findInventorys---", e);
        }

        return new PageInfo<MaterialInventoryVoForList>(
                materialInventoryDao.findInventorys(filters));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.implentoryService#
     * saveInventory (com.enerbos.cloud.eam.voentoryVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialInventory saveInventory(MaterialInventory inventory) {


        return materialInventoryRepository.save(inventory);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.materialInventoryReorder#
     * materialInventoryReorder (com.enerbos.cloud.eam.voentoryVo)
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void materialInventoryReorder(MaterialInventory inventory, String goodsReceiveNum) {
        long currentBalance = inventory.getCurrentBalance();
        long reorderPoint = 0;
        if (inventory.getReorderPoint() != null) {
            reorderPoint = Long.valueOf(inventory.getReorderPoint());
        }

        if (currentBalance < reorderPoint) {
            MaterialGoodsReceive materialGoodsReceive = new MaterialGoodsReceive();
            materialGoodsReceive.setGoodsReceiveNum(goodsReceiveNum);
            materialGoodsReceive.setChangeUser(inventory.getCreateUser());
            materialGoodsReceive.setDescription("重订购");
            materialGoodsReceive.setStoreroomId(inventory.getStoreroomId());
//            materialGoodsReceive.setReceiveType();
            MaterialGoodsReceive materialGoodsReceiveSave = materialGoodsReceiveRepository.save(materialGoodsReceive);

            MaterialGoodsReceiveDetail materialGoodsReceiveDetail = new MaterialGoodsReceiveDetail();
            materialGoodsReceiveDetail.setItemId(inventory.getItemId());
            materialGoodsReceiveDetail.setGoodsReceiveId(materialGoodsReceiveSave.getId());
            materialGoodsReceiveDetail.setAverageCost(inventory.getAverageCost());
            materialGoodsReceiveDetail.setreceiveQuantity(Long.valueOf(inventory.getEconomicOrderQuantity()));
            materialGoodsReceiveDetail.setReceiveUnit(inventory.getOrderUnit());

            materialGoodsReceiveDetailRepository.save(materialGoodsReceiveDetail);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialInventoryVoForList> findInventorysNotInCheck(String[] ids, String storeroomid, Integer pageNum, Integer pageSize, String siteId, String orgId) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> filters = new HashMap();
        if (ids.length > 0) {
            filters.put("ids", ids);
        }
        filters.put("storeroomid", storeroomid);
        filters.put("siteId", siteId);
        filters.put("orgId", orgId);
        return new PageInfo<MaterialInventoryVoForList>(
                materialInventoryDao.findInventorysNotInCheck(filters));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialInventoryVoForList> findInventorysNotInItemNum(String storeroomId, String[] itemNums, Integer pageNum, Integer pageSize, String siteId, String orgId) {

      //  MaterialRelease materialRelease = materialReleaseRepository.findOne(releaseId);


        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> filters = new HashMap<String, Object>();
        if (itemNums.length > 0) {
            filters.put("itemNums", itemNums);
        }
        if (storeroomId != null) {
            filters.put("storeroomId", storeroomId);
        }

        filters.put("siteId", siteId);
        filters.put("orgId", orgId);

        return new PageInfo<MaterialInventoryVoForList>(
                materialInventoryDao.findInventorys(filters));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaterialInventory findInventorysByItemIdAndStoreroomId(String itemid, String storeroomId) {
        return materialInventoryDao.findInventorysByItemIdAndStoreroomId(itemid,storeroomId);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.implentoryService#
     * updateInventory (com.enerbos.cloud.eam.voentoryVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialInventory updateInventory(MaterialInventory inventory) {

        return saveInventory(inventory);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.implentoryService#
     * deleteInventory (java.lang.String[])
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteInventory(String ids[]) {

        for (String id : ids) {
            materialInventoryRepository.delete(id);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.implentoryService#
     * findInventoryDetail(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaterialInventory findInventoryDetail(String id) {
        return materialInventoryRepository.findOne(id);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.implentoryService#
     * updateInventoryStatus(com.enerbos.cloud.eam.voentoryVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialInventory updateInventoryStatus(
            @RequestBody MaterialInventory inventory) {

        return saveInventory(inventory);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.implentoryService#
     * findItemsByinventory (java.util.Map,java.lang.String,java.lang.Integer,
     * java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialInventoryVoForList> findInventorysByItemId(
            String id, Integer pageNum, Integer pageSize,String siteId,String orgId) {

        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("itemid", id);
        filters.put("siteId", siteId);
        filters.put("orgId", orgId);
        //filters.put("status",true) ;

        return new PageInfo<MaterialInventoryVoForList>(
                materialInventoryDao.findInventorys(filters));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.implentoryService#
     * findInventorysNotInItems (java.lang.String,java.lang.Integer,
     * java.lang.Integer)
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialInventoryVoForList> findInventorysNotInItems(
            String[] ids, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> filters = new HashMap<String, Object>();
        filters.put("itemids", ids);
       // filters.put("status",true);
        return new PageInfo<MaterialInventoryVoForList>(
                materialInventoryDao.findInventorys(filters));
    }


    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.service.implentoryService#
     * updateInventoryCurrentBalance (java.lang.String,java.lang.String,
     * java.lang.Long)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateInventoryCurrentBalanceByItemId(String itemId, long quantity) {
        materialInventoryRepository.updateInventoryCurrentBalanceByItemId(itemId, quantity);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateInventoryCurrentBalanceByIntoryId(String inventoryId, long quantity) {
        materialInventoryRepository.updateInventoryCurrentBalanceByIntoryId(inventoryId, quantity);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialInventoryVoForStoreroomList> findInventorysByStoreroomId(String id, Integer pageNum, Integer pageSize,String siteId,String orgId) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<MaterialInventoryVoForStoreroomList>(
                materialInventoryDao.findInventorysByStoreroomId(id,siteId,orgId));
    }

}
