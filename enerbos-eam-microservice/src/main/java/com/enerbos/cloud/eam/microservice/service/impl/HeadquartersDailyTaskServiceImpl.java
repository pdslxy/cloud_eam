package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.HeadquartersDailyTaskCommon;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersDaily;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersDailyTask;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlan;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersDailyRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersDailyTaskRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersPlanRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadquartersDailyTaskDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadquartersPlanDao;
import com.enerbos.cloud.eam.microservice.service.HeadquartersDailyTaskService;
import com.enerbos.cloud.eam.microservice.service.HeadquartersPlanService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2016
 * Company   北京翼虎能源科技有限公司
 *
 * @author 张鹏伟
 * @version 1.0
 * @date 2017/8/16 14:50
 * @Description 例行工作单业务层实现类
 */
@Service
public class HeadquartersDailyTaskServiceImpl implements HeadquartersDailyTaskService {

    @Autowired
    private HeadquartersDailyTaskDao headquartersDailyTaskDao;

    @Autowired
    private HeadquartersDailyTaskRepository headquartersDailyTaskRepository;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<HeadquartersDailyTaskVo> findPageList(HeadquartersDailyTaskVoForFilter filter) {
        PageHelper.startPage(filter.getPageNum(),filter.getPageSize());
        String word = filter.getKeyword();
        Map<String, Object> filters=new HashMap<>();
        try{
            filters = ReflectionUtils.reflectionModelToMap(filter);
          //  filters = EamCommonUtil.reModelToMap(filter);
        if (StringUtils.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        if(filter.getExecutor()!=null&&filter.getExecutor().size()>0){
            filters.put("field_type",HeadquartersDailyTaskCommon.DISPATCH_ORDER_PERSON_EXECUTION);
        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new PageInfo<>(headquartersDailyTaskDao.findListByFilter(filters));
    }

    @Override
    public HeadquartersDailyTaskVo findDetail(String id) {
        return headquartersDailyTaskDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteById(String ids){
        String[] idss=ids.split(",");
        for (String id : idss) {
            headquartersDailyTaskDao.deleteByPrimaryKey(id);
        }
    }
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void upStatus(String[] ids,String status) {


       List list=new ArrayList();
        HeadquartersDailyTask headquartersDailyTaskVo;
        for (int i = 0, len = ids.length; i < len; i++) {
             headquartersDailyTaskVo = headquartersDailyTaskRepository.findOne(ids[i]);
            if (headquartersDailyTaskVo == null) {
                throw new EnerbosException("404", String.format("工单编号不存在！  [%s]", ids[i]));
            }
            if (HeadquartersDailyTaskCommon.DAILY_TASK_STATUS_CLOSE.equals(headquartersDailyTaskVo.getStatus())) {
                throw new EnerbosException("500", String.format("指定工单已经关闭！  工单编号：[%s]", headquartersDailyTaskVo.getPlanNum()));
            }
            headquartersDailyTaskVo.setStatus(status);
            list.add(headquartersDailyTaskVo);
        }
        headquartersDailyTaskRepository.save(list);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public HeadquartersDailyTaskVo save(HeadquartersDailyTaskVo savevo) {

        if(savevo.getId()==null){
            savevo.setCreateDate(new Date());
        }
        HeadquartersDailyTask po = new HeadquartersDailyTask();
        BeanUtils.copyProperties(savevo,po);
        HeadquartersDailyTask task = headquartersDailyTaskRepository.save(po);
        HeadquartersDailyTaskVo newSave = new HeadquartersDailyTaskVo();
        BeanUtils.copyProperties(task,newSave);
        return newSave;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<HeadquartersDailyTaskVo> findDailyTaskByplanId(String planId, String orgId, String siteId) {
        Map<String, Object> filters=new HashMap<>();
        filters.put("planId",planId);
        filters.put("orgId",orgId);
        filters.put("siteId",siteId);
        return headquartersDailyTaskDao.findListByFilter(filters);
    }
}
