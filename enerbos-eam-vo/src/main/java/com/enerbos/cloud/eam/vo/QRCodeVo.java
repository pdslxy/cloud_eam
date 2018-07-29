package com.enerbos.cloud.eam.vo;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年08月02日
 * @Description
 */
public class QRCodeVo implements Serializable {
	private static final long serialVersionUID = 1L;

    /**
     * 数据应用程序name
     */
    private String modelName;

    /**
     * 备注
     */
    private String remark;

    /**
     * 包含内容,二维码包含内容，ID和applicationValue 二维码应用程序值的map转string
     */
    private String content;

    /**
     * 标题
     */
    private String title;

    /**
     * 二维码右侧的基本信息列表
     */
    private List<String> infolist;

    /**
     * 二维码编码
     */
    private String QRCodeNum;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getInfolist() {
        return infolist;
    }

    public void setInfolist(List<String> infolist) {
        this.infolist = infolist;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getQRCodeNum() {
        return QRCodeNum;
    }

    public void setQRCodeNum(String QRCodeNum) {
        this.QRCodeNum = QRCodeNum;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    @Override
    public String toString() {
        return "QRCodeVo{" +
                "modelName='" + modelName + '\'' +
                ", remark='" + remark + '\'' +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                ", infolist=" + infolist +
                ", QRCodeNum='" + QRCodeNum + '\'' +
                '}';
    }
}
