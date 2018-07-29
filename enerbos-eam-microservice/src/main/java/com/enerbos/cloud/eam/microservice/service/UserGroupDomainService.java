package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.UserGroupDomainFilterVo;
import com.enerbos.cloud.eam.vo.UserGroupDomainVo;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Enerbos on 2016/10/17.
 */
public interface UserGroupDomainService {

    /**
     * 根据筛选条件和分页信息获取数据列表
     * @return
     */
    public PageInfo<UserGroupDomainVo> getList(UserGroupDomainFilterVo vo) ;

    /**
     * 新建
     * @param vo 新建的实体
     * @return 返回添加的实体
     */
    public abstract UserGroupDomainVo save(UserGroupDomainVo vo);


    /**
     * 删除
     * @param ids 数组
     */
    public abstract void delete(String ids[]);

    /**
     * 获取详情
     * @param id
     * @return
     */

    public UserGroupDomainVo findetail(String id);

    /**
     * 根据域值value，域num查询该域值value对应的用户组
     * @param domainValue 域值
     * @param domainNum 域
     * @param orgId 组织
     * @param siteId 站点
     * @return
     */
    public UserGroupDomainVo findUserGroupDomainByDomainValueAndDomainNum( String domainValue,String domainNum,String orgId,String siteId,String associationType);

    /**
     *
     * @param userGroupDomainVos
     */
    void saveBatchUserGroupDomain(List<UserGroupDomainVo> userGroupDomainVos) throws Exception;
}
