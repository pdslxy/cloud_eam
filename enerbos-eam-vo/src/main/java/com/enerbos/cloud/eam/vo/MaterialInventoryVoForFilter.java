package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年3月31日 下午5:11:28
 * @Description 物资库存实体类
 */
@ApiModel(value = "物资库存条件过滤实体")
public class MaterialInventoryVoForFilter extends EAMBaseFilterVo implements Serializable {

    /**
     * 物资
     */
    @ApiModelProperty(value = "物资编码")
    private String itemNum;

    /**
     * 物资名称
     */
    @ApiModelProperty(value = "物资名称")
    private String itemName;

    /**
     * 库房
     */
    @ApiModelProperty(value = "库房 ")
    private String storeroomNum;

    /**
     * 库房
     */
    @ApiModelProperty(value = "库房名称 ")
    private String storeroomName;

    /**
     * 发放单位
     */
    @ApiModelProperty(value = "发放单位")
    private String issueUnit;

    /**
     * 重订购
     */
    @ApiModelProperty(value = "重订购")
    private Boolean reorder;

    /**
     * 安全库存
     */
    @ApiModelProperty(value = "安全库存")
    private String safeStock;

    /**
     * 重订购点
     */
    @ApiModelProperty(value = "重订购点")
    private String reorderPoint;

    /**
     *
     */
    @ApiModelProperty(value = "状态")
    private List<String> status;

    /**
     * 发放成本类型
     */
    @ApiModelProperty(value = "发放成本类型")
    private List<String> costTypeId;

    /**
     * ABC 类型
     */
    @ApiModelProperty(value = "ABC 类型 ")
    private List<String> abcType;

    @ApiModelProperty(value = "收藏")
    private Boolean collect;

    @ApiModelProperty("开始时间")
    private String startDate;

    @ApiModelProperty("结束时间")
    private String endDate;
    /**
     * 模糊搜索关键词
     */
    @ApiModelProperty(value = "模糊搜索关键词")
    private String word;


    public String getItemNum() {
        return itemNum;
    }

    public void setItemNum(String itemNum) {
        this.itemNum = itemNum;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getStoreroomNum() {
        return storeroomNum;
    }

    public void setStoreroomNum(String storeroomNum) {
        this.storeroomNum = storeroomNum;
    }

    public String getStoreroomName() {
        return storeroomName;
    }

    public void setStoreroomName(String storeroomName) {
        this.storeroomName = storeroomName;
    }

    public String getIssueUnit() {
        return issueUnit;
    }

    public void setIssueUnit(String issueUnit) {
        this.issueUnit = issueUnit;
    }

    public Boolean getReorder() {
        return reorder;
    }

    public void setReorder(Boolean reorder) {
        this.reorder = reorder;
    }

    public String getSafeStock() {
        return safeStock;
    }

    public void setSafeStock(String safeStock) {
        this.safeStock = safeStock;
    }

    public String getReorderPoint() {
        return reorderPoint;
    }

    public void setReorderPoint(String reorderPoint) {
        this.reorderPoint = reorderPoint;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getCostTypeId() {
        return costTypeId;
    }

    public void setCostTypeId(List<String> costTypeId) {
        this.costTypeId = costTypeId;
    }

    public List<String> getAbcType() {
        return abcType;
    }

    public void setAbcType(List<String> abcType) {
        this.abcType = abcType;
    }

    public Boolean getCollect() {
        return collect;
    }

    public void setCollect(Boolean collect) {
        this.collect = collect;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "MaterialInventoryVoForFilter{" +
                "itemNum='" + itemNum + '\'' +
                ", itemName='" + itemName + '\'' +
                ", storeroomNum='" + storeroomNum + '\'' +
                ", storeroomName='" + storeroomName + '\'' +
                ", issueUnit='" + issueUnit + '\'' +
                ", reorder=" + reorder +
                ", safeStock='" + safeStock + '\'' +
                ", reorderPoint='" + reorderPoint + '\'' +
                ", status=" + status +
                ", costTypeId=" + costTypeId +
                ", abcType=" + abcType +
                ", collect=" + collect +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
