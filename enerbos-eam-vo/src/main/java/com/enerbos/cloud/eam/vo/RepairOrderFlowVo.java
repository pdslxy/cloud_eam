package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author hk
 * @version 1.0
 * @date 2017-07-12 11:17
 * @Description 报修工单-提报VO
 */
@ApiModel(value = "报修工单-流程VO", description = "报修工单-流程VO")
public class RepairOrderFlowVo implements Serializable {
    //============== 报修工单状态相关 =================
    @ApiModelProperty(value = "工单状态")
    private String workOrderStatus;
    @ApiModelProperty(value = "工单状态日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date workOrderStatusDate;
    @ApiModelProperty(value = "所属组织编码")
    private String orgId;
    @ApiModelProperty(value = "所属组织")
    private String orgName;
    @ApiModelProperty(value = "所属站点编码")
    private String siteId;
    @ApiModelProperty(value = "所属站点")
    private String siteName;
    //============== 报修工单提报相关 =================
    @ApiModelProperty(value = "工单id")
    private String id;
    @ApiModelProperty(value = "工单id")
    private String workOrderId;
    @ApiModelProperty(value = "工单编号")
    private String workOrderNum;
    @ApiModelProperty(value = "报修描述")
    private String description;
    @ApiModelProperty(value = "工单来源")
    private String workOrderSource;
    @ApiModelProperty(value = "工程类型")
    private String projectType;
    @ApiModelProperty(value = "事件性质")
    private String incidentNature;
    @ApiModelProperty(value = "事件级别")
    private String incidentLevel;
    //============= 报修工单报修人员相关 ================
    @ApiModelProperty(value = "报修部门")
    private String repairDept;
    @ApiModelProperty(value = "报修人")
    private String repairPerson;
    @ApiModelProperty(value = "联系方式")
    private String repairPersonTel;
    //============= 报修工单提报人员相关 ================
    @ApiModelProperty(value = "是否提报人派单")
    private Boolean reportAssignFlag = false;
    @ApiModelProperty(value = "提报人ID")
    private String reportPersonId;
    @ApiModelProperty(value = "提报人名称")
    private String reportPersonName;
    @ApiModelProperty(value = "提报人电话")
    private String reportPersonTel;
    @ApiModelProperty(value = "提报日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date reportDate;
    @ApiModelProperty(value = "提报说明")
    private String reportDescription;
    //============= 报修工单任务分派相关 ================
    @ApiModelProperty(value = "分派人")
    private String dispatchPersonId;
    @ApiModelProperty(value = "分派人名称")
    private String dispatchPersonName;
    @ApiModelProperty(value = "响应时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date dispatchTime;
    @ApiModelProperty(value = "是否跳过分派环节")
    private Boolean ignoreDispatchFlow = false;
    //============= 报修工单任务分派相关VO字段 ============
    @ApiModelProperty(value = "执行人ID")
    private String executionPersonId;
    @ApiModelProperty(value = "执行人名称")
    private String executionPerson;
    @ApiModelProperty(value = "执行人登陆名称")
    private String executionUserName;
    @ApiModelProperty(value = "执行人电话")
    private String executionPersonTel;
    @ApiModelProperty(value = "执行班组")
    private String executionWorkGroup;
    @ApiModelProperty(value = "是否委托执行")
    private Boolean entrustExecute = false;
    @ApiModelProperty(value = "委托执行人ID")
    private String entrustExecutePersonId;
    @ApiModelProperty(value = "委托执行人")
    private String entrustExecutePerson;
    //=================== 接单 ==================
    @ApiModelProperty(value = "接单人ID", hidden = true)
    private String receivePersonId;
    @ApiModelProperty(value = "接单人")
    private String receivePerson;
    //============= 报修工单执行汇报相关 ================
    @ApiModelProperty(value = "完成时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date completionTime;
    @ApiModelProperty(value = "工时耗时(单位：分钟)")
    private Double consumeHours;
    @ApiModelProperty(value = "是否挂起")
    private Boolean suspension = false;
    @ApiModelProperty(value = "挂起类型")
    private String suspensionType;
    @ApiModelProperty(value = "情况说明")
    private String suspensionCause;
    @ApiModelProperty(value = "实际执行人ID")
    private String actualExecutionPersonId;
    @ApiModelProperty(value = "实际执行人")
    private String actualExecutionPerson;
    @ApiModelProperty(value = "实际执行人电话")
    private String actualExecutionPersonTel;
    @ApiModelProperty(value = "实际执行班组")
    private String actualWorkGroup;
    //============= 报修工单验收确认相关 ================
    @ApiModelProperty(value = "验收人ID")
    private String acceptPersonId;
    @ApiModelProperty(value = "验收人")
    private String acceptPerson;
    @ApiModelProperty(value = "验收时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date acceptTime;
    @ApiModelProperty(value = "确认解决")
    private Boolean confirm;
    @ApiModelProperty(value = "验收说明")
    private String acceptDescription;

    @ApiModelProperty(value = "是否生成维保工单")
    private Boolean createMaintenanceWorkOrder = false;
    @ApiModelProperty(value = "关联维保工单ID")
    private String maintenanceWorkOrderId;
    @ApiModelProperty(value = "关联维保工单编码")
    private String maintenanceWorkOrderNum;

    @ApiModelProperty(value = "维修质量")
    private String maintenanceQuality;
    @ApiModelProperty(value = "维修态度")
    private String maintenanceAttitude;
    //============= 报修工单流程数据相关 ================
    @ApiModelProperty(value = "流程ID", hidden = true)
    private String processInstanceId;
    @ApiModelProperty(value = "流程状态")
    private String processStatus;
    @ApiModelProperty(value = "流程说明")
    private String processDescription;
    //============= 当前流程操作人相关信息 ===============
    @ApiModelProperty(value = "操作人ID", hidden = true)
    private String operatorPersonId;
    @ApiModelProperty(value = "操作人名称", hidden = true)
    private String operatorPerson;
    
    @ApiModelProperty(value = "工号", hidden = true)
    private String jobNumber;
    
    @ApiModelProperty(value = "评论说明", hidden = true)
    private String evaluateDescription;
    
    @ApiModelProperty(value = "是否评论")
    private Boolean evaluate = false;

    @ApiModelProperty(value = "关联设备ID")
    private String refAssetId;

    //============= 工单基础信息 ==== ===============
    @ApiModelProperty(value = "工单创建日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;
    @ApiModelProperty(value = "工单更新日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateDate;
    @ApiModelProperty(value = "工单创建人ID")
    private String createUser;
    //============= 当前环节权限人员列表 ===============
    private List<String> authPersonList;
    //============= get / set ================
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWorkOrderId() {
        return workOrderId;
    }

    public void setWorkOrderId(String workOrderId) {
        this.workOrderId = workOrderId;
    }

    public String getWorkOrderNum() {
        return workOrderNum;
    }

    public void setWorkOrderNum(String workOrderNum) {
        this.workOrderNum = workOrderNum;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getWorkOrderSource() {
        return workOrderSource;
    }

    public void setWorkOrderSource(String workOrderSource) {
        this.workOrderSource = workOrderSource;
    }

    public String getIncidentNature() {
        return incidentNature;
    }

    public void setIncidentNature(String incidentNature) {
        this.incidentNature = incidentNature;
    }

    public String getIncidentLevel() {
        return incidentLevel;
    }

    public void setIncidentLevel(String incidentLevel) {
        this.incidentLevel = incidentLevel;
    }

    public String getWorkOrderStatus() {
        return workOrderStatus;
    }

    public void setWorkOrderStatus(String workOrderStatus) {
        this.workOrderStatus = workOrderStatus;
    }

    public Date getWorkOrderStatusDate() {
        return workOrderStatusDate;
    }

    public void setWorkOrderStatusDate(Date workOrderStatusDate) {
        this.workOrderStatusDate = workOrderStatusDate;
    }

    public String getReportPersonId() {
        return reportPersonId;
    }

    public void setReportPersonId(String reportPersonId) {
        this.reportPersonId = reportPersonId;
    }

    public String getReportPersonName() {
        return reportPersonName;
    }

    public void setReportPersonName(String reportPersonName) {
        this.reportPersonName = reportPersonName;
    }

    public String getReportPersonTel() {
        return reportPersonTel;
    }

    public void setReportPersonTel(String reportPersonTel) {
        this.reportPersonTel = reportPersonTel;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Boolean getReportAssignFlag() {
        return reportAssignFlag;
    }

    public void setReportAssignFlag(Boolean reportAssignFlag) {
        this.reportAssignFlag = reportAssignFlag;
    }

    public String getRepairDept() {
        return repairDept;
    }

    public void setRepairDept(String repairDept) {
        this.repairDept = repairDept;
    }

    public String getRepairPerson() {
        return repairPerson;
    }

    public void setRepairPerson(String repairPerson) {
        this.repairPerson = repairPerson;
    }

    public String getRepairPersonTel() {
        return repairPersonTel;
    }

    public void setRepairPersonTel(String repairPersonTel) {
        this.repairPersonTel = repairPersonTel;
    }

    public String getEntrustExecutePersonId() {
        return entrustExecutePersonId;
    }

    public void setEntrustExecutePersonId(String entrustExecutePersonId) {
        this.entrustExecutePersonId = entrustExecutePersonId;
    }

    public String getEntrustExecutePerson() {
        return entrustExecutePerson;
    }

    public void setEntrustExecutePerson(String entrustExecutePerson) {
        this.entrustExecutePerson = entrustExecutePerson;
    }

    public Boolean getEntrustExecute() {
        return entrustExecute;
    }

    public void setEntrustExecute(Boolean entrustExecute) {
        this.entrustExecute = entrustExecute;
    }

    public String getDispatchPersonId() {
        return dispatchPersonId;
    }

    public void setDispatchPersonId(String dispatchPersonId) {
        this.dispatchPersonId = dispatchPersonId;
    }

    public String getDispatchPersonName() {
        return dispatchPersonName;
    }

    public void setDispatchPersonName(String dispatchPersonName) {
        this.dispatchPersonName = dispatchPersonName;
    }

    public Date getDispatchTime() {
        return dispatchTime;
    }

    public void setDispatchTime(Date dispatchTime) {
        this.dispatchTime = dispatchTime;
    }

    public Boolean getIgnoreDispatchFlow() {
        return ignoreDispatchFlow;
    }

    public void setIgnoreDispatchFlow(Boolean ignoreDispatchFlow) {
        this.ignoreDispatchFlow = ignoreDispatchFlow;
    }

    public String getExecutionPersonId() {
        return executionPersonId;
    }

    public void setExecutionPersonId(String executionPersonId) {
        this.executionPersonId = executionPersonId;
    }

    public String getExecutionPerson() {
        return executionPerson;
    }

    public void setExecutionPerson(String executionPerson) {
        this.executionPerson = executionPerson;
    }

    public String getExecutionWorkGroup() {
        return executionWorkGroup;
    }

    public void setExecutionWorkGroup(String executionWorkGroup) {
        this.executionWorkGroup = executionWorkGroup;
    }

    public String getReceivePersonId() {
        return receivePersonId;
    }

    public void setReceivePersonId(String receivePersonId) {
        this.receivePersonId = receivePersonId;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public Double getConsumeHours() {
        return consumeHours;
    }

    public void setConsumeHours(Double consumeHours) {
        this.consumeHours = consumeHours;
    }

    public Boolean getSuspension() {
        return suspension;
    }

    public void setSuspension(Boolean suspension) {
        this.suspension = suspension;
    }

    public String getSuspensionType() {
        return suspensionType;
    }

    public void setSuspensionType(String suspensionType) {
        this.suspensionType = suspensionType;
    }

    public String getSuspensionCause() {
        return suspensionCause;
    }

    public void setSuspensionCause(String suspensionCause) {
        this.suspensionCause = suspensionCause;
    }

    public String getActualExecutionPersonId() {
        return actualExecutionPersonId;
    }

    public void setActualExecutionPersonId(String actualExecutionPersonId) {
        this.actualExecutionPersonId = actualExecutionPersonId;
    }

    public String getActualExecutionPerson() {
        return actualExecutionPerson;
    }

    public void setActualExecutionPerson(String actualExecutionPerson) {
        this.actualExecutionPerson = actualExecutionPerson;
    }

    public String getActualWorkGroup() {
        return actualWorkGroup;
    }

    public void setActualWorkGroup(String actualWorkGroup) {
        this.actualWorkGroup = actualWorkGroup;
    }

    public String getAcceptPersonId() {
        return acceptPersonId;
    }

    public void setAcceptPersonId(String acceptPersonId) {
        this.acceptPersonId = acceptPersonId;
    }

    public String getAcceptPerson() {
        return acceptPerson;
    }

    public void setAcceptPerson(String acceptPerson) {
        this.acceptPerson = acceptPerson;
    }

    public Date getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(Date acceptTime) {
        this.acceptTime = acceptTime;
    }

    public Boolean getConfirm() {
        return confirm;
    }

    public void setConfirm(Boolean confirm) {
        this.confirm = confirm;
    }

    public String getAcceptDescription() {
        return acceptDescription;
    }

    public void setAcceptDescription(String acceptDescription) {
        this.acceptDescription = acceptDescription;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getProcessDescription() {
        return processDescription;
    }

    public void setProcessDescription(String processDescription) {
        this.processDescription = processDescription;
    }

    public String getOperatorPersonId() {
        return operatorPersonId;
    }

    public void setOperatorPersonId(String operatorPersonId) {
        this.operatorPersonId = operatorPersonId;
    }

    public String getOperatorPerson() {
        return operatorPerson;
    }

    public void setOperatorPerson(String operatorPerson) {
        this.operatorPerson = operatorPerson;
    }

    public List<String> getAuthPersonList() {
        return authPersonList;
    }

    public void setAuthPersonList(List<String> authPersonList) {
        this.authPersonList = authPersonList;
    }

    public String getJobNumber() {
        return jobNumber;
    }

    public void setJobNumber(String jobNumber) {
        this.jobNumber = jobNumber;
    }

    public String getEvaluateDescription() {
        return evaluateDescription;
    }

    public void setEvaluateDescription(String evaluateDescription) {
        this.evaluateDescription = evaluateDescription;
    }

    public Boolean getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(Boolean evaluate) {
        this.evaluate = evaluate;
    }

    public String getExecutionPersonTel() {
        return executionPersonTel;
    }

    public void setExecutionPersonTel(String executionPersonTel) {
        this.executionPersonTel = executionPersonTel;
    }

    public String getActualExecutionPersonTel() {
        return actualExecutionPersonTel;
    }

    public void setActualExecutionPersonTel(String actualExecutionPersonTel) {
        this.actualExecutionPersonTel = actualExecutionPersonTel;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getMaintenanceQuality() {
        return maintenanceQuality;
    }

    public void setMaintenanceQuality(String maintenanceQuality) {
        this.maintenanceQuality = maintenanceQuality;
    }

    public String getMaintenanceAttitude() {
        return maintenanceAttitude;
    }

    public void setMaintenanceAttitude(String maintenanceAttitude) {
        this.maintenanceAttitude = maintenanceAttitude;
    }

    public Boolean getCreateMaintenanceWorkOrder() {
        return createMaintenanceWorkOrder;
    }

    public void setCreateMaintenanceWorkOrder(Boolean createMaintenanceWorkOrder) {
        this.createMaintenanceWorkOrder = createMaintenanceWorkOrder;
    }

    public String getMaintenanceWorkOrderId() {
        return maintenanceWorkOrderId;
    }

    public void setMaintenanceWorkOrderId(String maintenanceWorkOrderId) {
        this.maintenanceWorkOrderId = maintenanceWorkOrderId;
    }

    public String getMaintenanceWorkOrderNum() {
        return maintenanceWorkOrderNum;
    }

    public void setMaintenanceWorkOrderNum(String maintenanceWorkOrderNum) {
        this.maintenanceWorkOrderNum = maintenanceWorkOrderNum;
    }

    public String getExecutionUserName() {
        return executionUserName;
    }

    public void setExecutionUserName(String executionUserName) {
        this.executionUserName = executionUserName;
    }

    public String getRefAssetId() {
        return refAssetId;
    }

    public void setRefAssetId(String refAssetId) {
        this.refAssetId = refAssetId;
    }

    @Override
    public String toString() {
        return "RepairOrderFlowVo{" +
                "workOrderStatus='" + workOrderStatus + '\'' +
                ", workOrderStatusDate=" + workOrderStatusDate +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", siteId='" + siteId + '\'' +
                ", siteName='" + siteName + '\'' +
                ", id='" + id + '\'' +
                ", workOrderId='" + workOrderId + '\'' +
                ", workOrderNum='" + workOrderNum + '\'' +
                ", description='" + description + '\'' +
                ", workOrderSource='" + workOrderSource + '\'' +
                ", projectType='" + projectType + '\'' +
                ", incidentNature='" + incidentNature + '\'' +
                ", incidentLevel='" + incidentLevel + '\'' +
                ", repairDept='" + repairDept + '\'' +
                ", repairPerson='" + repairPerson + '\'' +
                ", repairPersonTel='" + repairPersonTel + '\'' +
                ", reportAssignFlag=" + reportAssignFlag +
                ", reportPersonId='" + reportPersonId + '\'' +
                ", reportPersonName='" + reportPersonName + '\'' +
                ", reportPersonTel='" + reportPersonTel + '\'' +
                ", reportDate=" + reportDate +
                ", reportDescription='" + reportDescription + '\'' +
                ", dispatchPersonId='" + dispatchPersonId + '\'' +
                ", dispatchPersonName='" + dispatchPersonName + '\'' +
                ", dispatchTime=" + dispatchTime +
                ", ignoreDispatchFlow=" + ignoreDispatchFlow +
                ", executionPersonId='" + executionPersonId + '\'' +
                ", executionPerson='" + executionPerson + '\'' +
                ", executionUserName='" + executionUserName + '\'' +
                ", executionPersonTel='" + executionPersonTel + '\'' +
                ", executionWorkGroup='" + executionWorkGroup + '\'' +
                ", entrustExecute=" + entrustExecute +
                ", entrustExecutePersonId='" + entrustExecutePersonId + '\'' +
                ", entrustExecutePerson='" + entrustExecutePerson + '\'' +
                ", receivePersonId='" + receivePersonId + '\'' +
                ", receivePerson='" + receivePerson + '\'' +
                ", completionTime=" + completionTime +
                ", consumeHours=" + consumeHours +
                ", suspension=" + suspension +
                ", suspensionType='" + suspensionType + '\'' +
                ", suspensionCause='" + suspensionCause + '\'' +
                ", actualExecutionPersonId='" + actualExecutionPersonId + '\'' +
                ", actualExecutionPerson='" + actualExecutionPerson + '\'' +
                ", actualExecutionPersonTel='" + actualExecutionPersonTel + '\'' +
                ", actualWorkGroup='" + actualWorkGroup + '\'' +
                ", acceptPersonId='" + acceptPersonId + '\'' +
                ", acceptPerson='" + acceptPerson + '\'' +
                ", acceptTime=" + acceptTime +
                ", confirm=" + confirm +
                ", acceptDescription='" + acceptDescription + '\'' +
                ", createMaintenanceWorkOrder=" + createMaintenanceWorkOrder +
                ", maintenanceWorkOrderId='" + maintenanceWorkOrderId + '\'' +
                ", maintenanceWorkOrderNum='" + maintenanceWorkOrderNum + '\'' +
                ", maintenanceQuality='" + maintenanceQuality + '\'' +
                ", maintenanceAttitude='" + maintenanceAttitude + '\'' +
                ", processInstanceId='" + processInstanceId + '\'' +
                ", processStatus='" + processStatus + '\'' +
                ", processDescription='" + processDescription + '\'' +
                ", operatorPersonId='" + operatorPersonId + '\'' +
                ", operatorPerson='" + operatorPerson + '\'' +
                ", jobNumber='" + jobNumber + '\'' +
                ", evaluateDescription='" + evaluateDescription + '\'' +
                ", evaluate=" + evaluate +
                ", refAssetId='" + refAssetId + '\'' +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", createUser='" + createUser + '\'' +
                ", authPersonList=" + authPersonList +
                '}';
    }
}
