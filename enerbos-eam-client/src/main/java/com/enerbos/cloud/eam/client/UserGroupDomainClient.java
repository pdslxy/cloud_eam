package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.vo.UserGroupDomainFilterVo;
import com.enerbos.cloud.eam.vo.UserGroupDomainVo;
import feign.hystrix.FallbackFactory;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/16 13:53
 * @Description:
 */

@FeignClient(name = "enerbos-eam-microservice",url = "${eam.microservice.url:}", fallbackFactory = UserGroupDomainClientFallback.class)
public interface UserGroupDomainClient {

    /**
     * 根据过滤条和分页信息获取列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/getList", method = RequestMethod.POST)
    EnerbosPage<UserGroupDomainVo> getList(@RequestBody UserGroupDomainFilterVo userGroupDomainFilterVo);
    /**

     * @param ids
     * @return
     */
    @RequestMapping(value="/eam/micro/userGroupDomain/delete",method = RequestMethod.POST)
     boolean delete(@RequestParam("ids") String []ids);



    /**
     * 新建&修改
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/save", method = RequestMethod.POST)
     UserGroupDomainVo save(@RequestBody  UserGroupDomainVo vo);

    /**
     * 查询IP详细信息
     *
     * @param id
     *            id
     * @return 返回IP实体
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/findDetail", method = RequestMethod.GET)
    UserGroupDomainVo findDetail(@RequestParam("id") String id);


    /**
     * 根据域值value，域num查询该域值value对应的用户组
     * @param domainValue 域值
     * @param domainNum 域
     * @param orgId 组织
     * @param siteId 站点
     * @return
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/findUserGroupDomainByDomainValueAndDomainNum", method = RequestMethod.GET)
    UserGroupDomainVo findUserGroupDomainByDomainValueAndDomainNum(
            @ApiParam(value = "域值Value", required = true)@RequestParam("domainValue") String domainValue,
            @ApiParam(value = "域num", required = false)@RequestParam("domainNum") String domainNum,
            @ApiParam(value = "组织id", required = true)@RequestParam("orgId") String orgId,
            @ApiParam(value = "站点Id", required = true)@RequestParam("siteId") String siteId,
            @ApiParam(value = "关联类型", required = true)@RequestParam("associationType") String associationType
    );

    /**
     * 批量保存用户组和域关系
     * @param initEamSet
     * @return
     */
    @RequestMapping(value = "/eam/micro/userGroupDomain/saveBatchUserGroupDomain", method = RequestMethod.POST)
    Boolean saveBatchUserGroupDomain(@RequestBody  InitEamSet initEamSet);
}

@Component
class UserGroupDomainClientFallback implements FallbackFactory<UserGroupDomainClient>{
    @Override
    public UserGroupDomainClient create(Throwable throwable) {
        return new UserGroupDomainClient() {
            @Override
            public EnerbosPage<UserGroupDomainVo> getList(@RequestParam UserGroupDomainFilterVo userGroupDomainFilterVo) {
                return null;
            }

            @Override
            public boolean delete(@RequestParam(value = "ids", required = false) String[] ids) {
                return false;
            }

            @Override
            public UserGroupDomainVo save(@RequestBody UserGroupDomainVo vo) {
                return null;
            }

            @Override
            public UserGroupDomainVo findDetail(@RequestParam("id") String id) {
                return null;
            }

            @Override
            public UserGroupDomainVo findUserGroupDomainByDomainValueAndDomainNum(
                    @ApiParam(value = "域值Value", required = true) @RequestParam("domainValue") String domainValue,
                    @ApiParam(value = "域num", required = false) @RequestParam("domainNum") String domainNum,
                    @ApiParam(value = "组织id", required = true) @RequestParam("orgId") String orgId,
                    @ApiParam(value = "站点Id", required = true) @RequestParam("siteId") String siteId,
                    @ApiParam(value = "关联类型", required = true)@RequestParam("associationType") String associationType
            ) {
                return null;
            }

            /**
             * 批量保存用户组和域关系
             *
             * @param initEamSet
             * @return
             */
            @Override
            public Boolean saveBatchUserGroupDomain(@RequestBody InitEamSet initEamSet) {
                return null;
            }
        };
    }
}
