package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2016-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年9月9日
 * @Description 施工单-监体管说明Vo
 */
@ApiModel(value = "施工单-监体管说明", description = "施工单-监体管说明对应的Vo")
public class ConstructionSuperviseVo implements Serializable {

    /**
     * id
     */
    @ApiModelProperty(value = "id(新增不用传值)")
    @Length(max = 36, message = "id不能超过36个字符")
    private String id;

    /**
     * 监管时间
     */
    @ApiModelProperty("监管时间")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date superviseDate;

    /**
     * 监管情况说明
     */
    @ApiModelProperty("监管情况说明")
    private String superviseDesc;

    /**
     * 施工单标识，与监管情况说明为一对多的关系
     */
    @ApiModelProperty(value = "施工单标识，与监管情况说明为一对多的关系",hidden = true)
    private String constructionId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getSuperviseDate() {
        return superviseDate;
    }

    public void setSuperviseDate(Date superviseDate) {
        this.superviseDate = superviseDate;
    }

    public String getSuperviseDesc() {
        return superviseDesc;
    }

    public void setSuperviseDesc(String superviseDesc) {
        this.superviseDesc = superviseDesc;
    }

    public String getConstructionId() {
        return constructionId;
    }

    public void setConstructionId(String constructionId) {
        this.constructionId = constructionId;
    }

    @Override
    public String toString() {
        return "ConstructionSuperviseVo{" +
                "id='" + id + '\'' +
                ", superviseDate=" + superviseDate +
                ", superviseDesc='" + superviseDesc + '\'' +
                ", constructionId='" + constructionId + '\'' +
                '}';
    }
}
