package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-17 16:31
 * @Description 报修工单公共属性类
 */
public class RepairOrderCommon {

    //==================== 流程状态相关 ==========================
    public static final String REPAIR_ORDER_STATUS_DTB = "DTB";
    public static final String REPAIR_ORDER_STATUS_DFP = "DFP";
    public static final String REPAIR_ORDER_STATUS_DJD = "DJD";
    public static final String REPAIR_ORDER_STATUS_DHB = "DHB";
    public static final String REPAIR_ORDER_STATUS_DYS = "DYS";
    public static final String REPAIR_ORDER_STATUS_YS_DQR = "YSDQR";
    public static final String REPAIR_ORDER_STATUS_WAIT_SQ = "SQGQ";
    public static final String REPAIR_ORDER_STATUS_WAIT = "GQ"; //挂起
    public static final String REPAIR_ORDER_STATUS_CANCEL = "QX"; //取消
    public static final String REPAIR_ORDER_STATUS_CLOSE = "GB"; //关闭

    //==================== 流程操作相关 ==========================
    /**
     * 确认操作使用
     */
    public static final String REPAIR_ORDER_PROCESS_STATUS_PASS = "PASS";
    /**
     * 驳回操作使用
     */
    public static final String REPAIR_ORDER_PROCESS_STATUS_REJECT = "REJECT";
    /**
     * 取消操作使用
     */
    public static final String REPAIR_ORDER_PROCESS_STATUS_CANCEL = "CANCEL";
    /**
     * WFS流程KEY
     */
    public static final String REPAIR_ORDER_WFS_PROCESS_KEY = "repairOrder";
    /**
     * 供应商挂起确认操作使用
     */
    public static final String REPAIR_ORDER_ACTIVITY_PROCESS_STATUS_GYS_PASS = "GysPASS";

    /**
     * 指定提报人
     */
    public static final String REPAIR_ORDER_ACTIVITY_ASSIGNEE_SUBMIT_USER = "RepairOrderSubmitUser";
    /**
     * 指定分派人
     */
    public static final String REPAIR_ORDER_ACTIVITY_ASSIGNEE_ASSIGN_USER = "RepairOrderAssignUser";
    /**
     * 指定接收人
     */
    public static final String REPAIR_ORDER_ACTIVITY_ASSIGNEE_RECEIVE_USER = "RepairOrderReceiveUser";
    /**
     * 指定汇报人
     */
    public static final String REPAIR_ORDER_ACTIVITY_ASSIGNEE_REPORT_USER = "RepairOrderReportUser";
    /**
     * 指定申请挂起处理人
     */
    public static final String REPAIR_ORDER_ACTIVITY_ASSIGNEE_SUSPEND_APPLY_USER = "RepairOrderSuspendApplyUser";
    /**
     * 指定挂起处理人
     */
    public static final String REPAIR_ORDER_ACTIVITY_ASSIGNEE_SUSPEND_USER = "RepairOrderSuspendUser";
    /**
     * 指定验收人
     */
    public static final String REPAIR_ORDER_ACTIVITY_ASSIGNEE_ACCEPTOR_USER = "RepairOrderAcceptorUser";
    /**
     * 指定确认验收人
     */
    public static final String REPAIR_ORDER_ACTIVITY_ASSIGNEE_CONFIRM_ACCEPTOR_USER = "RepairOrderConfirmAcceptorUser";
    //==================== 工单关联人员相关 ========================
    /**
     * 报修工单-执行人
     */
    public static final String REPAIR_ORDER_PERSON_EXECUTION = "REPAIR_ORDER_PERSON_EXECUTION";
    /**
     * 报修工单-委托执行人
     */
    public static final String REPAIR_ORDER_PERSON_ENTRUST_EXECUTION = "REPAIR_ORDER_PERSON_ENTRUST_EXECUTION";
    /**
     * 报修工单-实际执行人
     */
    public static final String REPAIR_ORDER_PERSON_ACTUAL_EXECUTION = "REPAIR_ORDER_PERSON_ACTUAL_EXECUTION";
    /**
     * 报修工单-验收人
     */
    public static final String REPAIR_ORDER_PERSON_ACCEPTOR = "REPAIR_ORDER_PERSON_ACCEPTOR";

    //==================== 工程类型关联用户组相关 =====================
    /**
     * 报修工单-工程类型关联用户组前缀
     */
    public static final String REPAIR_ORDER_PROJECT_TYPE_PREFIX = "RO_PROJECTTYPE";
    /**
     * 报修工单工程类型的域value
     */
    public static final String REPAIR_ORDER_PROJECT_TYPE = "roProjectType";

    //==================== 工程类型关联用户组相关 =====================
    /**
     * 用户组关联域，关联类型 EXECUTE 执行组
     */
    public static final String USERGROUP_ASSOCIATION_TYPE_EXECUTE = "EXECUTE";


    //=====================环境监测 报警生成维修工单 相关常量 =============
    /**
     * 维保工单生成编码  model-key
     */
    public static final String REPAIR_ORDER_MODEL_KEY = "repair" ;

    /**
     * 事件级别 中
     */
    public static final String LEVEL_MIDDLE ="M" ;

    /**
     * 事件级别 高
     */
    public static final String LEVEL_HIGH = "H" ;
    /**
     * 事件级别 低
     */
    public static final String LEVEL_LOW = "L" ;

    /**
     *系统自动生成
     */
    public static final String SYSTEM_AUTO = "XTZD";

    /**
     * 报警 报修工单工程类型
     */
    public static final String WARNING_PROJECT_TYPE ="M" ;
}
