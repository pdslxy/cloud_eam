package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author liuxiupeng
 * @version 1.0
 * @date 2017年11月21日 13:51:03
 * @Description
 */
public class CopyMeterCommon {

    //抄表工单状态 domain值
    public static final String COPY_METER_ORDER_STATUS = "copyMeterOrderStatus";

    //未完成
    public static final String COPYMETER_STATUS_UNCOMPLETE = "uncomplete";

    //已完成
    public static final String COPYMETER_STATUS_COMPLETE = "complete";

    //已过期
    public static final String COPYMETER_STATUS_OUTOFDATE = "outofdate";

    //已补录
    public static final String COPYMETER_STATUS_ADDITIONALRECORDING = "additionalrecording";


    //    抄表计划
    public static final String COPY_METER_PLAN_STATUS = "copyMeterPlanStatus";
    //    //抄表计划，草稿状态
    public static final String COPYMETER_PLAN_STATUS_CG = "CG";
    //
//    抄表计划，活动状态
    public static final String COPYMETER_PLAN_STATUS_ACTIVITY = "activity";
    //抄表计划，不活动状态
    public static final String COPYMETER_PLAN_STATUS_INACTIVITY = "inactivity";
    //
    public static final String METER_ASSTT_TYPE = "meterAsstType";

    //工单状态-草稿
    public static final String COPY_METER_STATUS_CG = "CG";
    //工单状态-待分派
    public static final String COPY_METER_STATUS_DFP = "DFP";
    //工单状态-执行中
    public static final String COPY_METER_STATUS_ZX = "ZX";
    //工单状态-已完成
    public static final String COPY_METER_STATUS_WC = "WC";
    //工单状态-过期
    public static final String COPY_METER_STATUS_GQ = "GQ";
    //工单状态-补录
    public static final String COPY_METER_STATUS_BL = "BL";

    //抄表工单流程key前缀,根据流程key去确定唯一，每个组织站点应该不一样，key为PATROL_ORDER_WFS_PROCESS_KEY+站点code
    public static final String WFS_PROCESS_KEY = "meterRecord";


    public static final String ASSIGN_USER = "meterRecordAssignUser";
    public static final String EXCUTE_USER = "meterRecordExcuteUser";

    //工单编码
    public static final String ORDER_NUM = "orderNum";
    //工单描述
    public static final String ORDER_DESCRIPTION = "orderDescription";

    //抄表工单流程变更意见，同意
    public static final String PROCESS_STATUS_PASS = "PASS";

    //抄表工单流程变更意见，驳回
    public static final String PROCESS_STATUS_REJECTS = "REJECT";

    //巡检工单中，流程待分派变更为执行中条件参数
    public static final String ACTIVITY_ASSIGN_PASS = "assignPass";

    //巡检工单中，流程执行中变更为完成条件参数
    public static final String ACTIVITY_EXCUTE_PASS = "excutePass";

    public static final String ACTIVITY_EXCUTE_REJECT = "rejectExcutePass";

    public static final String PLAN_COLLECT_KEY = "copyMeterPlan";

    public static final String ORDER_COLLECT_KEY = "daliy";

    public static final String BEGIN_ASSIGN = "beginAssign"
            ;
    public static final String BEGIN_EXCUTE = "beginExcute";

    //计划生成
    public static  final String PLAN_CREATE="计划生成";
    //计划生成
    public static  final String PLAN_CREATE_CODE="auto";
    //今日创建
    public static  final String TODAY_CREATE="今日生成";
    //手动创建
    public static  final String MANUAL_CREATE="手动创建";

    public static  final String MANUAL_CREATE_CODE="manual";

    //手动创建域值
    public static final String COPY_MERTER_ORDER_TYPE_MANUAL = "manual" ;
}
