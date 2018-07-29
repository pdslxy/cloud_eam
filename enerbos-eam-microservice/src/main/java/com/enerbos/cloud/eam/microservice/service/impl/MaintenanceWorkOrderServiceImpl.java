package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrder;
import com.enerbos.cloud.eam.microservice.domain.MaintenanceWorkOrderRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceWorkOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaintenanceWorkOrderRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceWorkOrderDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaintenanceWorkOrderRfCollectorDao;
import com.enerbos.cloud.eam.microservice.service.MaintenanceWorkOrderService;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ParamConstans;
import com.enerbos.cloud.util.ParamUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月05日
 * @Description EAM维保工单service
 */
@Service
public class MaintenanceWorkOrderServiceImpl implements MaintenanceWorkOrderService {

    private Logger logger = LoggerFactory.getLogger(MaintenanceWorkOrderServiceImpl.class);

    @Autowired
    private MaintenanceWorkOrderDao maintenanceWorkOrderDao;

    @Autowired
    private MaintenanceWorkOrderRepository maintenanceWorkOrderRepository;

    @Autowired
    private MaintenanceWorkOrderRfCollectorRepository maintenanceWorkOrderRfCollectorRepository;

    @Autowired
    private MaintenanceWorkOrderRfCollectorDao maintenanceWorkOrderRfCollectorDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceWorkOrderForCommitVo findWorkOrderCommitByWorkOrderNum(String woNum) {
        return maintenanceWorkOrderDao.findWorkOrderCommitByWorkOrderNum(woNum);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaintenanceWorkOrder save(MaintenanceWorkOrder maintenanceWorkOrder) {
        return maintenanceWorkOrderRepository.save(maintenanceWorkOrder);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceWorkOrder findWorkOrderByID(String id) {
        return maintenanceWorkOrderDao.findWorkOrderById(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MaintenanceWorkOrderForDetailVo> findWorkOrderByIDs(List<String> ids) {
        return maintenanceWorkOrderDao.findWorkOrderByIds(ids);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceWorkOrderForCommitVo findEamWorkOrderCommitByID(String id) {
        return maintenanceWorkOrderDao.findWorkOrderCommitByID(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceWorkOrderForAssignVo findEamWorkOrderAssignByID(String id) {
        return maintenanceWorkOrderDao.findWorkOrderAssignByID(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceWorkOrderForReportVo findEamWorkOrderReportByID(String id) {
        return maintenanceWorkOrderDao.findWorkOrderReportByID(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaintenanceWorkOrderForCheckAcceptVo findEamWorkOrderCheckAcceptByID(String id) {
        return maintenanceWorkOrderDao.findWorkOrderCheckAcceptByID(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteEamWorkOrderByIds(List<String> ids) {
        for (String id : ids) {
            maintenanceWorkOrderRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteEamWorkOrderById(String id) {
        maintenanceWorkOrderRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MaintenanceWorkOrderForListVo> findWorkOrderList(MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo) {
        PageHelper.startPage(maintenanceWorkOrderSelectVo.getPageNum(), maintenanceWorkOrderSelectVo.getPageSize());
        if (StringUtils.isNotBlank(maintenanceWorkOrderSelectVo.getSorts())) {
            String sorts = maintenanceWorkOrderSelectVo.getSorts();
            maintenanceWorkOrderSelectVo.setSorts(ParamUtils.convertSortParam(new MaintenanceWorkOrder(), sorts).get(ParamConstans.SUCCESS).toString());
        }
        return maintenanceWorkOrderDao.findEamWorkOrder(maintenanceWorkOrderSelectVo);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<MaintenanceWorkOrderForListVo> findPageWorkOrderByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo) {
        PageHelper.startPage(maintenanceForAssetFilterVo.getPageNum(), maintenanceForAssetFilterVo.getPageSize());
        return maintenanceWorkOrderDao.findPageWorkOrderByAssetId(maintenanceForAssetFilterVo);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Double findWorkOrderSingleAssetLastTimeById(String id) {
        return maintenanceWorkOrderDao.findWorkOrderSingleAssetLastTimeById(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void collectWorkOrder(List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList) {
        if (maintenanceWorkOrderRfCollectorVoList == null || maintenanceWorkOrderRfCollectorVoList.isEmpty()) {
            return;
        }
        //过滤重复数据
        Map<String, List<MaintenanceWorkOrderRfCollectorVo>> map = maintenanceWorkOrderRfCollectorVoList.stream()
                .filter(vo -> Objects.nonNull(vo.getWorkOrderId()))
                .filter(vo -> Objects.nonNull(vo.getPersonId()))
                .collect(Collectors.groupingBy(o -> String.format("%s_%s", o.getWorkOrderId(), o.getPersonId()), Collectors.toList()));

        List<MaintenanceWorkOrderRfCollector> list = map.entrySet().stream().map(entry -> entry.getValue().get(0)).map(vo -> new MaintenanceWorkOrderRfCollector(vo.getWorkOrderId(), vo.getPersonId(), vo.getProduct())).collect(Collectors.toList());
        list.stream().filter(o -> 0 == maintenanceWorkOrderRfCollectorDao.checkIsCollected(o.getWorkOrderId(), o.getPersonId(), o.getProduct())).forEach(maintenanceWorkOrderRfCollectorRepository::save);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void cancelCollectWorkOrder(List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList) {
        if (maintenanceWorkOrderRfCollectorVoList == null || maintenanceWorkOrderRfCollectorVoList.isEmpty()) {
            return;
        }
        Map<String, List<MaintenanceWorkOrderRfCollectorVo>> map = maintenanceWorkOrderRfCollectorVoList.stream().collect(Collectors.groupingBy(MaintenanceWorkOrderRfCollectorVo::getPersonId, Collectors.toList()));
        for (Map.Entry<String, List<MaintenanceWorkOrderRfCollectorVo>> entry : map.entrySet()) {
            List<MaintenanceWorkOrderRfCollector> list = maintenanceWorkOrderRfCollectorDao.findWorkOrderRfCollectorByPersonId(entry.getKey());

            if (!ObjectUtils.isEmpty(list)) {
                Map<String, String> collectedIdMap = entry.getValue().stream().collect(Collectors.toMap(MaintenanceWorkOrderRfCollectorVo::getWorkOrderId, MaintenanceWorkOrderRfCollectorVo::getPersonId));

                list.stream().filter(o -> collectedIdMap.containsKey(o.getWorkOrderId())).forEach(o -> maintenanceWorkOrderRfCollectorRepository.delete(o.getId()));
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List   findCountWorkOrder(String startDate, String endDate, String siteId) {
        List  list = new ArrayList<>();
        Map map = new HashMap() ;
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("siteId",siteId);
        list = maintenanceWorkOrderDao.findCountWorkOrder(map);
        return list;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List  findCountUndoneWorkOrder(String startDate, String endDate, String siteId) {
        List  list = new ArrayList<>();
        Map map = new HashMap() ;
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("siteId",siteId);
        list = maintenanceWorkOrderDao.findCountUndoneWorkOrder(map);
        return list;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public OrderCountBySiteVo findCountByStatus(String orgId, String siteId, Date startDate, Date endDate) {
        OrderCountBySiteVo workOrder = maintenanceWorkOrderDao.findCountByStatus(orgId, siteId, startDate, endDate);

        return workOrder;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Map<String, Object> findCountAndRingratio(Map<String, String> map) {

        Map<String, Object> workOrder = maintenanceWorkOrderDao.findCountAndRingratio(map);

        return workOrder;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<OrderMaxCountVo> findMaxCountOrder(String orgId, String startDate, String endDate) {
        Map<String, String> param = new HashMap<>();
        param.put("orgId", orgId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return maintenanceWorkOrderDao.findMaxCountOrder(param);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<DashboardOrderCountVo> findWorkOrderTotal(String orgId, Date startDate, Date endDate) {
        Map<String, Object> param = new HashMap<>();
        param.put("orgId", orgId);
        param.put("startDate", startDate);
        param.put("endDate", endDate);
        return maintenanceWorkOrderDao.findWorkOrderTotal(param);
    }


}