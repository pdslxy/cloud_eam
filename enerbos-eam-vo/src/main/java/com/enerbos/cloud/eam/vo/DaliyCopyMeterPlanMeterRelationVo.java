package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年11月16日 16:34:43
 * @Description 抄表计划与仪表台帐关系
 */
@ApiModel(value = "抄表计划与仪表台帐关系")
public class DaliyCopyMeterPlanMeterRelationVo extends EAMBaseFilterVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7983360432330979802L;

    @ApiModelProperty(value = "唯一标识")
    private String id;
    /**
     * 仪表台帐id
     */
    @ApiModelProperty(value = "仪表台帐id")
    private String meterId;

    /**
     * 抄表计划id
     */
    @ApiModelProperty(value = "抄表计划id")
    private String copymeterPlanId;

    @ApiModelProperty(value = "创建人")
    private String createUser ;


    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    @Override
    public String toString() {
        return "DaliyCopyMeterPlanMeterRelationVo{" +
                "id='" + id + '\'' +
                ", meterId='" + meterId + '\'' +
                ", copymeterPlanId='" + copymeterPlanId + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeterId() {
        return meterId;
    }

    public void setMeterId(String meterId) {
        this.meterId = meterId;
    }

    public String getCopymeterPlanId() {
        return copymeterPlanId;
    }

    public void setCopymeterPlanId(String copymeterPlanId) {
        this.copymeterPlanId = copymeterPlanId;
    }
}
