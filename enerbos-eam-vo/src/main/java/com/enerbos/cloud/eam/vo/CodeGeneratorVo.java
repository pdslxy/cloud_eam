package com.enerbos.cloud.eam.vo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

import io.swagger.annotations.ApiModel;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年6月30日
 * @Description 获取编码
 */
@ApiModel(value = "工单实际物料VO", description = "工单实际物料的实体对应的VO")
public class CodeGeneratorVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 组织ID
     */
    @Column
    private String orgId;
	
	/**
     * 站点ID
     */
    @Column
    private String siteId;
    
    /**
     * 模块编码
     */
    @Column
    private String modelKey;
    
    /**
     * 前缀
     */
    @Column
    private String prifix;
    
    /**
     * 分隔符
     */
    @Column
    private String delimiter;
    
    /**
     * 当前编号
     */
    @Column
    private String currentCode;
    
    /**
     * 具体实现类全路径
     */
    @Column
    private String implClass;
    
    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    public CodeGeneratorVo() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getCurrentCode() {
        return currentCode;
    }

    public void setCurrentCode(String currentCode) {
        this.currentCode = currentCode;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getImplClass() {
        return implClass;
    }

    public void setImplClass(String implClass) {
        this.implClass = implClass;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public String getPrifix() {
        return prifix;
    }

    public void setPrifix(String prifix) {
        this.prifix = prifix;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "CodeGeneratorVo{" +
                "id='" + id + '\'' +
                ", orgId='" + orgId + '\'' +
                ", siteId='" + siteId + '\'' +
                ", modelKey='" + modelKey + '\'' +
                ", prifix='" + prifix + '\'' +
                ", delimiter='" + delimiter + '\'' +
                ", currentCode='" + currentCode + '\'' +
                ", implClass='" + implClass + '\'' +
                ", version=" + version +
                '}';
    }
}
