package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月07日
 * @Description EAM维保工单Client
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = WorkOrderClientFallback.class)
public interface MaintenanceWorkOrderClient {

	/**
     * saveWorkOrderCommit:保存维保工单-工单提报
     * @param maintenanceWorkOrderForCommitVo
     * @return MaintenanceWorkOrderForListVo
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrderCommit", method = RequestMethod.POST)
    public MaintenanceWorkOrderForCommitVo saveWorkOrderCommit(@RequestBody MaintenanceWorkOrderForCommitVo maintenanceWorkOrderForCommitVo);

    /**
     * saveWorkOrderAssign:保存维保工单-任务分派
     * @param maintenanceWorkOrderForAssignVo
     * @return MaintenanceWorkOrderForListVo
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrderAssign", method = RequestMethod.POST)
    public MaintenanceWorkOrderForAssignVo saveWorkOrderAssign(@RequestBody MaintenanceWorkOrderForAssignVo maintenanceWorkOrderForAssignVo);
    
    /**
     * saveWorkOrderReport:保存维保工单-执行汇报
     * @param maintenanceWorkOrderForReportVo
     * @return MaintenanceWorkOrderForReportVo
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrderReport", method = RequestMethod.POST)
    public MaintenanceWorkOrderForReportVo saveWorkOrderReport(@RequestBody MaintenanceWorkOrderForReportVo maintenanceWorkOrderForReportVo);
    
    /**
     * saveWorkOrderCheckAccept:保存维保工单-验收确认
     * @param maintenanceWorkOrderForCheckAcceptVo
     * @return MaintenanceWorkOrderForListVo
     */
    @RequestMapping(value = "/eam/micro/workorder/saveWorkOrderCheckAccept", method = RequestMethod.POST)
    public MaintenanceWorkOrderForCheckAcceptVo saveWorkOrderCheckAccept(@RequestBody MaintenanceWorkOrderForCheckAcceptVo maintenanceWorkOrderForCheckAcceptVo);

	/**
	 * findWorkOrderById:根据ID查询维保工单-详情
	 * @param id
	 * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报VO
	 */
	@RequestMapping(value = "/eam/micro/workorder/findWorkOrderById", method = RequestMethod.GET)
	MaintenanceWorkOrderForDetailVo findWorkOrderById(@RequestParam("id") String id);

	/**
	 * findWorkOrderByIds:根据ID数组查询维保工单-详情
	 * @param ids
	 * @return List<MaintenanceWorkOrderForDetailVo> 维保工单详情VO
	 */
	@RequestMapping(value = "/eam/micro/workorder/findWorkOrderByIds", method = RequestMethod.POST)
	List<MaintenanceWorkOrderForDetailVo> findWorkOrderByIds(@RequestBody List<String> ids);

    /**
     * findWorkOrderCommitById:根据ID查询维保工单-工单提报
     *
     * @param id
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderCommitById", method = RequestMethod.GET)
    public MaintenanceWorkOrderForCommitVo findWorkOrderCommitById(@RequestParam("id") String id);

    /**
     * findWorkOrderAssignById:根据ID查询维保工单-任务分派
     *
     * @param id
     * @return MaintenanceWorkOrderForAssignVo 维保工单-任务分派VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderAssignById", method = RequestMethod.GET)
    public MaintenanceWorkOrderForAssignVo findWorkOrderAssignById(@RequestParam("id") String id);

    /**
     * findWorkOrderReportById:根据ID查询维保工单-执行汇报
     *
     * @param id
     * @return MaintenanceWorkOrderForReportVo 维保工单-执行汇报VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderReportById", method = RequestMethod.GET)
    public MaintenanceWorkOrderForReportVo findWorkOrderReportById(@RequestParam("id") String id);

    /**
     * findWorkOrderCheckAcceptById:根据ID查询维保工单-验收确认
     *
     * @param id
     * @return MaintenanceWorkOrderForCheckAcceptVo 维保工单-验收确认VO
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderCheckAcceptById", method = RequestMethod.GET)
    public MaintenanceWorkOrderForCheckAcceptVo findWorkOrderCheckAcceptById(@RequestParam("id") String id);

    /**
     * findWorkOrderCommitByWorkOrderNum: 根据woNum查询维保工单-工单提报
     *
     * @param woNum 维保工单编码
     * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderCommitByWorkOrderNum", method = RequestMethod.GET)
    public MaintenanceWorkOrderForCommitVo findWorkOrderCommitByWorkOrderNum(@RequestParam("woNum") String woNum);

    /**
     * findPageWorkOrderList: 分页查询维保工单
     *
     * @param maintenanceWorkOrderSelectVo 查询条件 {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderSelectVo}
     * @return EnerbosPage<MaintenanceWorkOrderForListVo> 预防维保工单集合
     */
    @RequestMapping(value = "/eam/micro/workorder/findPageWorkOrderList", method = RequestMethod.POST)
    EnerbosPage<MaintenanceWorkOrderForListVo> findPageWorkOrderList(@RequestBody MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo);

