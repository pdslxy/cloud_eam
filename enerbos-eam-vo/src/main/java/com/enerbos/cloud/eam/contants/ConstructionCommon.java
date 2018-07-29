package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年09月06日
 * @Description 施工管理常量
 */
public class ConstructionCommon {

    //工单模块名，用于获取施工单编码
    public static final String CONSTRUCTION_CONFIRM_GROUP = "construction";

    //工单模块名，用于获取施工单编码
    public static final String CONSTRUCTION_WFS_PROCESS_KEY = "construction";
    //施工单,指定提报人
    public static final String CONSTRUCTION_SUBMIT_USER = "constructionSubmitUser";
    //施工单,指定确认人
    public static final String CONSTRUCTION_CONFIRM_USER = "constructionConfirmUser";
    //施工单状态，待提报
    public static final String CONSTRUCTION_STATUS_XZ = "XZ";
    //施工单状态，待确认
    public static final String CONSTRUCTION_STATUS_ZX = "ZX";
    //施工单状态，已确认
    public static final String CONSTRUCTION_STATUS_WC = "WC";
    //施工单,流程待提报变更为待确认条件参数
    public static final String CONSTRUCTION_REPORT_PASS = "commitPass";
    //施工单,流程待确认变更为已确认条件参数
    public static final String CONSTRUCTION_CANCEL_PASS = "cancelPass";
    public static final String CONSTRUCTION_REJECT = "rejectAcceptPass";
    //施工单,流程待确认变更为待提报条件参数
    public static final String CONSTRUCTION_NUM = "constructionNum";
    public static final String CONSTRUCTION_DESCRIPTION = "constructionDescription";

    public static final String CONSTRUCTION_STATUS = "construction";

}
