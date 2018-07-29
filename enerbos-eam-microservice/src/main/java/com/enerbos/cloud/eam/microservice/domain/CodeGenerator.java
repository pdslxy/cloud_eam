package com.enerbos.cloud.eam.microservice.domain;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年6月30日 下午3:39:22
 * @Description 获取编码对应实体
 */
@Entity
@EntityListeners({AuditingEntityListener.class})
@Table(name = "eam_code_generator")
public class CodeGenerator implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(unique = true, nullable = false, length = 36)
    private String id;

    /**
     * 所属站点ID
     */
    @Column(name = "site_id",nullable = true, length = 36)
    private String siteId;

    /**
     * 所属组织ID
     */
    @Column(name = "org_id", nullable = true,length = 36)
    private String orgId;
    
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

    public CodeGenerator() {}

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
        return "CodeGenerator{" +
                "id='" + id + '\'' +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", modelKey='" + modelKey + '\'' +
                ", prifix='" + prifix + '\'' +
                ", delimiter='" + delimiter + '\'' +
                ", currentCode='" + currentCode + '\'' +
                ", implClass='" + implClass + '\'' +
                ", version=" + version +
                '}';
    }
}
