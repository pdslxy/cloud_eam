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
public class ContractCommon {
        /**
         * eam产品id数组
         */
        public static final String[] EAM_PROD_IDS = { "4028e7ee5da6b132015da6b1f1e70000" };
        /**
         * eam产品id数组
         */
        public static final String CONTRACT = "contract";
        /**
         * eam产品id数组
         */
        public static final String CONSTRUCTION = "construction";
        //合同编码
        public static final String Contract_NUM = "contractNum";
        //合同描述
        public static final String Contract_DESCRIPTION = "contractDescription";
        //合同流程变更意见，同意
        public static final String PROCESS_STATUS_AGREE = "agree";
        //合同状态
        public static final String STATUS_XZ = "XZ";
        //合同状态
        public static final String STATUS_ZX = "ZX";
        //合同状态
        public static final String STATUS_PJ = "PJ";
        //合同状态
        public static final String STATUS_WC = "WC";
        //合同,指定提报人
        public static final String SUBMIT_USER = "contractSubmitUser";
        //合同,指定分派人
        public static final String EXCUTE_USER = "contractExecuteUser";
        //合同，指定确认人
        public static final String CONFIRM_USER = "contractConfirmUser";
        //合同,指定接收人
        public static final String RECEIVE_USER = "ReceiveOrder";
        //合同,指定汇报人
        public static final String REPORT_USER = "PerformReportingUser";
        //合同,指定验收人
        public static final String PERFORMREPORTING_USER = "ConfirmAcceptanceUser";
        //合同中，流程新增变更为执行中条件参数
        public static final String ACTIVITY_COMMIT_PASS = "commitPass";
        //合同中，流程评价中变更为关闭流程条件参数
        public static final String ACTIVITY_CANCEL_PASS = "cancelPass";
        //合同中，流程执行中变更为评价中流程条件参数
        public static final String ACTIVITY_EXCUTE_PASS = "excutePass";

        public static final String WFS_PROCESS_KEY = "contract";

        public static final String CONTRACT_TYPE = "contractType";

        public static final String CONTRACT_STATUS = "contract";


}
