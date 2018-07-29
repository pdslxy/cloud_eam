package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年09月06日
 * @Description 缺陷管理常量
 */
public class DefectCommon {

    //缺陷单工程类型的域value
    public static final String DEFECT_DOCUMENT_PROJECT_TYPE = "defectDocumentType";
    //工单模块名，用于获取缺陷单编码
    public static final String DEFECT_DOCUMENT_WFS_PROCESS_KEY = "defectDocument";
    //缺陷单,指定提报人
    public static final String DEFECT_DOCUMENT_SUBMIT_USER = "documentSubmitUser";
    //缺陷单,指定确认人
    public static final String DEFECT_DOCUMENT_CONFIRM_USER = "documentConfirmUser";
    //缺陷单状态，待提报
    public static final String DEFECT_DOCUMENT_STATUS_DTB = "DTB";
    //缺陷单状态，待确认
    public static final String DEFECT_DOCUMENT_STATUS_DQR = "DQR";
    //缺陷单状态，已确认
    public static final String DEFECT_DOCUMENT_STATUS_YQR = "YQR";
    //缺陷单,流程待提报变更为待确认条件参数
    public static final String DEFECT_DOCUMENT_REPORT_PASS = "reportPass";
    //缺陷单,流程待确认变更为已确认条件参数
    public static final String DEFECT_DOCUMENT_ACCEPT_PASS = "acceptPass";
    //缺陷单,流程待确认变更为待提报条件参数
    public static final String DEFECT_DOCUMENT_REJECT_ACCEPT_PASS = "rejectAcceptPass";






    //消缺工单工程类型的域value
    public static final String DEFECT_ORDER_PROJECT_TYPE = "defectOrderType";
    //工单模块名，用于获取缺陷单编码
    public static final String DEFECT_ORDER_WFS_PROCESS_KEY = "defectOrder";
    //消缺工单状态，待提报
    public static final String DEFECT_ORDER_STATUS_DTB = "DTB";
    //消缺工单状态，待分派
    public static final String DEFECT_ORDER_STATUS_DFP = "DFP";
    //消缺工单状态，待接单
    public static final String DEFECT_ORDER_STATUS_DJD = "DJD";
    //消缺工单状态，待汇报
    public static final String DEFECT_ORDER_STATUS_DHB = "DHB";
    //消缺工单状态，待验收
    public static final String DEFECT_ORDER_STATUS_DYS = "DYS";
    //消缺工单状态，申请挂起
    public static final String DEFECT_ORDER_STATUS_SQGQ = "SQGQ";
    //消缺工单状态，挂起
    public static final String DEFECT_ORDER_STATUS_GQ = "GQ";
    //消缺工单状态，关闭
    public static final String DEFECT_ORDER_STATUS_GB = "GB";
    //消缺工单,指定提报人
    public static final String DEFECT_ORDER_SUBMIT_USER = "DefectOrderSubmitUser";
    //消缺工单,指定分派人
    public static final String DEFECT_ORDER_ASSIGN_USER = "TaskAssignmentUser";
    //消缺工单,指定接收人
    public static final String DEFECT_ORDER_RECEIVE_USER = "ReceiveOrder";
    //消缺工单,指定汇报人
    public static final String DEFECT_ORDER_REPORT_USER = "PerformReportingUser";
    //消缺工单,指定验收人
    public static final String DEFECT_ORDER_PERFORMREPORTING_USER = "ConfirmAcceptanceUser";
    //消缺工单,指定申请挂起处理人
    public static final String DEFECT_ORDER_SUSPENDAPPLY_USER = "suspendApply";
    //消缺工单,指定挂起处理人
    public static final String DEFECT_ORDER_SUSPEND_USER = "suspend";
    //消缺工单中，流程待分派变更为待接单条件参数
    public static final String DEFECT_ORDER_ACTIVITY_ASSIGN_PASS = "assignPass";
    //消缺工单中，流程待分派变更为待提报条件参数
    public static final String DEFECT_ORDER_ACTIVITY_REJECT_ASSIGN_PASS = "rejectAssignPass";
    //消缺工单中，流程待分派变更为取消流程条件参数
    public static final String DEFECT_ORDER_ACTIVITY_CANCEL_PASS = "cancelPass";
    //消缺工单中，流程待接单变更为拒绝接单条件参数
    public static final String DEFECT_ORDER_ACTIVITY_REJECT_TAKING_PASS = "rejectTakingPass";
    //消缺工单中，流程待接单变更为接单条件参数
    public static final String DEFECT_ORDER_ACTIVITY_TAKING_PASS = "takingPass";
    //消缺工单中，流程执行汇报变更为确认验收条件参数
    public static final String DEFECT_ORDER_ACTIVITY_REPORT_PASS = "reportPass";
    //消缺工单中，流程执行汇报变更为确认验收条件参数
    public static final String DEFECT_ORDER_ACTIVITY_REJECT_REPORT_PASS = "rejectReportPass";
    //消缺工单中，流程执行汇报变更为申请挂起条件参数
    public static final String DEFECT_ORDER_ACTIVITY_APPLY_SUSPEND_PASS = "applySuspendPass";
    //消缺工单中，流程申请挂起变更为执行汇报，拒绝挂起条件参数
    public static final String DEFECT_ORDER_ACTIVITY_REJECT_APPLY_SUSPEND_PASS = "rejectApplySuspendPass";
    //消缺工单中，流程申请挂起变更为挂起条件参数
    public static final String DEFECT_ORDER_ACTIVITY_SUSPEND_PASS = "suspendPass";
    //消缺工单中，流程申请挂起变更为验收确认条件参数
    public static final String DEFECT_ORDER_ACTIVITY_APPLY_SUSPEND_ACCEPT_PASS = "applySuspendAcceptPass";
    //消缺工单中，流程挂起变更为执行汇报参数
    public static final String DEFECT_ORDER_ACTIVITY_SUSPEND_REPORT_PASS = "suspendReportPass";
    //消缺工单中，流程挂起变更为验收确认条件参数
    public static final String DEFECT_ORDER_ACTIVITY_SUSPEND_ACCEPT_PASS = "suspendAcceptPass";
    //消缺工单中，流程挂起变更为重新分派参数
    public static final String DEFECT_ORDER_ACTIVITY_SUSPEND_ASSIGN_PASS = "suspendAssignPass";
    //消缺工单中，流程验收确认结束流程条件参数
    public static final String DEFECT_ORDER_ACTIVITY_ACCEPT_PASS = "acceptPass";
    //消缺工单中，流程验收确认变更为验收驳回，重新执行汇报参数
    public static final String DEFECT_ORDER_ACTIVITY_REJECT_ACCEPT_PASS = "rejectAcceptPass";

    //==================== 工单关联人员相关 ========================
    public static final String DEFECT_ORDER_PERSON_EXECUTION = "DEFECT_ORDER_PERSON_EXECUTION";		                 //消缺工单-执行人
    public static final String DEFECT_ORDER_PERSON_ENTRUST_EXECUTION = "DEFECT_ORDER_PERSON_ENTRUST_EXECUTION";		//消缺工单-委托执行人
    public static final String DEFECT_ORDER_PERSON_ACTUAL_EXECUTION = "DEFECT_ORDER_PERSON_ACTUAL_EXECUTION";	    	//消缺工单-实际执行人
}
