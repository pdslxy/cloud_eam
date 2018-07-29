package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/8/31
 * @Description
 */
public class PatrolOrderCommon {
        /**
         * eam产品id数组
         */
        public static final String[] EAM_PROD_IDS = { "4028e7ee5da6b132015da6b1f1e70000" };
        //工单编码
        public static final String ORDER_NUM = "orderNum";
        //工单描述
        public static final String ORDER_DESCRIPTION = "orderDescription";
        //巡检工单流程变更意见，同意
        public static final String PROCESS_STATUS_AGREE = "agree";
        //巡检工单流程变更意见，驳回
        public static final String PROCESS_STATUS_REJECT = "reject";
        //巡检工单流程变更意见，取消
        public static final String PROCESS_STATUS_CANCEL = "cancel";

        //巡检工单流程变更意见，同意
        public static final String PROCESS_STATUS_PASS = "PASS";

        //巡检工单流程变更意见，驳回
        public static final String PROCESS_STATUS_REJECTS = "REJECT";

        //巡检工单状态
        public static final String STATUS_DTB = "DTB";
        //巡检工单状态
        public static final String STATUS_DFP = "DFP";
        //巡检工单状态
        public static final String STATUS_DHB = "DHB";
        //巡检工单状态
        public static final String STATUS_DJD = "DJD";
        //巡检工单状态
        public static final String STATUS_GB = "GB";
        //巡检工单,指定提报人
        public static final String SUBMIT_USER = "PatrolOrderSubmitUser";
        //巡检工单,指定分派人
        public static final String ASSIGN_USER = "TaskAssignmentUser";
        //巡检工单,指定接收人
        public static final String RECEIVE_USER = "ReceiveOrder";
        //巡检工单,指定汇报人
        public static final String REPORT_USER = "PerformReportingUser";
        //巡检工单,指定验收人
        public static final String PERFORMREPORTING_USER = "ConfirmAcceptanceUser";
        //巡检工单中，流程待分派变更为待接单条件参数
        public static final String ACTIVITY_ASSIGN_PASS = "assignPass";
        //巡检工单中，流程待分派变更为待提报条件参数
        public static final String ACTIVITY_REJECT_ASSIGN_PASS = "rejectAssignPass";
        //巡检工单中，流程待分派变更为取消流程条件参数
        public static final String ACTIVITY_CANCEL_PASS = "cancelPass";
        //巡检工单中，流程待接单变更为拒绝接单条件参数
        public static final String ACTIVITY_REJECT_TAKING_PASS = "rejectTakingPass";
        //巡检工单中，流程待接单变更为接单条件参数
        public static final String ACTIVITY_TAKING_PASS = "takingPass";
        //巡检工单中，流程执行汇报变更为确认验收条件参数
        public static final String ACTIVITY_REPORT_PASS = "reportPass";
        //巡检工单中，流程执行汇报变更为确认验收条件参数
        public static final String ACTIVITY_REJECT_REPORT_PASS = "rejectReportPass";
        //巡检工单中，流程执行汇报变更为申请挂起条件参数
        public static final String ACTIVITY_APPLY_SUSPEND_PASS = "applySuspendPass";
        //巡检工单流程key前缀,根据流程key去确定唯一，每个组织站点应该不一样，key为PATROL_ORDER_WFS_PROCESS_KEY+站点code
        public static final String WFS_PROCESS_KEY = "patrolOrder";

        public static final String PATROL_TYPE = "patrolType";


}
