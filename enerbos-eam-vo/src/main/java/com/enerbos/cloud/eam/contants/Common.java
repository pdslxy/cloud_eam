package com.enerbos.cloud.eam.contants;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年6月26日 下午6:14:05
 * @Description 
 */

public class Common {

	/**
	 * eam产品value
	 */
	public static final String EAM_PROD_VALUE = "EAM";

	/**
	 * eam产品id数组
	 */
	public static final String[] EAM_PROD_IDS = { "4028e7ee5da6b132015da6b1f1e70000" };

	/**
	 * 巡检点类型域value
	 */
	public static final String PATROL_TYPE = "patrolType";

	/**
	 * 时间频率单位域value
	 */
	public static final String TIME_FREQUENCY = "timeFrequency";

	//工单编码
	public static final String ORDER_NUM = "orderNum";
	//工单描述
	public static final String ORDER_DESCRIPTION = "orderDescription";

	//工单模块名，用于获取维保工单编码
	public static final String WORK_ORDER_MODEL_KEY = "wo";

	//工作类型-维修工单
	public static final String WORK_ORDER_WORK_TYPE_REPAIR = "BM";

	//系统用户personID
	public static final String SYSTEM_USER = "fe892d133ac511e7a7920242ac110003";

	//系统用户登录名
	public static final String SYSTEM_USER_LOGINNAME = "sysadmin";

	//维保工单流程变更意见，同意
	public static final String WORK_ORDER_PROCESS_STATUS_AGREE = "agree";
	//维保工单流程变更意见，驳回
	public static final String WORK_ORDER_PROCESS_STATUS_REJECT = "reject";
	//维保工单流程变更意见，取消
	public static final String WORK_ORDER_PROCESS_STATUS_CANCEL = "cancel";

	//维保工单流程key前缀,根据流程key去确定唯一，每个组织站点应该不一样，key为WORK_ORDER_WFS_PROCESS_KEY+站点code
	public static final String WORK_ORDER_WFS_PROCESS_KEY = "workOrder";
	
	//报修工单工单流程key前缀,根据流程key去确定唯一，每个组织站点应该不一样，key为WORK_ORDER_WFS_PROCESS_KEY+站点code
	public static final String REPAIR_ORDER_WFS_PROCESS_KEY = "repairOrder";
	
	//分派工单流程key前缀,根据流程key去确定唯一，每个组织站点应该不一样，key为WORK_ORDER_WFS_PROCESS_KEY+站点code
	public static final String DISPATCH_ORDER_WFS_PROCESS_KEY = "dispatchOrder";
	//例行工作单流程key前缀,根据流程key去确定唯一，每个组织站点应该不一样，key为WORK_ORDER_WFS_PROCESS_KEY+站点code
	public static final String DAILY_TASK_WFS_PROCESS_KEY = "headquartersDaliyTask";

	//作业标准，草稿状态
	public static final String JOBSTANDARD_STATUS_DRAFT = "draft";

	//预防性维护计划，草稿状态
	public static final String MAINTENANCE_PLAN_STATUS_DRAFT = "draft";
	//预防性维护计划，活动状态
	public static final String MAINTENANCE_PLAN_STATUS_ACTIVITY = "activity";
	//预防性维护计划，不活动状态
	public static final String MAINTENANCE_PLAN_STATUS_INACTIVITY = "inactivity";

	//维保模块，活动状态
	public static final String WORK_ORDER_STATUS_ACTIVITY = "activity";

	//挂起类型，供应商挂起
	public static final String GYSWX = "GYSWX";// 供应商维修域值

	public static final String ACTIVITI_STATUS = "GYSWX";// 供应商维修域值

	//=============巡检工单=================
	//巡检工单流程key前缀,根据流程key去确定唯一，每个组织站点应该不一样，key为PATROL_ORDER_WFS_PROCESS_KEY+站点code
	public static final String PATROL_ORDER_WFS_PROCESS_KEY = "patrolOrder";
	//巡检工单,指定提报人
	public static final String PATROL_ORDER_SUBMIT_USER = "PatrolOrderSubmitUser";


	//------------------------------域值维护------------------------------

	public static final String WORK_TYPE_DOMAIN = "workType";// 例行工作单，工作类型域值维护
	public static final String USERGROUP_ASSOCIATION_TYPE_ALL = "ALL";// 用户组关联域，关联类型 ALL

