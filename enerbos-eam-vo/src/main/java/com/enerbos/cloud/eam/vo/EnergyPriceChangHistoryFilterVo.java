package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * Created by Enerbos on 2016/10/19.
 */

@ApiModel(value = "能源价格变更历史", description = "能源价格变更历史")
public class EnergyPriceChangHistoryFilterVo extends EAMBaseFilterVo {


    @ApiModelProperty(value = "id(能源价格ID)")
    private String  eneryPriceId;


    public String getEneryPriceId() {
        return eneryPriceId;
    }

    public void setEneryPriceId(String eneryPriceId) {
        this.eneryPriceId = eneryPriceId;
    }
}
