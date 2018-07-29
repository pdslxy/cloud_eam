package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/15.
 */
@ApiModel(value = "按站点查询工单总数，未完成，已完成等vo", description = "按站点查询工单总数，未完成，已完成等vo")
public class OrderCountBySiteVo implements Serializable{

    @ApiModelProperty(value = "总量")
    private String total="0";

    @ApiModelProperty(value = "未完成数量")
    private String wwc="0";

    @ApiModelProperty(value = "已完成数量")
    private String ywc="0";

    @ApiModelProperty(value = "挂起数量")
    private String gq="0";

    @ApiModelProperty(value = "待分派数量")
    private String dfp="0";

    @ApiModelProperty(value = "待汇报数量")
    private String dhb="0";

    @ApiModelProperty(value = "待接单数量")
    private String djd="0";

    @ApiModelProperty(value = "待验收数量")
    private String dys="0";

    @ApiModelProperty(value = "取消数量")
    private String qx="0";

    @ApiModelProperty(value = "待提报数量")
    private String dtb="0";

    @ApiModelProperty(value = "申请挂起数量")
    private String sqgq="0";

    @ApiModelProperty(value = "验收待确认数量")
    private String ysdqr="0";

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWwc() {
        return wwc;
    }

    public void setWwc(String wwc) {
        this.wwc = wwc;
    }

    public String getYwc() {
        return ywc;
    }

    public void setYwc(String ywc) {
        this.ywc = ywc;
    }

    public String getGq() {
        return gq;
    }

    public void setGq(String gq) {
        this.gq = gq;
    }

    public String getDfp() {
        return dfp;
    }

    public void setDfp(String dfp) {
        this.dfp = dfp;
    }

    public String getDhb() {
        return dhb;
    }

    public void setDhb(String dhb) {
        this.dhb = dhb;
    }

    public String getDjd() {
        return djd;
    }

    public void setDjd(String djd) {
        this.djd = djd;
    }

    public String getDys() {
        return dys;
    }

    public void setDys(String dys) {
        this.dys = dys;
    }

    public String getQx() {
        return qx;
    }

    public void setQx(String qx) {
        this.qx = qx;
    }

    public String getDtb() {
        return dtb;
    }

    public void setDtb(String dtb) {
        this.dtb = dtb;
    }

    public String getSqgq() {
        return sqgq;
    }

    public void setSqgq(String sqgq) {
        this.sqgq = sqgq;
    }

    public String getYsdqr() {
        return ysdqr;
    }

    public void setYsdqr(String ysdqr) {
        this.ysdqr = ysdqr;
    }


    @Override
    public String toString() {
        return "OrderCountBySiteVo{" +
                "total='" + total + '\'' +
                ", wwc='" + wwc + '\'' +
                ", ywc='" + ywc + '\'' +
                ", gq='" + gq + '\'' +
                ", dfp='" + dfp + '\'' +
                ", dhb='" + dhb + '\'' +
                ", djd='" + djd + '\'' +
                ", dys='" + dys + '\'' +
                ", qx='" + qx + '\'' +
                ", dtb='" + dtb + '\'' +
                ", sqgq='" + sqgq + '\'' +
                ", ysdqr='" + ysdqr + '\'' +
                '}';
    }
}
