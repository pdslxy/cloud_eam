package com.enerbos.cloud.eam.contants;

import com.enerbos.cloud.eam.vo.*;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 严作宇
 * @version 1.0
 * @date 17/10/27 上午10:24
 * @Description
 */
public class InitEamSet {

    /**
     * 用户组和域关系
     */
    private List<UserGroupDomainVo> userGroupDomainVos ;
    /**
     * 档案管理
     */
    private List<HeadArchivesVo> headArchivesVoList ;

    /**
     * 档案类型
     */
    private List<HeadArchivesTypeVo> headArchivesTypeVos ;

    /**
     * 标准
     */
    private List<MaintenanceJobStandardVo> mjsVos ;


    /**
     * 预防维护
     */
    private List<MaintenanceMaintenancePlanVo> mmpVos ;

    /**
     * 编码规则
     */
    List<CodeGeneratorVo> codeGeneratorVos ;

    public List<MaintenanceMaintenancePlanVo> getMmpVos() {
        return mmpVos;
    }

    public void setMmpVos(List<MaintenanceMaintenancePlanVo> mmpVos) {
        this.mmpVos = mmpVos;
    }

    public List<MaintenanceJobStandardVo> getMjsVos() {
        return mjsVos;
    }

    public void setMjsVos(List<MaintenanceJobStandardVo> mjsVos) {
        this.mjsVos = mjsVos;
    }

    public List<HeadArchivesVo> getHeadArchivesVoList() {
        return headArchivesVoList;
    }

    public void setHeadArchivesVoList(List<HeadArchivesVo> headArchivesVoList) {
        this.headArchivesVoList = headArchivesVoList;
    }

    public List<CodeGeneratorVo> getCodeGeneratorVos() {
        return codeGeneratorVos;
    }

    public void setCodeGeneratorVos(List<CodeGeneratorVo> codeGeneratorVos) {
        this.codeGeneratorVos = codeGeneratorVos;
    }

    public List<UserGroupDomainVo> getUserGroupDomainVos() {
        return userGroupDomainVos;
    }

    public void setUserGroupDomainVos(List<UserGroupDomainVo> userGroupDomainVos) {
        this.userGroupDomainVos = userGroupDomainVos;
    }

    public List<HeadArchivesTypeVo> getHeadArchivesTypeVos() {
        return headArchivesTypeVos;
    }

    public void setHeadArchivesTypeVos(List<HeadArchivesTypeVo> headArchivesTypeVos) {
        this.headArchivesTypeVos = headArchivesTypeVos;
    }
}
