package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.ams.vo.field.FieldDomainVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.UserGroupDomainClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.vo.UserGroupDomainVo;
import com.enerbos.cloud.uas.client.OrgClient;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.client.UgroupClient;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.util.Json;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @version 1.0
 * @author: 张鹏伟
 * @Date: 2017/8/28 11:17
 * @Description:
 */
@RestController
@Api(description = "eam用户组域关联")
public class UserGroupDomainColler {

    private Logger logger = LoggerFactory.getLogger(UserGroupDomainColler.class);

    @Autowired
    private UserGroupDomainClient userGroupDomainClient;
    @Autowired
    private SiteClient siteClient;
    @Autowired
    private OrgClient orgClient;
    @Autowired
    private UgroupClient ugroupClient;
    @Autowired
    private FieldDomainClient fieldDomainClient;
    @Autowired
    private PersonAndUserClient personAndUserClient;




//    /**
//     * 根据过滤条和分页信息获取IP列表
//     *
//     * @return
//     */
//    @ApiOperation(value = "分页查询列表", response = UserGroupDomainVo.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
//    //  @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "filters", value = "所有查询条件")})
//    @RequestMapping(value = "/eam/open/userGroupDomain/getList", method = RequestMethod.GET)
//    public EnerbosMessage getList(UserGroupDomainFilterVo filterV,Principal user) {
//        try {
//
//            //根据组查询
//            //根据域值查询
//            //根据域查询
//            //支持ALL
//           EnerbosPage<UserGroupDomainVo> pageInfo = userGroupDomainClient.getList(filterV);
//
//            pageInfo.getList().forEach(vo -> {
//                if (StringUtils.isNotEmpty(vo.getSiteId())&&!vo.getSiteId().equals(Common.USERGROUP_ASSOCIATION_TYPE_ALL)) {
//                    SiteVoForDetail siteVo=siteClient.findById(vo.getSiteId());
//                    vo.setSiteName(siteVo.getName());
//                    vo.setOrgName(siteVo.getOrgName());
//                }
//                if(vo.getSiteId().equals(Common.USERGROUP_ASSOCIATION_TYPE_ALL)){
//                    vo.setSiteName(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
//                }
//                if(vo.getOrgId().equals(Common.USERGROUP_ASSOCIATION_TYPE_ALL)){
//                    vo.setOrgName(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
//                }else{
//                    OrgVoForDetail orgVoForDetail=  orgClient.findById(vo.getOrgId());
//                    vo.setOrgName(orgVoForDetail.getName());
//                }
//                UgroupVoForDetail ugroupVoForDetail= ugroupClient.findById(vo.getUserGroupId());
//                vo.setUserGroupName(ugroupVoForDetail.getName());
//
//
//            });
//
//            return EnerbosMessage.createSuccessMsg(pageInfo, "查询成功", "");
//        } catch (Exception e) {
//            logger.error("-----findItems ------", e);
//            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
//                    .getStackTrace().toString());
//        }
//    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据ID删除数据", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/userGroupDomain/delete", method = RequestMethod.POST)
    public EnerbosMessage delete(@ApiParam(value = "需要删除的数据ID,支持批量.多个用逗号分隔", required = true) @RequestParam("ids") String[] ids) {
        try {
            boolean isdeleteok = userGroupDomainClient.delete(ids);
            return EnerbosMessage.createSuccessMsg(isdeleteok, "删除IP功", "");
        } catch (Exception e) {
            logger.error("-----deleteInventory ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 新建
     * @param vo 新建的实体
     * @return 返回添加的实体
     */
    @ApiOperation(value = "新建&更新", response = UserGroupDomainVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/userGroupDomain/save", method = RequestMethod.POST)
    public EnerbosMessage save(@ApiParam(value = "数据对象", required = true)  UserGroupDomainVo vo, Principal user) {
        try {
            String personid = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            Assert.isTrue(StringUtils.isNotEmpty(vo.getDomainValueId()), "域值ID不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(vo.getUserGroupId()), "用户组不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(vo.getSiteId()), "站点不能为空。");
            Assert.isTrue(StringUtils.isNotEmpty(vo.getOrgId()), "组织不能为空。");

            UgroupVoForDetail u= ugroupClient.findById(vo.getUserGroupId());
            Assert.notNull(u, "未知用户组！");
            if(!Common.USERGROUP_ASSOCIATION_TYPE_ALL.equals(vo.getSiteId())){
                SiteVoForDetail siteVo = siteClient.findById(vo.getSiteId());
                Assert.notNull(siteVo, "未知站点！");
                Assert.isTrue(siteVo.getOrgId().equals(vo.getOrgId()), "所属组织错误！");
            }
            if(!Common.USERGROUP_ASSOCIATION_TYPE_ALL.equals(vo.getOrgId())){
                OrgVoForDetail orgVoForDetail= orgClient.findById(vo.getOrgId());
                Assert.notNull(orgVoForDetail, "未知组织！");
            }
//            FieldDomainValueVo fieldDomainValueVo=fieldDomainClient.findDomainValueById(vo.getDomainValueId());
//            Assert.notNull(fieldDomainValueVo, "未知域值！");
//            vo.setDomainValue(fieldDomainValueVo.getValue());

            FieldDomainValueVo valueVo=fieldDomainClient.findDomainValueById(vo.getDomainValueId());

            if(valueVo==null){
                return EnerbosMessage.createErrorMsg("500", "域值数据不存在!!", "");
            }
            FieldDomainVo vo1  =fieldDomainClient.findDomainById(valueVo.getDomainId());
            if(vo1==null){
                return EnerbosMessage.createErrorMsg("500", "域数据不存在!!", "");
            }
            vo.setDomainNum(vo1.getDomainNum());
            if(StringUtils.isEmpty(vo.getId())){
                if(vo.getStatus()==null){
                    vo.setStatus(true);
                }
                vo.setCreatetime(new Date());
                vo.setCreator(personid);
                return EnerbosMessage.createSuccessMsg(userGroupDomainClient.save(vo), "新建成功", "");
            }else{
                UserGroupDomainVo old= userGroupDomainClient.findDetail(vo.getId());
                if(old==null){
                    return EnerbosMessage.createErrorMsg("500", "id不存在!!", "");
                }
                vo.setSiteId(old.getSiteId());
                vo.setOrgId(old.getOrgId());
                vo.setCreatetime(old.getCreatetime());
                vo.setCreator(old.getCreator());
                return EnerbosMessage.createSuccessMsg(userGroupDomainClient.save(vo), "更新成功", "");
            }
        } catch (Exception e) {
            logger.error("-----saveIP ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }
    /**
     * 查询用户组关联域详细信息
     * @param id
     * @return
     */
    @ApiOperation(value = "根据详细信息", response = UserGroupDomainVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/userGroupDomain/findDetail", method = RequestMethod.GET)
    public EnerbosMessage findDetail(@ApiParam(value = "数据Id", required = true)@RequestParam("id") String id) {
        try {

            UserGroupDomainVo vo=userGroupDomainClient.findDetail(id);
            if(vo==null){
                return EnerbosMessage.createSuccessMsg(null, "根据Id查询成功", "");
            }
            if(Common.USERGROUP_ASSOCIATION_TYPE_ALL.equals(vo.getSiteId())){
                vo.setSiteName(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            }else{
                SiteVoForDetail detail= siteClient.findById(vo.getSiteId());
                vo.setSiteName(detail.getName());
            }
            if(Common.USERGROUP_ASSOCIATION_TYPE_ALL.equals(vo.getOrgId())){
                vo.setOrgName(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            }else{
                OrgVoForDetail detail=  orgClient.findById(vo.getOrgId());
                vo.setOrgName(detail.getName());
            }
            UgroupVoForDetail u=ugroupClient.findById(vo.getUserGroupId());
            vo.setUserGroupName(u.getName());
            //域值详细先不写
            return EnerbosMessage.createSuccessMsg(vo, "根据Id查询成功", "");
        } catch (Exception e) {
            logger.error("-------findIPDetail--------",e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 根据域值value，域num查询该域值value对应的用户组
     * @param domainValue 域值
     * @param domainNum 域
     * @param orgId 组织
     * @param siteId 站点
     * @return
     */
    @ApiOperation(value = "根据域值value或者域查询用户组ID", response = UserGroupDomainVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/userGroupDomain/findUserGroupDomainByDomainValueAndDomainNum", method = RequestMethod.GET)
    public EnerbosMessage findUserGroupDomainByDomainValue(
            @ApiParam(value = "域值Value", required = true)@RequestParam("domainValue") String domainValue,
            @ApiParam(value = "域num", required = false)@RequestParam("domainNum") String domainNum,
            @ApiParam(value = "组织id", required = true)@RequestParam("orgId") String orgId,
            @ApiParam(value = "站点Id", required = true)@RequestParam("siteId") String siteId,
            @ApiParam(value = "关联类型", required = true)@RequestParam("associationType") String associationType
    ) {
        try {

            List domainId=new ArrayList();
//            if(StringUtils.isNotEmpty(domainNum)){
//                List<FieldDomainValueVo> vos= fieldDomainClient.findDomainValueByDomainNum(domainNum);
//                vos.stream().forEach(
//                        vos1->{
//                            domainId.add(vos1.getId());
//                        }
//                );
//            }
            UserGroupDomainVo vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(domainValue,domainNum,orgId,siteId,associationType);

           if(vo!=null){
               if(Common.USERGROUP_ASSOCIATION_TYPE_ALL.equals(vo.getSiteId())){
                   vo.setSiteName(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
               }else{
                   SiteVoForDetail detail= siteClient.findById(vo.getSiteId());
                   vo.setSiteName(detail.getName());
               }
               if(Common.USERGROUP_ASSOCIATION_TYPE_ALL.equals(vo.getOrgId())){
                   vo.setOrgName(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
               }else{
                   OrgVoForDetail detail=  orgClient.findById(vo.getOrgId());
                   vo.setOrgName(detail.getName());
               }
               UgroupVoForDetail u=ugroupClient.findById(vo.getUserGroupId());
               vo.setUserGroupName(u.getName());

           }
            logger.info("result：vo {}", vo);
            return EnerbosMessage.createSuccessMsg(vo, "根据域查询人员完成", "");
        } catch (Exception e) {
            logger.error("-------findDetail--------",e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }



    /**
     *根据域值value或者域查询该域num对应的用户组所属的人员
     * @param domainValue 域值
     * @param domainNum 域
     * @param orgId 组织
     * @param siteId 站点
     * @return
     */
    @ApiOperation(value = "查询关联数据", response = PersonAndUserVoForDetail.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/userGroupDomain/findUserByDomainValueORDomainNum", method = RequestMethod.GET)
    public EnerbosMessage findUserByDomainValueORDomainNum(
            @ApiParam(value = "域值Value", required = true)@RequestParam("domainValue") String domainValue,
            @ApiParam(value = "域num", required = false)@RequestParam("domainNum") String domainNum,
            @ApiParam(value = "组织id", required = true)@RequestParam("orgId") String orgId,
            @ApiParam(value = "站点Id", required = true)@RequestParam("siteId") String siteId,
            @ApiParam(value = "关联类型", required = true)@RequestParam("associationType") String associationType
    ) {
        try {

//            List domainId=new ArrayList();
//            if(StringUtils.isNotEmpty(domainNum)){
//                List<FieldDomainValueVo> vos= fieldDomainClient.findDomainValueByDomainNum(domainNum);
//                vos.stream().forEach(
//                        vos1->{
//                            domainId.add(vos1.getId());
//                        }
//                );
//            }
            UserGroupDomainVo vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(domainValue,domainNum,orgId,siteId,associationType);
            if(vo==null){
                return EnerbosMessage.createSuccessMsg("", "查询人员失败", "域人员组关联查询结果为null");
            }
            UgroupVoForDetail voForDetail=  ugroupClient.findById(vo.getUserGroupId());
            if(voForDetail==null){
                return EnerbosMessage.createSuccessMsg("", "查询人员失败", "用户组信息查询失败");
            }
            List<PersonAndUserVoForDetail> detailList=new ArrayList<>();
            voForDetail.getUsers().stream().forEach(
                    u->{
                     //   PersonAndUserVoForDetail  person=new PersonAndUserVoForDetail();
                        PersonAndUserVoForDetail person= personAndUserClient.findByPersonId(u.getPersonId());
                        if(person!=null){//清空无关数据
                            person.setOrgs(null);
                            person.setSites(null);
                            person.setProducts(null);
                            person.setLevels(null);
                            person.setRoles(null);
                            detailList.add(person);
                        }

                    }
            );
            logger.info("result：detailList {}", detailList);
            return EnerbosMessage.createSuccessMsg(detailList, "查询人员成功", "");
        } catch (Exception e) {
            logger.error("-------findUserByDomainValueORDomainNum--------",e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }


    /**
     *根据域值value，域num查询该域值value对应的用户组
     * @param domainValue 域值
     * @param domainNum 域
     * @param orgId 组织
     * @param siteId 站点
     * @return
     */
    public UserGroupDomainVo findUserGroupDomainByDomainValues(String domainValue, String domainNum,String orgId,String siteId,String associationType) {
        UserGroupDomainVo vo=new UserGroupDomainVo();
        try {
//            List domainId=new ArrayList();
//            if(StringUtils.isNotEmpty(domainNum)){
//                List<FieldDomainValueVo> vos= fieldDomainClient.findDomainValueByDomainNum(domainNum);
//                vos.stream().forEach(
//                        vos1->{
//                            domainId.add(vos1.getId());
//                        }
//                );
//            }
         vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum( domainValue,  domainNum, orgId, siteId,associationType);
           if(vo!=null){
               if(Common.USERGROUP_ASSOCIATION_TYPE_ALL.equals(vo.getSiteId())){
                   vo.setSiteName(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
               }else{
                   SiteVoForDetail detail= siteClient.findById(vo.getSiteId());
                   vo.setSiteName(detail.getName());
               }
               if(Common.USERGROUP_ASSOCIATION_TYPE_ALL.equals(vo.getOrgId())){
                   vo.setOrgName(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
               }else{
                   OrgVoForDetail detail=  orgClient.findById(vo.getOrgId());
                   vo.setOrgName(detail.getName());
               }
               UgroupVoForDetail u=ugroupClient.findById(vo.getUserGroupId());
               if(u!=null){
                   vo.setUserGroupName(u.getName());
               }

           }
            logger.info("result：vo {}", vo);
            return vo;
        } catch (Exception e) {
            logger.error("-------findUserGroupDomainByDomainValues--------",e);

        }
        return  vo;
    }

    /**
     *根据域值value或者域查询该域num对应的用户组所属的人员
     * @param domainValue 域值
     * @param domainNum 域
     * @param orgId 组织
     * @param siteId 站点
     * @return
     */
    public  List<PersonAndUserVoForDetail> findUserByDomainValueORDomainNums(
           String domainValue, String domainNum, String orgId,String siteId,String associationType
    ) {
        List<PersonAndUserVoForDetail> detailList=new ArrayList<>();
        try {

//            List domainId=new ArrayList();
//            if(StringUtils.isNotEmpty(domainNum)){
//                List<FieldDomainValueVo> vos= fieldDomainClient.findDomainValueByDomainNum(domainNum);
//                vos.stream().forEach(
//                        vos1->{
//                            domainId.add(vos1.getId());
//                        }
//                );
//            }
            UserGroupDomainVo vo=userGroupDomainClient.findUserGroupDomainByDomainValueAndDomainNum(domainValue,  domainNum, orgId, siteId, associationType);
            UgroupVoForDetail voForDetail=  ugroupClient.findById(vo.getUserGroupId());
            voForDetail.getUsers().stream().forEach(
                    u->{
                        //   PersonAndUserVoForDetail  person=new PersonAndUserVoForDetail();
                        PersonAndUserVoForDetail person= personAndUserClient.findByPersonId(u.getPersonId());
                        if(person!=null){//清空无关数据
                            person.setOrgs(null);
                            person.setSites(null);
                            person.setProducts(null);
                            person.setLevels(null);
                            person.setRoles(null);
                            detailList.add(person);
                        }

                    }
            );
            logger.info("result：detailList {}", detailList);
            return detailList ;
        } catch (Exception e) {
            logger.error("-------findUserByDomainValueORDomainNums--------",e);
        }
        return detailList ;
    }
}