	public static  final  String WORK_DATA_ALL="ALL";//ALL数据


	//工单流程类型，正常
	public static final String ORDER_PROCESS_TYPE_NORMAL = "normal";
	//工单流程类型，驳回
	public static final String ORDER_PROCESS_TYPE_REJECT = "reject";

	//---------------------------用户组需要的编码前缀及名称后缀----------------

	/**
	 * 维保工单对应域
	 */
	public static  final String FIELD_WO_PROJECT_TYPE  ="woProjectType" ;

	/**
	 * 保修工单对应域
	 */
	public static  final String FIELD_RO_PROJECT_TYPE="roProjectType";

	/**
	 * 巡检工单 对应域
	 */
	public static  final String  FIELD_PO_TYPE="patrolType" ;

	/**
	 *   缺陷单 、整改单 对应域
	 */
	public static  final String  FIELD_DEFECT_TYPE="defectDocumentType" ;

	/**
	 * 例行工作单 对应域
	 */
	public static  final String FIELD_WORK_TYPE = "workType" ;

	/**
	 * 分派组
	 */
	public static  final String UGROUP_NAME_SUFFIX_ALLOT = "分派组" ;
	/**
	 * 确认组
	 */
	public static  final String UGROUP_NAME_SUFFIX_SURE = "确认组" ;
	/**
	 * 维保工单工程类型
	 */
	public static  final String UGROUP_WO_PROJECTTYPE = "WO_PROJECTTYPE" ;

	public static  final String UGROUP_WO_PROJECTTYPE_NAME_PREFIX = "【维修工单】" ;


	public static  final String UGROUP_RO_PROJECTTYPE = "RO_PROJECTTYPE" ;

	public static  final String UGROUP_RO_PROJECTTYPE_NAME_PREFIX = "【报修工单】" ;

	/**
	 * 派工单
	 */
	public static  final String UGROUP_DISPATCH = "dispatchOrder" ;

	/**
	 * 派工单
	 */
	public static  final String UGROUP_DISPATCH_NAME = "派工工单分派组" ;

	/**
	 * 巡检工单
	 */
	public static final  String UGROUP_PO_TYPE = "PO_TYPE" ;

	/**
	 * 巡检工单
	 */
	public static final  String UGROUP_PO_TYPE_NAME_PREFIX = "【巡检工单】" ;

	/**
	 * 缺陷工单 编码
	 */
	public static final  String UGROUP_DEFECT_DOCUMENT_PROJECT ="DEFECT_DOCUMENT_PROJECT" ;

	/**
	 * 缺陷工单名称前缀
	 */
	public static final  String UGROUP_DEFECT_DOCUMENT_PROJECT_NAME_PREFIX ="【缺陷单】" ;

	/**
	 * 整改单 编码
	 */
	public static final  String UGROUP_DEFECT_OEDER_PROJECT ="DEFECT_OEDER_PROJECT" ;
	/**
	 * 整改单名称前缀
	 */
	public static final  String UGROUP_DEFECT_OEDER_PROJECT_NAME_PREFIX="【整改单】";
	/**
	 * 例行工单 编码
	 */
	public static final  String UGROUP_ROUTINE_WORK_ORDER_PROJECTTYPE = "ROUTINE_WORK_ORDER_PROJECTTYPE";
	/**
	 * 例行工单名称前缀
	 */
	public static final  String UGROUP_ROUTINE_WORK_ORDER_PROJECTTYPE_NAME_PREFIX = "【例行工作单】";


   //我的任务域

	public static  final  String MY_TASK_DOAMIN="taskType";




	//工单来源
        //总部计划生成派工单
	public static  final  String HEADQUARTERS_PLAN_DISPATCH="HPD";
		//总部计划生成预防性维护
	public static  final  String HEADQUARTERS_PLAN_MAINTENANCE="HPM";


	//频率单位
	    //天
	public  static  final  String FREQUENCY_DAY="day";
	   //周
	public  static  final  String FREQUENCY_WEEK="week";
	   //月
	public  static  final  String FREQUENCY_MONTH="month";
	  //年
	public  static  final  String FREQUENCY_YEAR="year";

	//事件等级
	 //高
	public static   final String EVENT_GRADE_H="H";
	//中
	public static   final String EVENT_GRADE_M="M";
	//低
	public static   final String EVENT_GRADE_L="L";



}