    /**
     * findPageWorkOrderByAssetId:根据设备ID分页查询维保工单
     *
     * @param maintenanceForAssetFilterVo 根据设备查询维保工单列表过滤条件VO {@link com.enerbos.cloud.eam.vo.MaintenanceForAssetFilterVo}
     * @return EnerbosPage<MaintenanceWorkOrderForListVo> 维保工单集合
     */
    @RequestMapping(value = "/eam/micro/workorder/findPageWorkOrderByAssetId", method = RequestMethod.POST)
    EnerbosPage<MaintenanceWorkOrderForListVo> findPageWorkOrderByAssetId(@RequestBody MaintenanceForAssetFilterVo maintenanceForAssetFilterVo);

	/**
     * deleteWorkOrderList:删除选中的维保工单
     * @param ids
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workorder/deleteWorkOrderList", method = RequestMethod.POST)
    Boolean deleteWorkOrderList(@RequestBody List<String> ids);
    
    /**
     * deleteWorkOrder:删除维保工单
     * @param id
     * @return Boolean 是否删除成功
     */
    @RequestMapping(value = "/eam/micro/workorder/deleteWorkOrder", method = RequestMethod.DELETE)
    Boolean deleteWorkOrder(@RequestParam("id") String id);

	/**
	 * findWorkOrderSingleAssetLastTimeById:根据维保工单ID查询该预防性维护计划上次生成工单的实际工时（分钟）
	 * @param id
	 * @return Double 该预防性维护计划上次生成工单的实际工时（分钟）
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/eam/micro/workorder/findWorkOrderSingleAssetLastTimeById")
	Double findWorkOrderSingleAssetLastTimeById(@RequestParam("id") String id);

	/**
	 * findWorkOrderCommitById:根据ID查询维保工单-工单提报
	 * @param id
	 * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报VO
	 */
	@RequestMapping(value = "/eam/micro/workorder/findWorkOrderWorkFlowById", method = RequestMethod.GET)
	MaintenanceWorkOrderForWorkFlowVo findWorkOrderWorkFlowById(@RequestParam("id") String id);

	/**
	 * findWorkOrderCommitById:根据ID查询维保工单-工单提报
	 * @param maintenanceWorkOrderForWorkFlowVo {@link com.enerbos.cloud.eam.vo.MaintenanceWorkOrderForWorkFlowVo}
	 * @return MaintenanceWorkOrderForCommitVo 维保工单-工单提报VO
	 */
	@RequestMapping(value = "/eam/micro/workorder/saveWorkOrder", method = RequestMethod.POST)
	MaintenanceWorkOrderForWorkFlowVo saveWorkOrder(@RequestBody MaintenanceWorkOrderForWorkFlowVo maintenanceWorkOrderForWorkFlowVo);

	/**
	 * 收藏维保工单
	 * @param maintenanceWorkOrderRfCollectorVoList 收藏的维保工单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/workorder/collect")
	public void collectWorkOrder(@RequestBody List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList);

	/**
	 * 取消收藏维保工单
	 * @param maintenanceWorkOrderRfCollectorVoList 需要取消收藏的维保工单列表
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/workorder/collect/cancel")
	public void cancelCollectWorkOrder(@RequestBody List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList);
	
	/**
	 * 查询维保工单各专业情况的统计
	 * @param siteId 站点id
	 * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
	 * @param endDate 结束时间 格式 yyyy-MM-dd HH:mm:ss
	 * @return 查询结果
	 */
	@RequestMapping(value = "/eam/micro/workorder/findCountWorkOrder",method = RequestMethod.GET)
	public List findCountWorkOrder(@RequestParam("siteId") String siteId, @RequestParam("startDate")  String startDate, @RequestParam("endDate")  String endDate);

	/**
	 * 查询未完成维保工单各专业情况的统计
	 * @param siteId 站点id
	 * @param startDate 开始时间 格式 yyyy-MM-dd HH:mm:ss
	 * @param endDate 结束时间 格式 yyyy-MM-dd HH:mm:ss
	 * @return 查询结果
	 */
	@RequestMapping(value = "/eam/micro/workorder/findCountUndoneWorkOrder",method = RequestMethod.GET)
	public List findCountUndoneWorkOrder(@RequestParam("siteId") String siteId, @RequestParam("startDate")  String startDate, @RequestParam("endDate")  String endDate);

