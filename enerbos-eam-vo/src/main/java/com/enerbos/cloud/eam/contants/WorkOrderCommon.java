package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月30日
 * @Description 维保工单常量
 */
public class WorkOrderCommon {

    //维保工单工程类型的域value
    public static final String WORK_ORDER_PROJECT_TYPE = "woProjectType";
    //维保工单事件级别的域value
    public static final String WORK_ORDER_INCIDENT_LEVEL = "incidentlevel";

    //维保工单工单类型中保养工单的域value
	public static final String WORK_ORDER_WORK_ORDER_TYPE = "PM";

    //维保工单状态域value,待提报
    public static final String WORK_ORDER_STATUS_DTB = "DTB";
    //维保工单状态域value,待分派
    public static final String WORK_ORDER_STATUS_DFP = "DFP";
    //维保工单状态域value,待汇报
    public static final String WORK_ORDER_STATUS_DHB = "DHB";
    //维保工单状态域value,待接单
    public static final String WORK_ORDER_STATUS_DJD = "DJD";
    //维保工单状态域value,申请挂起
    public static final String WORK_ORDER_STATUS_SQGQ = "SQGQ";
    //维保工单状态域value,挂起
    public static final String WORK_ORDER_STATUS_GQ = "GQ";
    //维保工单状态域value,待验收
    public static final String WORK_ORDER_STATUS_DYS = "DYS";
    //维保工单状态域value,关闭
    public static final String WORK_ORDER_STATUS_GB = "GB";
    //维保工单状态域value,取消
    public static final String WORK_ORDER_STATUS_QX = "QX";

    //维保工单,指定提报人
    public static final String WORK_ORDER_SUBMIT_USER = "WorkOrderSubmitUser";
    //维保工单,指定分派人
    public static final String WORK_ORDER_ASSIGN_USER = "TaskAssignmentUser";
    //维保工单,指定接收人
    public static final String WORK_ORDER_RECEIVE_USER = "ReceiveOrder";
    //维保工单,指定汇报人
    public static final String WORK_ORDER_REPORT_USER = "PerformReportingUser";
    //维保工单,指定验收人
    public static final String WORK_ORDER_PERFORMREPORTING_USER = "ConfirmAcceptanceUser";
    //维保工单,指定申请挂起处理人
    public static final String WORK_ORDER_SUSPENDAPPLY_USER = "suspendApply";
    //维保工单,指定挂起处理人
    public static final String WORK_ORDER_SUSPEND_USER = "suspend";

    //维保工单中，流程待分派变更为待接单条件参数
    public static final String WORK_ORDER_ACTIVITY_ASSIGN_PASS = "assignPass";
    //维保工单中，流程待分派变更为待提报条件参数
    public static final String WORK_ORDER_ACTIVITY_REJECT_ASSIGN_PASS = "rejectAssignPass";
    //维保工单中，流程待分派变更为取消流程条件参数
    public static final String WORK_ORDER_ACTIVITY_CANCEL_PASS = "cancelPass";
    //维保工单中，流程待接单变更为拒绝接单条件参数
    public static final String WORK_ORDER_ACTIVITY_REJECT_TAKING_PASS = "rejectTakingPass";
    //维保工单中，流程待接单变更为接单条件参数
    public static final String WORK_ORDER_ACTIVITY_TAKING_PASS = "takingPass";
    //维保工单中，流程执行汇报变更为确认验收条件参数
    public static final String WORK_ORDER_ACTIVITY_REPORT_PASS = "reportPass";
    //维保工单中，流程执行汇报变更为确认验收条件参数
    public static final String WORK_ORDER_ACTIVITY_REJECT_REPORT_PASS = "rejectReportPass";
    //维保工单中，流程执行汇报变更为申请挂起条件参数
    public static final String WORK_ORDER_ACTIVITY_APPLY_SUSPEND_PASS = "applySuspendPass";
    //维保工单中，流程申请挂起变更为执行汇报，拒绝挂起条件参数
    public static final String WORK_ORDER_ACTIVITY_REJECT_APPLY_SUSPEND_PASS = "rejectApplySuspendPass";
    //维保工单中，流程申请挂起变更为挂起条件参数
    public static final String WORK_ORDER_ACTIVITY_SUSPEND_PASS = "suspendPass";
    //维保工单中，流程申请挂起变更为验收确认条件参数
    public static final String WORK_ORDER_ACTIVITY_APPLY_SUSPEND_ACCEPT_PASS = "applySuspendAcceptPass";
    //维保工单中，流程挂起变更为执行汇报参数
    public static final String WORK_ORDER_ACTIVITY_SUSPEND_REPORT_PASS = "suspendReportPass";
    //维保工单中，流程挂起变更为验收确认条件参数
    public static final String WORK_ORDER_ACTIVITY_SUSPEND_ACCEPT_PASS = "suspendAcceptPass";
    //维保工单中，流程挂起变更为重新分派参数
    public static final String WORK_ORDER_ACTIVITY_SUSPEND_ASSIGN_PASS = "suspendAssignPass";
    //维保工单中，流程验收确认结束流程条件参数
    public static final String WORK_ORDER_ACTIVITY_ACCEPT_PASS = "acceptPass";
    //维保工单中，流程验收确认变更为验收驳回，重新执行汇报参数
    public static final String WORK_ORDER_ACTIVITY_REJECT_ACCEPT_PASS = "rejectAcceptPass";

    //==================== 工单关联人员相关 ========================
    public static final String WORK_ORDER_PERSON_EXECUTION = "WORK_ORDER_PERSON_EXECUTION";		                 //维保工单-执行人
    public static final String WORK_ORDER_PERSON_ENTRUST_EXECUTION = "WORK_ORDER_PERSON_ENTRUST_EXECUTION";		//维保工单-委托执行人
    public static final String WORK_ORDER_PERSON_ACTUAL_EXECUTION = "WORK_ORDER_PERSON_ACTUAL_EXECUTION";	    	//维保工单-实际执行人
}
