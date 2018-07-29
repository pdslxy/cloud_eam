package com.enerbos.cloud.eam.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * Created by Enerbos on 2016/10/17.
 */

@ApiModel(value = "IP管理条件过滤实体")
public class SafeIPVoForFilter extends EAMBaseFilterVo {

    @ApiModelProperty(value = "能源价格ID(新增不需要传值)")
    private String id;


    /**
     * IP地址
     */
    @ApiModelProperty(value = "ip地址")
    private String ip;


    /**
     * ip状态，可选项：生效/禁用
     */
    @ApiModelProperty(value = "ip状态")
    private String status;


    /**
     * 创建人
     */
    @ApiModelProperty(value = "备注(最长不能超过36个字节)")
    private String creator;


    /**
     * 创建日期
     */
    @ApiModelProperty(value = "创建日期(新增、修改不用传值)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createDate;


    /**
     * 备注
     */
    @ApiModelProperty(value = "备注(最长不能超过500个字节)")
    private String remark;


    @ApiModelProperty(value = "模糊查询关键词")
    private String word;

    private List<String> wordsList;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public List<String> getWordsList() {
        return wordsList;
    }

    public void setWordsList(List<String> wordsList) {
        this.wordsList = wordsList;
    }

    @Override
    public String toString() {
        return "SafeIPVoForFilter{" +
                "id='" + id + '\'' +
                ", ip='" + ip + '\'' +
                ", status='" + status + '\'' +
                ", creator='" + creator + '\'' +
                ", createDate=" + createDate +
                ", remark='" + remark + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
