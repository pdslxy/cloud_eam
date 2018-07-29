package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MaterialItem;
import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialItemRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialInventoryDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialItemDao;
import com.enerbos.cloud.eam.microservice.service.MaterialItemService;
import com.enerbos.cloud.eam.vo.MaterialItemVo;
import com.enerbos.cloud.eam.vo.MaterialItemVoForAssertList;
import com.enerbos.cloud.eam.vo.MaterialItemVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialItemVoForList;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MaterialItemServiceImpl implements MaterialItemService {

    @Autowired
    private MaterialItemRepository itemRepostory;

    @Autowired
    private MaterialInventoryDao materialInventoryDao;

    @Autowired
    private MaterialItemDao itemDao;

    @Autowired
    private MaterialRfCollectorRepository materialRfCollectorRepository;

    Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
     * (non-Javadoc)
	 * 
	 * @see
	 * com.enerbos.cloud.eam.microservice.inv.service.impl.ItemService#findItems
	 * (java.lang.String, java.util.Map, java.lang.Integer, java.lang.Integer)
	 */

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialItemVoForList> findItems(MaterialItemVoForFilter materialItemVoForFilter) {
        PageHelper.startPage(materialItemVoForFilter.getPageNum(),
                materialItemVoForFilter.getPageSize());

        String word = materialItemVoForFilter.getWord();
        Map<String, Object> filters = null;
        try {
            filters = ReflectionUtils.reflectionModelToMap(materialItemVoForFilter);
            if (StringUtil.isNotEmpty(word)) {
                String[] words = word.split(" ");
                filters.put("words", words);
            }
        } catch (Exception e) {

            logger.error("--------ItemServiceImpl.findItems---------", e);
        }
        return new PageInfo<MaterialItemVoForList>(itemDao.findItems(filters));
    }

	/*
     * (non-Javadoc)
	 * 
	 * @see com.enerbos.cloud.eam.microservice.inv.service.impl.ItemService#
	 * findItemsByinventory(java.lang.String, java.lang.Integer,
	 * java.lang.Integer)
	 */

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialItemVo> findItemsByinventory(String inventoryId,
                                                         Integer pageNum, Integer pageSize) {

        return null;

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.ItemService#saveItem
     * (com.enerbos.cloud.eam.inv.vo.ItemVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialItem saveItem(@RequestBody MaterialItem item) {

        return itemRepostory.save(item);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.ItemService#updateItem
     * (com.enerbos.cloud.eam.inv.vo.ItemVo)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialItem updateItem(MaterialItem item) {

        return saveItem(item);

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.ItemService#deleteItem
     * (java.lang.String[])
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteItem(String ids[]) {

        for (String id : ids) {
            if (materialInventoryDao.isItemInUse(id).size() > 0) {
                throw new RuntimeException("所选物资台帐，已经入库不能删除!!");
            }
            itemRepostory.delete(id);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see com.enerbos.cloud.eam.microservice.inv.service.impl.ItemService#
     * findItemDetail(java.lang.String)
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaterialItem findItemDetail(String id) {
        return itemRepostory.findOne(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialItemVoForList> findItemsNotInResevies(@Param("itemNums") String[] itemNums, Integer pageSize, Integer pageNum, String siteId, String orgId) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> filters = new HashMap<String, Object>();
        if (itemNums.length > 0) {
            filters.put("itemNums", itemNums);
        }
        filters.put("siteId", siteId);
        filters.put("orgId", orgId);
        return new PageInfo<MaterialItemVoForList>(itemDao.findItemsNotInResevies(filters));

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialItemVoForAssertList> findItemByAssertId(String[] itemIds, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        Map<String, Object> filters = new HashMap<String, Object>();
        if (itemIds.length > 0) {
            filters.put("itemIds", itemIds);
        }
        return new PageInfo<MaterialItemVoForAssertList>(itemDao.findItemByAssertId(filters));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MaterialItemVo> findByIdAndQrCodeNumAndSiteId(String[] id, String qrCodeNum, String siteId) {
        return itemDao.findByIdAndQrCodeNumAndSiteId(id, qrCodeNum, siteId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public boolean updateIsupdatedata(String id, String siteId, boolean b) {
        try {
            itemDao.updateIsupdatedata(id, siteId, b);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void collect(String id, String eamProdId, String type, String personId) {

        MaterialRfCollector pc = new MaterialRfCollector();
        pc.setCollectId(id);
        pc.setPersonId(personId);
        pc.setProduct(eamProdId);
        pc.setType(type);
        pc.setCreateUser(personId);
        materialRfCollectorRepository.save(pc);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void cancelCollect(String id, String eamProdId, String type, String personId) {
        materialRfCollectorRepository.deleteByCollectIdAndProductAndTypeAndPersonId(id, eamProdId, type, personId);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialRfCollector findcollect(String id, String item, String eamProdId) {
        return materialRfCollectorRepository.findByCollectIdAndTypeAndProduct(id,item,eamProdId);
    }

}
