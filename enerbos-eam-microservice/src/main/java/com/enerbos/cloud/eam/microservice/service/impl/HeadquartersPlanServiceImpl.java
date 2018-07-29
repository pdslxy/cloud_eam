package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.DispatchWorkOrderCommon;
import com.enerbos.cloud.eam.contants.HeadquartersDailyTaskCommon;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersDaily;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlan;
import com.enerbos.cloud.eam.microservice.domain.HeadquartersPlanRange;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersDailyRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersDailyTaskRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersPlanRangeRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadquartersPlanRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadquartersPlanDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadquartersPlanRangeDao;
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
 * @author 周长松
 * @version 1.0
 * @date 2017/8/9 20:31
 * @Description 总部计划业务层实现类
 */
@Service
public class HeadquartersPlanServiceImpl implements HeadquartersPlanService {

    @Autowired
    private HeadquartersPlanDao headquartersPlanDao;

    @Autowired
    private HeadquartersPlanRangeDao headquartersPlanRangeDao;

    @Autowired
    private HeadquartersPlanRepository headquartersPlanRepository;

    @Autowired
    private HeadquartersPlanRangeRepository headquartersPlanRangeRepository;

    @Autowired
    private HeadquartersDailyRepository headquartersDailyRepository;

    @Autowired
    private HeadquartersDailyTaskRepository headquartersDailyTaskRepository;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<HeadquartersPlanVo> findPageList(HeadquartersPlanVoForFilter filter) {
        PageHelper.startPage(filter.getPageNum(),filter.getPageSize());
        String word = filter.getWord();
        Map<String, Object> filters=new HashMap<>();
        try{
            if(filter.getSiteId()!=null){//站点级
                List list=new ArrayList();
                if(filter.getStatus()==null||filter.getStatus().size()==0){//状态无传参
                    list.add(HeadquartersDailyTaskCommon.WORK_PLAN_STASTS_ISSUED);
                    list.add(HeadquartersDailyTaskCommon.WORK_PLAN_STASTS_BLANKOUT);
                }
                if(filter.getStatus()!=null&&filter.getStatus().size()>0){
                    //有传参
                    filter.getStatus().stream().forEach(vo->{
                        if(!vo.equals(HeadquartersDailyTaskCommon.WORK_PLAN_STASTS_DRAFT)){
                            list.add(vo);
                        }
                    });
                }
                filter.setStatus(list);
            }
            filters = ReflectionUtils.reflectionModelToMap(filter);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (StringUtils.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }



        List<HeadquartersPlanVo> planVos= headquartersPlanDao.findListByFilter(filters);
        if(filter.getOrgId()!=null&&filter.getSiteId()==null){//组织级别
            planVos.forEach(vo -> {
                List<String> siteList = headquartersPlanRangeDao.getSiteListByPlanId(vo.getId());
                vo.setPlanSite(siteList);
            });
        }else{//站点级
            planVos.forEach(vo -> {
                List list=new ArrayList();
                list.add(filter.getSiteId());
                vo.setPlanSite(list);
            });
        }

        return new PageInfo<>(planVos);
    }

    @Override
    public HeadquartersPlanVo findDetail(String id) {
        return headquartersPlanDao.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteById(List<String> ids){
        for (String id : ids){
            headquartersPlanRepository.delete(id);
        }

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public HeadquartersPlanVoForSave save(HeadquartersPlanVoForSave savevo) {
        HeadquartersPlan po = new HeadquartersPlan();
        BeanUtils.copyProperties(savevo,po);
        //保存总部计划
        HeadquartersPlan plan = headquartersPlanRepository.save(po);
        //删除无用的总部计划应用范围
        headquartersPlanRangeRepository.deleteByPlanId(plan.getId());
        HeadquartersPlanVoForSave newSave = new HeadquartersPlanVoForSave();
        BeanUtils.copyProperties(plan, newSave);
        savevo.getPlanSite().forEach(siteId -> {

            HeadquartersPlanRange planRange = new HeadquartersPlanRange();
            planRange.setPlanId(plan.getId());
            planRange.setSiteId(siteId);
            planRange.setOrgId(plan.getOrgId());

            //保存总部计划应用范围
            headquartersPlanRangeRepository.save(planRange);
        });
        return newSave;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public HeadquartersPlan workPlanSave(HeadquartersPlanVoForSave savevo) {
        HeadquartersPlan po = new HeadquartersPlan();
        BeanUtils.copyProperties(savevo,po);
        //保存总部计划
        HeadquartersPlan plan = headquartersPlanRepository.save(po);

        return plan;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void upStatus(HeadquartersPlanVoForUpStatus headquartersPlanVoForUpStatus) {
        headquartersPlanDao.upStatusByIds(headquartersPlanVoForUpStatus);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Boolean batchRelease(List<String> ids) {
        this.upStatus(new HeadquartersPlanVoForUpStatus(ids, HeadquartersDailyTaskCommon.WORK_PLAN_STASTS_ISSUED));
//        List<HeadquartersPlanVo> list = headquartersPlanDao.findListByIds(ids);
//        for(HeadquartersPlanVo vo : list){
//            HeadquartersDaily daily = new HeadquartersDaily();
//            BeanUtils.copyProperties(vo, daily);
//            List<String> siteList = headquartersPlanRangeDao.getSiteListByPlanId(vo.getId());
//            siteList.forEach(siteId -> {
//                daily.setSiteId(siteId);
//                headquartersDailyRepository.save(daily);
//            });
//            List<String> id=new ArrayList<>();
//            id.add(vo.getId());
//            this.upStatus(new HeadquartersPlanVoForUpStatus(id, HeadquartersDailyTaskCommon.WORK_PLAN_STASTS_ISSUED));
//        }
        return true;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Boolean createRoutineWork(HeadquartersDailyVoForCreateWork work) {
        List<HeadquartersPlanVo> list = new ArrayList<>();
        //判断总部计划ID是否存在，不存在的话查询全部，并生成例行工作
        if(work.getIds().size() != 0){
            list = headquartersPlanDao.findListByIds(work.getIds());
        }else {
      //      list = headquartersPlanDao.findAll(Map<String, Object> filter);
        }
        for(HeadquartersPlanVo vo : list){
            HeadquartersDaily daily = new HeadquartersDaily();
            BeanUtils.copyProperties(vo, daily);
            BeanUtils.copyProperties(work, daily);

            List<String> siteList = headquartersPlanRangeDao.getSiteListByPlanId(vo.getId());
            siteList.forEach(siteId -> {
                daily.setSiteId(siteId);
                daily.setStatus(HeadquartersDailyTaskCommon.DAILY_WORK_STASTS_ACTIVUTY);//指定状态
                headquartersDailyRepository.save(daily);
            });
        }
        return true;
    }

    @Override
    public List<DispatchWorkOrderFlowVo> batchLabor(HeadquartersDailyTaskVoForBatchLabor batchLabor) {
        List<HeadquartersPlanVo> list = new ArrayList<>();
        //判断总部计划ID是否存在，不存在的话查询全部，生成派工单
        if(batchLabor.getIds().size() != 0){
            list = headquartersPlanDao.findListByIds(batchLabor.getIds());
        }else {
            Map<String,Object> filter=new HashMap();
            //只查询已下达的
            filter.put("status",HeadquartersDailyTaskCommon.WORK_PLAN_STASTS_ISSUED);
            //只查询本组织站点的
            filter.put("siteId",batchLabor.getSiteId());//只查询本站点的
            filter.put("orgId",batchLabor.getOrgId());//只查询本站点的
            list = headquartersPlanDao.findAll(filter);
        }
        List<DispatchWorkOrderFlowVo> workOrderFlowVos = new ArrayList<>();
            for(int i=0; i<list.size(); i++){
            HeadquartersPlanVo planVo = list.get(i);
            //工单编号
            String workOrderNum = batchLabor.getWorkOrderNumList().get(i);
            DispatchWorkOrderFlowVo workOrderFlowVo = new DispatchWorkOrderFlowVo();
            workOrderFlowVo.setSiteId(batchLabor.getSiteId());
            workOrderFlowVo.setOrgId(batchLabor.getOrgId());
            workOrderFlowVo.setDescription(planVo.getDescription());
            workOrderFlowVo.setReportDate(new Date());
            workOrderFlowVo.setWorkOrderNum(workOrderNum);
            //提报人ID
            workOrderFlowVo.setReportPersonId(batchLabor.getReportPersonId());
            //提报人电话
            workOrderFlowVo.setReportPersonTel(batchLabor.getReportPersonTel());
            //提报人名称
            workOrderFlowVo.setReportPersonName(batchLabor.getReportPersonName());
            //需求部门
            workOrderFlowVo.setDemandDept(batchLabor.getDemandDept());
            //需求人
            workOrderFlowVo.setDemandPerson(batchLabor.getDemandPerson());
            //需求人电话
            workOrderFlowVo.setDemandPersonTel(batchLabor.getDemandPersonTel());
            workOrderFlowVo.setDescription(batchLabor.getDescription());//派工描述
            //总部计划ID
             workOrderFlowVo.setSrcId(batchLabor.getIds().get(i));
             //总部来源类型
             workOrderFlowVo.setSrcType(Common.HEADQUARTERS_PLAN_DISPATCH);
            workOrderFlowVos.add(workOrderFlowVo);
        }

//        for(HeadquartersPlanVo vo : list){
//            HeadquartersDailyTask dailyTask = new HeadquartersDailyTask();
//            BeanUtils.copyProperties(vo, dailyTask);
//            BeanUtils.copyProperties(batchLabor, dailyTask);
//            headquartersDailyTaskRepository.save(dailyTask);
//        }
        return workOrderFlowVos;
    }

    @Override
    public  List<HeadquartersPlanVo> getHeadquartersPlanAllByFilter(Map<String, Object> filter){
        return  headquartersPlanDao.findAll(filter);
    };

    @Override
    public List<HeadquartersPlanVo> findListByIds(List<String> ids) {
        return headquartersPlanDao.findListByIds(ids);
    }
}
