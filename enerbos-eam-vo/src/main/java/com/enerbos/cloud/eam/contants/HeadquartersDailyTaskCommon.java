package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 张鹏伟
 * @version 1.0
 * @date 2017-07-24 10:01
 * @Description 例行工作单公共属性类
 */
public class HeadquartersDailyTaskCommon {
    //==================== 流程状态相关 ==========================
    public static final String DAILY_TASK_STATUS_DTB = "DTB";//待提报
    public static final String DAILY_TASK_STATUS_DFP = "DFP";//待分派
    public static final String DAILY_TASK_STATUS_DJD = "DJD";//待接单
    public static final String DAILY_TASK_STATUS_DHB = "DHB";//待汇报
    public static final String DAILY_TASK_STATUS_DYS = "DYS";//待验收
    public static final String DAILY_TASK_STATUS_CLOSE = "QX";//取消
    public static final String DAILY_TASK_STATUS_SUCCESS = "GB";//完成

    //==================== 流程操作相关 ==========================
    public static final String DAILY_TASK_PROCESS_STATUS_PASS = "PASS";					//确认操作使用
    public static final String DAILY_TASK_PROCESS_STATUS_REJECT = "REJECT";			//驳回操作使用
    public static final String DAILY_TASK_PROCESS_STATUS_CANCEL = "CANCEL";	    //取消操作使用

    public static final String DAILY_TASK_WFS_PROCESS_KEY = "headquartersDaliyTask";

    public  static  final  String  DAILY_TASK_WORKTYPE="workType";//工作类型

    //指定提报人
    public static final String DAILY_TASK_ACTIVITY_ASSIGNEE_SUBMIT_USER = "WorkOrderSubmitUser";
    //指定分派人
    public static final String DAILY_TASK_ACTIVITY_ASSIGNEE_ASSIGN_USER = "TaskAssignmentUser";
    //指定接单人
    public static final String DAILY_TASK_ACTIVITY_ASSIGNEE_ORDERS_USER = "ReceiveOrderUser";
    //指定汇报人
    public static final String DAILY_TASK_ACTIVITY_ASSIGNEE_REPORT_USER = "PerformReportingUser";
    //验收人
    public static final String DAILY_TASK_ACTIVITY_ASSIGNEE_CHECK_USER = "ConfirmAcceptanceUser";

    //==================== 工单关联人员相关 ========================
    public static final String DISPATCH_ORDER_PERSON_EXECUTION = "DAILY_TASK_ORDER_PERSON_EXECUTION";//执行负责人                                  //派工工单-执行人

    //总部计划状态
    public static final String WORK_PLAN_STASTS_DRAFT = "draft";//草稿
    public static final String WORK_PLAN_STASTS_ISSUED = "Issued";//已下达
    public static final String WORK_PLAN_STASTS_BLANKOUT = "blankOut";//已作废
    //例行工作状态
    public static final String DAILY_WORK_STASTS_DRAFT = "draft";//草稿
    public static final String DAILY_WORK_STASTS_ACTIVUTY = "activity";//活动
    public static final String DAILY_WORK_STASTS_IN_ACTIVUTY = "inactivity";//不活动
}
