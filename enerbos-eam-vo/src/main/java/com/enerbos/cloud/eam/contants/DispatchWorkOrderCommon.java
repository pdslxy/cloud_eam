package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-24 10:01
 * @Description 派工工单公共属性类
 */
public class DispatchWorkOrderCommon {
    //==================== 流程状态相关 ==========================
    public static final String DISPATCH_ORDER_STATUS_DTB = "DTB";
    public static final String DISPATCH_ORDER_STATUS_DFP = "DFP";
    public static final String DISPATCH_ORDER_STATUS_DHB = "DHB";
    public static final String DISPATCH_ORDER_STATUS_DYS = "DYS";
    public static final String DISPATCH_ORDER_STATUS_CANCEL = "QX";
    public static final String DISPATCH_ORDER_STATUS_CLOSE = "GB";

    //==================== 流程操作相关 ==========================
    /**
     * 流程确认操作使用
     */
    public static final String DISPATCH_ORDER_PROCESS_STATUS_PASS = "PASS";
    /**
     * 流程驳回操作使用
     */
    public static final String DISPATCH_ORDER_PROCESS_STATUS_REJECT = "REJECT";
    /**
     * 流程取消操作使用
     */
    public static final String DISPATCH_ORDER_PROCESS_STATUS_CANCEL = "CANCEL";
    /**
     * WFS流程KEY
     */
    public static final String DISPATCH_ORDER_WFS_PROCESS_KEY = "dispatchOrder";

    /**
     * 工单分派时用户组域值key
     */
    public static final String DISPATCH_ORDER_DISPATCH_USER_GROUP_KEY = "dispatchOrder";

    /**
     * 指定提报人
     */
    public static final String DISPATCH_ORDER_ACTIVITY_ASSIGNEE_SUBMIT_USER = "WorkOrderSubmitUser";
    /**
     * 指定分派人
     */
    public static final String DISPATCH_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER = "TaskAssignmentUser";
    /**
     * 指定汇报人
     */
    public static final String DISPATCH_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER = "PerformReportingUser";
    /**
     * 指定确认验收人
     */
    public static final String DISPATCH_ORDER_ACTIVITY_ASSIGNEE_CONFIRM_ACCEPTOR_USER = "PerformAcceptUser";
    //==================== 工单关联人员相关 ========================
    /**
     * 派工工单-执行人
     */
    public static final String DISPATCH_ORDER_PERSON_EXECUTION = "DISPATCH_ORDER_PERSON_EXECUTION";
    /**
     * 派工工单-实际执行人
     */
    public static final String DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION = "DISPATCH_ORDER_PERSON_ACTUAL_EXECUTION";
    /**
     * 派工工单-验收人
     */
    public static final String DISPATCH_ORDER_PERSON_ACCEPTOR = "DISPATCH_ORDER_PERSON_ACCEPTOR";
}
