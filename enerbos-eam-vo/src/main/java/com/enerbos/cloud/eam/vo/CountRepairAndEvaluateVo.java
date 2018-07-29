package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/20.
 */
@ApiModel(value = "报修小程序首页统计已完成，为分派，处理中，已评价对应的VO", description = "报修小程序首页统计已完成，为分派，处理中，已评价对应的VO")
public class CountRepairAndEvaluateVo {

    @ApiModelProperty(value = "已完成数量")
    private String ywc;

    @ApiModelProperty(value = "未分派数量")
    private String wfp;

    @ApiModelProperty(value = "处理中数量")
    private String clz;

    @ApiModelProperty(value = "已评价数量")
    private String ypj;


    public String getYwc() {
        return ywc;
    }

    public void setYwc(String ywc) {
        this.ywc = ywc;
    }

    public String getWfp() {
        return wfp;
    }

    public void setWfp(String wfp) {
        this.wfp = wfp;
    }

    public String getClz() {
        return clz;
    }

    public void setClz(String clz) {
        this.clz = clz;
    }

    public String getYpj() {
        return ypj;
    }

    public void setYpj(String ypj) {
        this.ypj = ypj;
    }

    @Override
    public String toString() {
        return "CountRepairAndEvaluateVo{" +
                "ywc='" + ywc + '\'' +
                ", wfp='" + wfp + '\'' +
                ", clz='" + clz + '\'' +
                ", ypj='" + ypj + '\'' +
                '}';
    }
}
