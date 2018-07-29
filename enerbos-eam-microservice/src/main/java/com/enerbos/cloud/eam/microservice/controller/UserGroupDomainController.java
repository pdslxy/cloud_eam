package com.enerbos.cloud.eam.microservice.controller;


import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.microservice.service.UserGroupDomainService;
import com.enerbos.cloud.eam.vo.UserGroupDomainFilterVo;
import com.enerbos.cloud.eam.vo.UserGroupDomainVo;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
public class UserGroupDomainController {
    private static final Logger logger = LoggerFactory.getLogger(UserGroupDomainController.class);
    @Autowired
    private UserGroupDomainService userGroupDomainService;

    /**
     * 根据过滤条和分页信息获取列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/getList", method = RequestMethod.POST)
    public PageInfo<UserGroupDomainVo> getList(@RequestBody UserGroupDomainFilterVo filterVo) {
        PageInfo<UserGroupDomainVo> pageInfo = userGroupDomainService.getList(filterVo);
        return pageInfo;
    }

    /**
     * 删除
     *
     * @param ids 的集合
     * @return 结果
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/delete", method = RequestMethod.POST)
    public boolean delete(@RequestParam(value = "ids", required = false) String[] ids) {
        try {
            userGroupDomainService.delete(ids);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * 新建
     *
     * @param vo 新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/save", method = RequestMethod.POST)
    public UserGroupDomainVo saveip(@RequestBody @Valid UserGroupDomainVo vo) {
        return userGroupDomainService.save(vo);
    }

    /**
     * 查询IP的详细信息
     *
     * @param id
     * @return 返回数据的实体
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/findDetail", method = RequestMethod.GET)
    public UserGroupDomainVo findDetail(@RequestParam("id") String id) {
        UserGroupDomainVo domainVo = userGroupDomainService.findetail(id);
        return domainVo;
    }

    /**
     * 根据域值value，域num查询该域值value对应的用户组
     * @param domainValue 域值
     * @param domainNum 域
     * @param orgId 组织
     * @param siteId 站点
     * @return
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/findUserGroupDomainByDomainValueAndDomainNum", method = RequestMethod.GET)
    public UserGroupDomainVo findUserGroupDomainByDomainValueAndDomainNum(
            @ApiParam(value = "域值Value", required = true)@RequestParam("domainValue") String domainValue,
            @ApiParam(value = "域num", required = false)@RequestParam("domainNum") String domainNum,
            @ApiParam(value = "组织id", required = true)@RequestParam("orgId") String orgId,
            @ApiParam(value = "站点Id", required = true)@RequestParam("siteId") String siteId,
            @ApiParam(value = "关联类型", required = true)@RequestParam("associationType") String associationType
    ) {
        logger.info("根据域value查询组");
        UserGroupDomainVo domainVo = userGroupDomainService.findUserGroupDomainByDomainValueAndDomainNum(domainValue,domainNum,orgId,siteId,associationType);
        return domainVo;
    }

    /**
     * 批量保存用户组和域关系
     * @param initEamSet
     * @return
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/saveBatchUserGroupDomain", method = RequestMethod.POST)
    Boolean saveBatchUserGroupDomain(@RequestBody InitEamSet initEamSet) throws  Exception{

        userGroupDomainService.saveBatchUserGroupDomain(initEamSet.getUserGroupDomainVos()) ;
        return true ;
    }


}