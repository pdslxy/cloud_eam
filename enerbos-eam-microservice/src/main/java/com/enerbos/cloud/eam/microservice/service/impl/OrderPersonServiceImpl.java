package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.OrderPerson;
import com.enerbos.cloud.eam.microservice.repository.jpa.OrderPersonRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.OrderPersonDao;
import com.enerbos.cloud.eam.microservice.service.OrderPersonService;
import com.enerbos.cloud.eam.vo.OrderPersonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-15 20:23
 * @Description EAM工单关联人员接口
 */
@Service
public class OrderPersonServiceImpl implements OrderPersonService {

    @Autowired
    private OrderPersonDao orderPersonDao;

    @Autowired
    private OrderPersonRepository orderPersonRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OrderPersonVo> findListByFilter(OrderPersonVo orderPersonVo) {
        return orderPersonDao.findListByFilter(orderPersonVo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteOrderPersonById(String id) {
        orderPersonRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteOrderPersonByOrderIdAndFieldType(String orderId, String fieldType) {
        OrderPersonVo vo = new OrderPersonVo();
        vo.setOrderId(orderId);
        vo.setFieldType(fieldType);
        List<OrderPersonVo> result = orderPersonDao.findListByFilter(vo);
        if (Objects.nonNull(result) && !result.isEmpty()) {
            result.forEach(orderPersonVo -> orderPersonRepository.delete(orderPersonVo.getId()));
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteOrderPersonByOrderId(String orderId) {
        OrderPersonVo vo = new OrderPersonVo();
        vo.setOrderId(orderId);
        List<OrderPersonVo> result = orderPersonDao.findListByFilter(vo);
        if (Objects.nonNull(result) && !result.isEmpty()) {
            result.forEach(orderPersonVo -> orderPersonRepository.delete(orderPersonVo.getId()));
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void addOrderPerson(List<OrderPersonVo> orderPersonVoList) {
        if (Objects.isNull(orderPersonVoList) || orderPersonVoList.isEmpty()) {
            return;
        }

        OrderPerson orderPerson;
        for (OrderPersonVo orderPersonVo : orderPersonVoList) {
            orderPerson = new OrderPerson();

            orderPerson.setOrderId(orderPersonVo.getOrderId());
            orderPerson.setFieldType(orderPersonVo.getFieldType());
            orderPerson.setPersonId(orderPersonVo.getPersonId());
            orderPersonRepository.save(orderPerson);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<OrderPersonVo> updateOrderPersonByOrderIdAndFieldType(List<OrderPersonVo> orderPersonVoList) {
        if (Objects.isNull(orderPersonVoList) || orderPersonVoList.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, List<OrderPersonVo>> groupOrderPersonMap = orderPersonVoList.stream().collect(Collectors.groupingBy((OrderPersonVo orderPersonVo) -> String.format("%s###%s", orderPersonVo.getOrderId(), orderPersonVo.getFieldType()), Collectors.toList()));

        String[] keyArray;
        List<OrderPersonVo> voList, addList = new ArrayList<>(), oldDataList = new ArrayList<>();
        for (String key : groupOrderPersonMap.keySet()) {
            keyArray = key.split("###");

            List<OrderPersonVo> result = orderPersonDao.findListByFilter(new OrderPersonVo(keyArray[0], keyArray[1]));

            voList = groupOrderPersonMap.get(key);
            //如果当前分类下只有一条记录，并且用户ID为空，说明是回滚操作，而且原始内容为空
            if (voList.size() == 1 && voList.get(0).getPersonId() == null) {
                this.deleteOrderPersonByOrderIdAndFieldType(keyArray[0], keyArray[1]);
                continue;
            }

            Map<String, OrderPersonVo> map = voList.stream().filter(vo -> Objects.nonNull(vo.getPersonId())).collect(Collectors.toMap(OrderPersonVo::getPersonId, Function.identity(), (o, o2) -> o2));
            voList.clear();
            voList.addAll(map.values());

            if (Objects.isNull(result) || result.isEmpty()) {
                addList.addAll(voList);
            } else {
                Map<String, String> oldPersonMap = new HashMap<>();
                for (OrderPersonVo orderPersonVo : result) {
                    //如果工单-分类-人员存在重复数据，则移除重复值
                    if (oldPersonMap.containsKey(orderPersonVo.getPersonId())) {
                        orderPersonRepository.delete(orderPersonVo.getId());
                        continue;
                    }
                    oldPersonMap.put(orderPersonVo.getPersonId(), orderPersonVo.getId());
                }
                for (OrderPersonVo orderPersonVo : voList) {
                    //数据库中存在相同记录，则无需新增操作
                    if (oldPersonMap.containsKey(orderPersonVo.getPersonId())) {
                        oldPersonMap.remove(orderPersonVo.getPersonId());
                    } else {
                        addList.add(orderPersonVo);
                    }
                }

                for (Map.Entry<String, String> entry : oldPersonMap.entrySet()) {
                    orderPersonRepository.delete(entry.getValue());
                }

                oldDataList.addAll(result);
            }
        }

        this.addOrderPerson(addList);
        //返回修改前的原始数据
        return oldDataList;
    }
}
