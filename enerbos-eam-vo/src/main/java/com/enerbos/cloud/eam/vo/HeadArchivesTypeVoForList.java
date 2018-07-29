package com.enerbos.cloud.eam.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Enerbos on 2016/10/17.
 */
@ApiModel(value = "档案类型管理")
public class HeadArchivesTypeVoForList implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -1205663637397029364L;
    /**
     * id
     */
    @ApiModelProperty(value = "档案类型ID(新增不需要传值)")
    private String id;

    /**
     * 类型名称
     */
    @ApiModelProperty(value = "类型名称id")
    private String typeName;

    /**
     * 父节点id
     */
    @ApiModelProperty(value = "父节点id")
    private String parentId;

    /**
     * 是否有子节点
     */
    @ApiModelProperty(value = "是否有子节点")
    private boolean hasChild;

    /**
     * 是否为根目录
     */
    @ApiModelProperty(value = "是否为根目录")
    private boolean isRoot;

    /**
     * 站点
     */
    @ApiModelProperty(value = "站点")
    private String siteId;
    /**
     * 组织
     */
    @ApiModelProperty(value = "组织")
    private String orgId;


    @ApiModelProperty(hidden = true)
    private List<HeadArchivesTypeVoForList> children;


    public List<HeadArchivesTypeVoForList> getChildren() {
        return children;
    }

    public void setChildren(List<HeadArchivesTypeVoForList> children) {
        this.children = children;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public boolean isHasChild() {
        return hasChild;
    }

    public void setHasChild(boolean hasChild) {
        this.hasChild = hasChild;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Override
    public String toString() {
        return "HeadArchivesTypeVoForList{" +
                "id='" + id + '\'' +
                ", typeName='" + typeName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", hasChild=" + hasChild +
                ", isRoot=" + isRoot +
                ", siteId='" + siteId + '\'' +
                ", orgId='" + orgId + '\'' +
                ", children=" + children +
                '}';
    }
}
