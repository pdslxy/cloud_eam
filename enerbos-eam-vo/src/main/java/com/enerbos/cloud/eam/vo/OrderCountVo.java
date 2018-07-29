package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/14.
 */

@ApiModel(value = "报修工单统计分析Vo", description = "报修工单统计分析Vo")
public class OrderCountVo implements Serializable {

    @ApiModelProperty(value = "总量")
    private List<String> listme;
    
    @ApiModelProperty(value = "其他")
    private List<String> listqt;

    @ApiModelProperty(value = "未完成数量")
    private List<String> listwxf;
    
    @ApiModelProperty(value = "供应商原因")
    private List<String> listgys;
    
    @ApiModelProperty(value = "缺备件原因")
    private List<String> listqbj;
    
    @ApiModelProperty(value = "已完成数量")
    private List<String> listyxf;

    @ApiModelProperty(value = "执行中数量")
    private List<String> listzxz;


    public List<String> getListme() {
        return listme;
    }

    public void setListme(List<String> listme) {
        this.listme = listme;
    }

    public List<String> getListqt() {
        return listqt;
    }

    public void setListqt(List<String> listqt) {
        this.listqt = listqt;
    }

    public List<String> getListwxf() {
        return listwxf;
    }

    public void setListwxf(List<String> listwxf) {
        this.listwxf = listwxf;
    }

    public List<String> getListgys() {
        return listgys;
    }

    public void setListgys(List<String> listgys) {
        this.listgys = listgys;
    }

    public List<String> getListqbj() {
        return listqbj;
    }

    public void setListqbj(List<String> listqbj) {
        this.listqbj = listqbj;
    }

    public List<String> getListyxf() {
        return listyxf;
    }

    public void setListyxf(List<String> listyxf) {
        this.listyxf = listyxf;
    }

    public List<String> getListzxz() {
        return listzxz;
    }

    public void setListzxz(List<String> listzxz) {
        this.listzxz = listzxz;
    }

    @Override
    public String toString() {
        return "OrderCountVo{" +
                "listme=" + listme +
                ", listqt=" + listqt +
                ", listwxf=" + listwxf +
                ", listgys=" + listgys +
                ", listqbj=" + listqbj +
                ", listyxf=" + listyxf +
                ", listzxz=" + listzxz +
                '}';
    }
}