	/**
	 * 查询维保工单总数，未完成数量，已完成数量，以及各未完成工单的各状态的数量
	 * @param orgId  组织ID
	 * @param siteId 站点id
	 * @return 查询结果
	 */
	@RequestMapping(value = "/eam/micro/workorder/findCountByStatus",method = RequestMethod.GET)
	public OrderCountBySiteVo findCountByStatus(@RequestParam("orgId") String orgId,@RequestParam(value = "siteId",required = false) String siteId,
												@RequestParam(value = "startDate",required = false) Date startDate,
												@RequestParam(value = "endDate",required = false) Date endDate);

    /**
     * 今日工总数，未完成/完成数量，历史今日工单数（前五年），以及环比
     *
     * @param siteId 站点id
     * @return 查询结果
     */
    @RequestMapping(value = "/eam/micro/workorder/findCountAndRingratio", method = RequestMethod.GET)
    public OrderCountAndRingratio findCountAndRingratio(@RequestParam("siteId") String siteId);

    /**
     * 查询工单最大数量
     *
     * @param orgId     组织id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/workorder/findMaxCountOrder", method = RequestMethod.GET)
    List<OrderMaxCountVo> findMaxCountOrder(@RequestParam("orgId") String orgId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate);

    /**
     * 统计总部维修工单、保养工单在时间段内的总量
     *
     * @param orgId     组织Id
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @RequestMapping(value = "/eam/micro/workorder/findWorkOrderTotal", method = RequestMethod.GET)
    List<DashboardOrderCountVo> findWorkOrderTotal(@RequestParam("orgId") String orgId,@RequestParam("startDate") Date startDate,@RequestParam("endDate") Date endDate);
}

@Component
class  WorkOrderClientFallback implements FallbackFactory<MaintenanceWorkOrderClient> {
	@Override
	public MaintenanceWorkOrderClient create(Throwable throwable) {
		return new MaintenanceWorkOrderClient() {
			@Override
			public MaintenanceWorkOrderForCommitVo saveWorkOrderCommit(MaintenanceWorkOrderForCommitVo maintenanceWorkOrderForCommitVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForAssignVo saveWorkOrderAssign(MaintenanceWorkOrderForAssignVo maintenanceWorkOrderForAssignVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForReportVo saveWorkOrderReport(MaintenanceWorkOrderForReportVo maintenanceWorkOrderForReportVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForCheckAcceptVo saveWorkOrderCheckAccept(
					MaintenanceWorkOrderForCheckAcceptVo maintenanceWorkOrderForCheckAcceptVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForDetailVo findWorkOrderById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForCommitVo findWorkOrderCommitById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForAssignVo findWorkOrderAssignById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForReportVo findWorkOrderReportById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForCheckAcceptVo findWorkOrderCheckAcceptById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForCommitVo findWorkOrderCommitByWorkOrderNum(String wonum) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<MaintenanceWorkOrderForListVo> findPageWorkOrderList(MaintenanceWorkOrderSelectVo maintenanceWorkOrderSelectVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteWorkOrderList(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Boolean deleteWorkOrder(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public EnerbosPage<MaintenanceWorkOrderForListVo> findPageWorkOrderByAssetId(MaintenanceForAssetFilterVo maintenanceForAssetFilterVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public Double findWorkOrderSingleAssetLastTimeById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForWorkFlowVo findWorkOrderWorkFlowById(String id) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public MaintenanceWorkOrderForWorkFlowVo saveWorkOrder(MaintenanceWorkOrderForWorkFlowVo maintenanceWorkOrderForWorkFlowVo) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void collectWorkOrder(List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}

			@Override
			public void cancelCollectWorkOrder(List<MaintenanceWorkOrderRfCollectorVo> maintenanceWorkOrderRfCollectorVoList) {
				throw new RuntimeException(throwable.getMessage());
			}
			
			@Override
			public List findCountWorkOrder( String siteId, String startDate,String endDate){
				throw new RuntimeException(throwable.getMessage());
			}
			
			@Override
			public List findCountUndoneWorkOrder( String siteId, String startDate,  String endDate){
				throw new RuntimeException(throwable.getMessage());
			}
			@Override
			public OrderCountBySiteVo findCountByStatus( String orgId,String siteId,Date startDate,Date endDate){
				throw new RuntimeException(throwable.getMessage());
			}

            @Override
            public OrderCountAndRingratio findCountAndRingratio(String siteId) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List findMaxCountOrder(String orgId, String startDate, String endDate) {
				throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public List<DashboardOrderCountVo> findWorkOrderTotal(String orgId, Date startDate, Date endDate) {
				throw new RuntimeException(throwable.getMessage());
            }

			@Override
			public List<MaintenanceWorkOrderForDetailVo> findWorkOrderByIds(List<String> ids) {
				throw new RuntimeException(throwable.getMessage());
			}
		};
    }
}