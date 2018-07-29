package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.ClassificationClient;
import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.constants.MixType;
import com.enerbos.cloud.ams.vo.asset.AssetVoForSave;
import com.enerbos.cloud.ams.vo.classification.ClassificationVoForDetail;
import com.enerbos.cloud.ams.vo.classification.ClassificationVoForSave;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.ams.vo.location.LocationVoForSave;
import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.eam.client.CodeGeneratorClient;
import com.enerbos.cloud.eam.client.UserGroupDomainClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.vo.CodeGeneratorVo;
import com.enerbos.cloud.eam.vo.UserGroupDomainVo;
import com.enerbos.cloud.uas.client.*;
import com.enerbos.cloud.uas.constants.UASConstants;
import com.enerbos.cloud.uas.vo.org.OrgVoForDetail;
import com.enerbos.cloud.uas.vo.org.OrgVoForSave;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForInvite;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForSave;
import com.enerbos.cloud.uas.vo.resource.ResourceVo;
import com.enerbos.cloud.uas.vo.role.RoleVoForDetail;
import com.enerbos.cloud.uas.vo.role.RoleVoForSave;
import com.enerbos.cloud.uas.vo.role.RoleVoForUpdateRes;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForList;
import com.enerbos.cloud.uas.vo.site.SiteVoForSave;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForDetail;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForSave;
import com.enerbos.cloud.uas.vo.ugroup.UgroupVoForUpdateUsers;
import com.enerbos.cloud.wfs.client.ProcessModelClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author mopeng
 * @version 1.0
 * @date 2017年9月23日
 */

@RestController
@Api(description = "初始化总部和项目(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class InitOrgAndSiteController {

    @Value("${orgAdminRoleResCodeList}")
    private String orgAdminRoleResCodeList;

    @Value("${orgManagerRoleResCodeList}")
    private String orgManagerRoleResCodeList;

    @Value("${siteAdminRoleResCodeList}")
    private String siteAdminRoleResCodeList;

    @Value("${eamTrialRoleResCodeList}")
    private String eamTrialRoleResCodeList;

    @Value("${processList}")
    private String processList;

    @Value("${codeGeneratorList}")
    private String codeGeneratorList;

    @Autowired
    public OrgClient orgClient;

    @Autowired
    public SiteClient siteClient;

    @Autowired
    public PersonAndUserClient personAndUserClient;

    @Autowired
    public RoleClient roleClient;

    @Autowired
    public ResourceClient resourceClient;

    @Autowired
    public ProcessModelClient processModelClient;

    @Autowired
    private MessageClient messageClient;

    @Autowired
    public UgroupClient ugroupClient;

    @Autowired
    public ClassificationClient classificationClient;

    @Autowired
    private FieldDomainClient fieldDomainClient;

    @Autowired
    private CodeGeneratorClient codeGeneratorClient;

    @Autowired
    private UserGroupDomainClient userGroupDomainClient;

    @Autowired
    private AssetClient assetClient;

    @Autowired
    private LocationClient locationClient;


    private static final Logger logger = LoggerFactory.getLogger(InitOrgAndSiteController.class);


    /**
     * 初始化组织(手动创建组织,站点,人员后使用)
     *
     * @param orgId
     * @param orgAdmin
     * @return
     */
    @ApiOperation(value = "初始化组织", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/initOrg", method = RequestMethod.POST)
    public EnerbosMessage initOrg(
            @ApiParam(value = "组织ID", required = true) @RequestParam(value = "orgId", required = true) String orgId,
            @ApiParam(value = "组织管理员用户名", required = true) @RequestParam(value = "orgAdmin", required = true) String orgAdmin) {
        try {

            OrgVoForDetail org = orgClient.findById(orgId);
            if (org == null) {
                throw new EnerbosException("", "组织不存在!");
            }


            PersonAndUserVoForDetail orgAdmin_User = personAndUserClient.findByLoginName(orgAdmin);
            if (orgAdmin_User == null) {
                throw new EnerbosException("", "组织管理员不存在!");
            }


            //创建组织管理员角色
            RoleVoForSave orgAdminRole = new RoleVoForSave();
            orgAdminRole.setName(org.getCode() + "-总部管理员");
            orgAdminRole.setOrgId(orgId);
            orgAdminRole.setProductId(UASConstants.PRODUCT_ADC_ID);
            orgAdminRole.setType(UASConstants.ROLE_TYPE_ORGADMINROLE);
            orgAdminRole.setCreateUser(UASConstants.ADMIN_IN_SET);
            RoleVoForDetail orgAdminRoleForDetail = roleClient.add(orgAdminRole);

            //授予对应资源
            RoleVoForUpdateRes orgAdminForUpdateRes = new RoleVoForUpdateRes();
            orgAdminForUpdateRes.setRoleId(orgAdminRoleForDetail.getId());
            String[] orgAdminRes = orgAdminRoleResCodeList.split(",");
            List<ResourceVo> resourceVoList = resourceClient.findByCodes(orgAdminRes);
            String[] orgAdminResIds = new String[resourceVoList.size()];
            for (int i = 0; i < resourceVoList.size(); i++) {
                orgAdminResIds[i] = resourceVoList.get(i).getId();
            }
            orgAdminForUpdateRes.setResourceIds(orgAdminResIds);
            orgAdminForUpdateRes.setCreateUser(UASConstants.ADMIN_IN_SET);
            roleClient.updateResources(orgAdminForUpdateRes);


            //创建组织管理层角色
            RoleVoForSave orgManagerRole = new RoleVoForSave();
            orgManagerRole.setName(org.getCode() + "-总部管理层");
            orgManagerRole.setOrgId(orgId);
            orgManagerRole.setProductId(UASConstants.PRODUCT_EAM_ID);
            orgManagerRole.setType(UASConstants.ROLE_TYPE_ORGCUSTOMROLE);
            orgManagerRole.setCreateUser(UASConstants.ADMIN_IN_SET);
            RoleVoForDetail orgManagerRoleForDetail = roleClient.add(orgManagerRole);

            //授予对应资源
            RoleVoForUpdateRes orgManagerForUpdateRes = new RoleVoForUpdateRes();
            orgManagerForUpdateRes.setRoleId(orgManagerRoleForDetail.getId());
            String[] orgManagerRes = orgManagerRoleResCodeList.split(",");
            List<ResourceVo> orgManagerResourceVoList = resourceClient.findByCodes(orgManagerRes);
            String[] orgManagerResIds = new String[orgManagerResourceVoList.size()];
            for (int i = 0; i < orgManagerResourceVoList.size(); i++) {
                orgManagerResIds[i] = orgManagerResourceVoList.get(i).getId();
            }
            orgManagerForUpdateRes.setResourceIds(orgManagerResIds);
            orgManagerForUpdateRes.setCreateUser(UASConstants.ADMIN_IN_SET);
            roleClient.updateResources(orgManagerForUpdateRes);

            //查询组织下的站点
            List<SiteVoForList> siteVoForList = siteClient.findByOrgId(orgId);
            List<String> siteIdList = new ArrayList<>();
            for (SiteVoForList tmpSite : siteVoForList) {
                siteIdList.add(tmpSite.getId());
            }

            //给新建用户设置组织,组织管理员角色
            PersonAndUserVoForInvite voForInvite = new PersonAndUserVoForInvite();
            voForInvite.setPersonIds(new String[]{orgAdmin_User.getPersonId()});
            voForInvite.setOrgIds(new String[]{orgId});
            voForInvite.setSiteIds(siteIdList.toArray(new String[0]));
            voForInvite.setProductIds(new String[]{UASConstants.PRODUCT_ADC_ID, UASConstants.PRODUCT_EAM_ID});
            voForInvite.setRoleIds(new String[]{orgAdminRoleForDetail.getId(), orgManagerRoleForDetail.getId()});
            voForInvite.setCreateUser(UASConstants.ADMIN_IN_SET);
            personAndUserClient.addOrgsAndSites(voForInvite);

            return EnerbosMessage.createSuccessMsg("", "组织初始化成功", "");

        } catch (Exception e) {
            logger.error("-----InitOrg ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 初始化站点(手动创建组织,站点,人员后使用)
     *
     * @param siteId
     * @param siteAdmin
     * @return
     */
    @ApiOperation(value = "初始化站点", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/initSite", method = RequestMethod.POST)
    public EnerbosMessage initSite(
            @ApiParam(value = "站点ID", required = true) @RequestParam(value = "siteId", required = true) String siteId,
            @ApiParam(value = "站点管理员用户名", required = true) @RequestParam(value = "siteAdmin", required = true) String siteAdmin) {
        try {

            SiteVoForDetail site = siteClient.findById(siteId);
            if (site == null) {
                throw new EnerbosException("", "站点不存在!");
            }

            PersonAndUserVoForDetail siteAdmin_User = personAndUserClient.findByLoginName(siteAdmin);
            if (siteAdmin_User == null) {
                throw new EnerbosException("", "站点管理员不存在!");
            }
            String userId = siteAdmin_User.getUserId();
            //创建站点管理员角色
            RoleVoForSave siteAdminRole = new RoleVoForSave();
            siteAdminRole.setName(site.getCode() + "-项目管理员");
            siteAdminRole.setOrgId(site.getOrgId());
            siteAdminRole.setSiteId(siteId);
            siteAdminRole.setProductId(UASConstants.PRODUCT_ADC_ID);
            siteAdminRole.setType(UASConstants.ROLE_TYPE_SITEADMINROLE);
            siteAdminRole.setCreateUser(UASConstants.ADMIN_IN_SET);
            RoleVoForDetail siteAdminRoleForDetail = roleClient.add(siteAdminRole);

            //授予对应资源
            RoleVoForUpdateRes siteAdminForUpdateRes = new RoleVoForUpdateRes();
            siteAdminForUpdateRes.setRoleId(siteAdminRoleForDetail.getId());
            String[] siteAdminRes = siteAdminRoleResCodeList.split(",");
            List<ResourceVo> siteAdminResVoList = resourceClient.findByCodes(siteAdminRes);
            String[] siteAdminResIds = new String[siteAdminResVoList.size()];
            for (int i = 0; i < siteAdminResVoList.size(); i++) {
                siteAdminResIds[i] = siteAdminResVoList.get(i).getId();
            }
            siteAdminForUpdateRes.setResourceIds(siteAdminResIds);
            siteAdminForUpdateRes.setCreateUser(UASConstants.ADMIN_IN_SET);
            roleClient.updateResources(siteAdminForUpdateRes);

            //创建EAM试用角色
            RoleVoForSave eamTrialRole = new RoleVoForSave();
            eamTrialRole.setName(site.getCode() +"-EAM试用角色");
            eamTrialRole.setOrgId(site.getOrgId());
            eamTrialRole.setSiteId(siteId);
            eamTrialRole.setProductId(UASConstants.PRODUCT_EAM_ID);
            eamTrialRole.setType(UASConstants.ROLE_TYPE_SITECUSTOMROLE);
            eamTrialRole.setCreateUser(UASConstants.ADMIN_IN_SET);
            RoleVoForDetail eamTrialRoleForDetail = roleClient.add(eamTrialRole);

            //授予对应资源
            RoleVoForUpdateRes eamTriaForUpdateRes = new RoleVoForUpdateRes();
            eamTriaForUpdateRes.setRoleId(eamTrialRoleForDetail.getId());
            String[] eamTriaRoleRes = eamTrialRoleResCodeList.split(",");
            List<ResourceVo> eamTriaResVoList = resourceClient.findByCodes(eamTriaRoleRes);
            String[] eamTriaResIds = new String[eamTriaResVoList.size()];
            for (int i = 0; i < eamTriaResVoList.size(); i++) {
                eamTriaResIds[i] = eamTriaResVoList.get(i).getId();
            }
            eamTriaForUpdateRes.setResourceIds(eamTriaResIds);
            eamTriaForUpdateRes.setCreateUser(UASConstants.ADMIN_IN_SET);
            roleClient.updateResources(eamTriaForUpdateRes);

            //给新建用户设置站点,站点管理员角色,EAM试用角色
            PersonAndUserVoForInvite voForInvite = new PersonAndUserVoForInvite();
            voForInvite.setPersonIds(new String[]{siteAdmin_User.getPersonId()});
            voForInvite.setOrgIds(new String[]{site.getOrgId()});
            voForInvite.setSiteIds(new String[]{siteId});
            voForInvite.setProductIds(new String[]{UASConstants.PRODUCT_ADC_ID, UASConstants.PRODUCT_EAM_ID});
            voForInvite.setRoleIds(new String[]{siteAdminRoleForDetail.getId(), eamTrialRoleForDetail.getId()});
            voForInvite.setCreateUser(UASConstants.ADMIN_IN_SET);
            personAndUserClient.addOrgsAndSites(voForInvite);


            //初始化流程
            String[] processArray = processList.split(",");
            for (String process : processArray) {
                processModelClient.deployProcessByBpmnXml("processes/" + process, site.getOrgId(), siteId, site.getCode());
            }

            //初始化用户组
            initUgroup(site.getOrgId(), siteId, userId, siteAdmin, site.getCode());

            //初始化编码规则
            initCodeGenerator(site.getOrgId(),siteId);

            return EnerbosMessage.createSuccessMsg("", "初始化站点成功", "");

        } catch (Exception e) {
            logger.error("-----initSite ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 初始化站点编码规则
     *
     * @param siteId
     */
    private void initCodeGenerator(String orgId,String siteId) {
        String[] codeGeneratorArray = codeGeneratorList.split("@End@");
        for (String codeStr : codeGeneratorArray) {
            logger.debug("codeStr:" + codeStr);
            String[] codeArray = codeStr.split(",");
            CodeGeneratorVo codeGeneratorVo = new CodeGeneratorVo();
            codeGeneratorVo.setSiteId(siteId);
            codeGeneratorVo.setCurrentCode("0");
            codeGeneratorVo.setDelimiter(StringUtils.isNotBlank(codeArray[0]) ? codeArray[0] : "");
            codeGeneratorVo.setImplClass(StringUtils.isNotBlank(codeArray[1]) ? codeArray[1] : "");
            codeGeneratorVo.setModelKey(StringUtils.isNotBlank(codeArray[2]) ? codeArray[2] : "");
            codeGeneratorVo.setPrifix(StringUtils.isNotBlank(codeArray[3]) ? codeArray[3] : "");
            codeGeneratorVo.setVersion(0);
            codeGeneratorVo.setOrgId(orgId);
            codeGeneratorClient.add(codeGeneratorVo);
        }
    }


    /**
     * 简单注册新项目
     *
     * @param orgName  组织名称
     * @param siteName 站点名称
     * @param name     姓名
     * @param mobile   手机号
     * @return
     */
    @ApiOperation(value = "简单注册新项目", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/simpleRegister", method = RequestMethod.POST)
    public EnerbosMessage simpleRegister(
            @ApiParam(value = "组织名称", required = true) @RequestParam(value = "orgName", required = true) String orgName,
            @ApiParam(value = "站点名称", required = true) @RequestParam(value = "siteName", required = true) String siteName,
            @ApiParam(value = "姓名", required = true) @RequestParam(value = "name", required = true) String name,
            @ApiParam(value = "手机号", required = true) @RequestParam(value = "mobile", required = true) String mobile,
            @ApiParam(value = "验证码", required = true) @RequestParam(value = "code", required = true) String code) {

        logger.debug("-----simpleRegister Info------ orgName {}, siteName {}, name {}, mobiel {}, code {}", orgName, siteName, name, mobile, code);

        try {

            if(StringUtils.isBlank(orgName)){
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "公司名称不能为空!");
            }
            if(StringUtils.isBlank(siteName)){
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "项目名称不能为空!");
            }
            if(StringUtils.isBlank(name)){
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "姓名不能为空!");
            }
            if(StringUtils.isBlank(mobile)){
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "手机号不能为空!");
            }
            if(StringUtils.isBlank(code)){
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "验证码不能为空!");
            }

            boolean exitsMobile = personAndUserClient.checkUserExistsByMobile(mobile);
            if (exitsMobile) {
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "该手机号已经注册!");
            }

            boolean isMatch;
            try {
                isMatch = messageClient.checkMobileCode(mobile, code, UASConstants.MSG_TYPE_REGISTER);
            } catch (Exception e) {
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "验证码不正确!", e.getCause());
            }
            if (!isMatch) {
                throw new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "验证码不正确!");
            }

            //创建组织
            OrgVoForSave orgVoForSave = new OrgVoForSave();
            orgVoForSave.setName(orgName);
            String orgCode = "" ;
            do {
                orgCode = "SO" + RandomStringUtils.randomNumeric(6);
                Boolean orgCodeCheck = orgClient.checkOrgExistsByCode(orgCode);
                if( !orgCodeCheck){
                    break;
                }
            } while (1==1) ;
            orgVoForSave.setCode(orgCode);
            orgVoForSave.setCreateUser(UASConstants.ADMIN_IN_SET);
            orgVoForSave.setProductIds(new String[]{UASConstants.PRODUCT_ADC_ID, UASConstants.PRODUCT_EAM_ID});
            OrgVoForDetail orgVoForDetail = orgClient.add(orgVoForSave);
            String orgId = orgVoForDetail.getId();
            //创建站点
            SiteVoForSave siteVoForSave = new SiteVoForSave();
            siteVoForSave.setName(siteName);
            String siteCode = "" ;
            do {
                siteCode = "SS" + RandomStringUtils.randomNumeric(6);
                Boolean siteCodeCheck = siteClient.checkSiteExistsByCode(siteCode);
                if( !siteCodeCheck){
                    break;
                }
            } while (1==1) ;
            siteVoForSave.setCode(siteCode);
            siteVoForSave.setCreateUser(UASConstants.ADMIN_IN_SET);
            siteVoForSave.setProductIds(new String[]{UASConstants.PRODUCT_ADC_ID, UASConstants.PRODUCT_EAM_ID});
            siteVoForSave.setOrgId(orgVoForDetail.getId());
            SiteVoForDetail siteVoForDetail = siteClient.add(siteVoForSave);
            String siteId = siteVoForDetail.getId();

            //创建用户和人员
            PersonAndUserVoForSave personAndUserVoForSave = new PersonAndUserVoForSave();
            personAndUserVoForSave.setGender("male");
            personAndUserVoForSave.setLoginName(mobile);
            personAndUserVoForSave.setMobile(mobile);
            personAndUserVoForSave.setName(name);
            personAndUserVoForSave.setCreateUser(UASConstants.ADMIN_IN_SET);
            personAndUserVoForSave.setUserType(UASConstants.USER_TYPE_CUSTOMER);
            PersonAndUserVoForDetail personAndUserVoForDetail = personAndUserClient.add(personAndUserVoForSave);
            String personId = personAndUserVoForDetail.getPersonId();
            String userId = personAndUserVoForDetail.getUserId();
            //创建角色和其他功能需要的初始化信息
            initRoleAndFunction(orgId, orgCode, siteId, siteCode, personId, userId, mobile);
            messageClient.sendRegisterFinishMessage(mobile);
            return EnerbosMessage.createSuccessMsg("", "初始化成功", "");
        } catch (Exception e) {
            logger.error("-----simpleRegister Exception ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }

    }


    public boolean initRoleAndFunction(String orgId, String orgCode, String siteId,String siteCode, String personId, String userId,  String loginName) {

        //初始化 角色资源
        initRoleResource(personId, orgId, orgCode, siteId, siteCode);

        //初始化流程
        try {
            String[] processArray = processList.split(",");
            for (String process : processArray) {
                processModelClient.deployProcessByBpmnXml("processes/" + process, orgId, siteId, siteCode);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        //初始化 分类
        initClassification(orgId, siteId, loginName);

        //初始化用户组
        initUgroup(orgId, siteId, userId, loginName, siteCode);

        //初始化 设备相关 ： 包括 分类、位置、设备
        //initAsset(orgId, siteId ,loginName,siteCode) ;

        //初始化编码规则
        initCodeGenerator(orgId,siteId);

        return true;
    }

    private void initAsset(String orgId, String siteId, String loginName, String siteCode) {
        //创建一个一级位置
        LocationVoForSave locationVoForSave = new LocationVoForSave();
        locationVoForSave.setOrgId(orgId);
        locationVoForSave.setSiteId(siteId);
        locationVoForSave.setName("翼虎能源大厦");
        locationVoForSave.setDescription("翼虎能源大厦");
        locationVoForSave.setCode("YHNY-" + siteCode);
        locationVoForSave.setProducts(Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        locationVoForSave.setHasChildren(true);
        locationVoForSave = locationClient.save(loginName, locationVoForSave);
        //创建二级位置
        LocationVoForSave locationChildOne = new LocationVoForSave();
        locationChildOne.setOrgId(orgId);
        locationChildOne.setSiteId(siteId);
        locationChildOne.setName("翼虎A区");
        locationChildOne.setDescription("翼虎A区");
        locationChildOne.setCode("YHNY-A-" + siteCode);
        locationChildOne.setProducts(Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        locationChildOne.setParentId(locationVoForSave.getId());
        locationChildOne.setHasChildren(true);
        locationChildOne = locationClient.save(loginName, locationChildOne);
        //创建三级位置
        LocationVoForSave locationChildOneChild = new LocationVoForSave();
        locationChildOneChild.setOrgId(orgId);
        locationChildOneChild.setSiteId(siteId);
        locationChildOneChild.setName("A区25层");
        locationChildOneChild.setDescription("A区25层");
        locationChildOneChild.setCode("YHNY-A-25-" + siteCode);
        locationChildOneChild.setProducts(Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        locationChildOneChild.setParentId(locationChildOne.getId());
        locationChildOneChild.setHasChildren(true);
        locationChildOneChild = locationClient.save(loginName, locationChildOneChild);


        //创建二级位置
        LocationVoForSave locationChildTwo = new LocationVoForSave();
        locationChildTwo.setOrgId(orgId);
        locationChildTwo.setSiteId(siteId);
        locationChildTwo.setName("翼虎B区");
        locationChildTwo.setDescription("翼虎B区");
        locationChildTwo.setCode("YHNY-B-" + siteCode);
        locationChildTwo.setProducts(Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        locationChildTwo.setParentId(locationVoForSave.getId());
        locationChildTwo.setHasChildren(true);
        locationChildTwo = locationClient.save(loginName, locationChildTwo);
        //创建二级位置
        LocationVoForSave locationChildThree = new LocationVoForSave();
        locationChildThree.setOrgId(orgId);
        locationChildThree.setSiteId(siteId);
        locationChildThree.setName("翼虎C区");
        locationChildThree.setDescription("翼虎C区");
        locationChildThree.setCode("YHNY-C-" + siteCode);
        locationChildThree.setProducts(Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        locationChildThree.setParentId(locationVoForSave.getId());
        locationChildThree.setHasChildren(true);
        locationChildThree = locationClient.save(loginName, locationChildThree);
        //创建三级位置
        LocationVoForSave locationChildTwoChild = new LocationVoForSave();
        locationChildTwoChild.setOrgId(orgId);
        locationChildTwoChild.setSiteId(siteId);
        locationChildTwoChild.setName("B区25层");
        locationChildTwoChild.setDescription("B区25层");
        locationChildTwoChild.setCode("YHNY-B-25-" + siteCode);
        locationChildTwoChild.setProducts(Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        locationChildTwoChild.setParentId(locationChildTwo.getId());
        locationChildTwoChild.setHasChildren(true);
        locationChildOneChild = locationClient.save(loginName, locationChildTwoChild);


        //初始化 分类 初始两级

        ClassificationVoForDetail classificationVoForDetail = classificationClient.findByCode(MixType.CLASSICIATION_SBSS, null, null);
        //初始
        ClassificationVoForSave classificationVoForSave = new ClassificationVoForSave();
        classificationVoForSave.setName("空调机组");
        classificationVoForSave.setCode("KTJZ-" + siteCode);
        classificationVoForSave.setOrgId(orgId);
        classificationVoForSave.setParentId(classificationVoForDetail.getId());
        classificationVoForSave.setProducts(java.util.Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        classificationVoForSave = classificationClient.save(loginName, classificationVoForSave);

        ClassificationVoForSave classificationVoForSave1 = new ClassificationVoForSave();
        classificationVoForSave1.setName("暖通空调");
        classificationVoForSave1.setCode("NTKT-" + siteCode);
        classificationVoForSave1.setOrgId(orgId);
        classificationVoForSave1.setParentId(classificationVoForDetail.getId());
        classificationVoForSave1.setProducts(java.util.Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        classificationVoForSave1 = classificationClient.save(loginName, classificationVoForSave1);
        AssetVoForSave assetVoForSave = new AssetVoForSave();
        assetVoForSave.setOrgId(orgId);
        assetVoForSave.setSiteId(siteId);
        assetVoForSave.setCode("AS-" + siteCode);
        assetVoForSave.setName("风机盘管");
        assetVoForSave.setDepartment("风机盘管");
        assetVoForSave.setClassifications(Arrays.asList(classificationVoForSave.getId()));
        assetVoForSave.setLocationId(locationChildOneChild.getId());
        assetVoForSave.setProducts(Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        assetClient.save(loginName, assetVoForSave);

        AssetVoForSave assetVoForSave1 = new AssetVoForSave();
        assetVoForSave1.setOrgId(orgId);
        assetVoForSave1.setSiteId(siteId);
        assetVoForSave1.setCode("AD-" + siteCode);
        assetVoForSave1.setName("空调机");
        assetVoForSave1.setDepartment("空调机");
        assetVoForSave1.setClassifications(Arrays.asList(classificationVoForSave1.getId()));
        assetVoForSave1.setLocationId(locationChildOneChild.getId());
        assetVoForSave1.setProducts(Arrays.asList(UASConstants.PRODUCT_EAM_ID));
        assetClient.save(loginName, assetVoForSave1);


    }


    boolean initRoleResource(String personId, String orgId, String orgCode, String siteId, String siteCode) {
        //创建组织管理员角色
        RoleVoForSave orgAdminRole = new RoleVoForSave();
        orgAdminRole.setName(orgCode+"-总部管理员");
        orgAdminRole.setOrgId(orgId);
        orgAdminRole.setProductId(UASConstants.PRODUCT_ADC_ID);
        orgAdminRole.setType(UASConstants.ROLE_TYPE_ORGADMINROLE);
        orgAdminRole.setCreateUser(UASConstants.ADMIN_IN_SET);
        RoleVoForDetail orgAdminRoleForDetail = roleClient.add(orgAdminRole);

        String roleId = orgAdminRoleForDetail.getId();
        //授予对应资源
        RoleVoForUpdateRes orgAdminForUpdateRes = new RoleVoForUpdateRes();
        orgAdminForUpdateRes.setRoleId(orgAdminRoleForDetail.getId());
        String[] orgAdminRes = orgAdminRoleResCodeList.split(",");
        List<ResourceVo> resourceVoList = resourceClient.findByCodes(orgAdminRes);
        String[] orgAdminResIds = new String[resourceVoList.size()];
        for (int i = 0; i < resourceVoList.size(); i++) {
            orgAdminResIds[i] = resourceVoList.get(i).getId();
        }
        orgAdminForUpdateRes.setResourceIds(orgAdminResIds);
        orgAdminForUpdateRes.setCreateUser(UASConstants.ADMIN_IN_SET);
        roleClient.updateResources(orgAdminForUpdateRes);

        //创建站点管理员角色
        RoleVoForSave siteAdminRole = new RoleVoForSave();
        siteAdminRole.setName(siteCode+"-项目管理员");
        siteAdminRole.setOrgId(orgId);
        siteAdminRole.setSiteId(siteId);
        siteAdminRole.setProductId(UASConstants.PRODUCT_ADC_ID);
        siteAdminRole.setType(UASConstants.ROLE_TYPE_SITEADMINROLE);
        siteAdminRole.setCreateUser(UASConstants.ADMIN_IN_SET);
        RoleVoForDetail siteAdminRoleForDetail = roleClient.add(siteAdminRole);

        //授予对应资源
        RoleVoForUpdateRes siteAdminForUpdateRes = new RoleVoForUpdateRes();
        siteAdminForUpdateRes.setRoleId(siteAdminRoleForDetail.getId());
        String[] siteAdminRes = siteAdminRoleResCodeList.split(",");
        List<ResourceVo> siteAdminResVoList = resourceClient.findByCodes(siteAdminRes);
        String[] siteAdminResIds = new String[siteAdminResVoList.size()];
        for (int i = 0; i < siteAdminResVoList.size(); i++) {
            siteAdminResIds[i] = siteAdminResVoList.get(i).getId();
        }
        siteAdminForUpdateRes.setResourceIds(siteAdminResIds);
        siteAdminForUpdateRes.setCreateUser(UASConstants.ADMIN_IN_SET);
        roleClient.updateResources(siteAdminForUpdateRes);

        //创建EAM试用角色
        RoleVoForSave eamTrialRole = new RoleVoForSave();
        eamTrialRole.setName(siteCode+"-EAM试用角色");
        eamTrialRole.setOrgId(orgId);
        eamTrialRole.setSiteId(siteId);
        eamTrialRole.setProductId(UASConstants.PRODUCT_EAM_ID);
        eamTrialRole.setType(UASConstants.ROLE_TYPE_SITECUSTOMROLE);
        eamTrialRole.setCreateUser(UASConstants.ADMIN_IN_SET);
        RoleVoForDetail eamTrialRoleForDetail = roleClient.add(eamTrialRole);

        //授予对应资源
        RoleVoForUpdateRes eamTriaForUpdateRes = new RoleVoForUpdateRes();
        eamTriaForUpdateRes.setRoleId(eamTrialRoleForDetail.getId());
        String[] eamTriaRoleRes = eamTrialRoleResCodeList.split(",");
        List<ResourceVo> eamTriaResVoList = resourceClient.findByCodes(eamTriaRoleRes);
        String[] eamTriaResIds = new String[eamTriaResVoList.size()];
        for (int i = 0; i < eamTriaResVoList.size(); i++) {
            eamTriaResIds[i] = eamTriaResVoList.get(i).getId();
        }
        eamTriaForUpdateRes.setResourceIds(eamTriaResIds);
        eamTriaForUpdateRes.setCreateUser(UASConstants.ADMIN_IN_SET);
        roleClient.updateResources(eamTriaForUpdateRes);

        //给新建用户设置组织,站点,组织管理员角色,站点管理员角色
        PersonAndUserVoForInvite voForInvite = new PersonAndUserVoForInvite();
        voForInvite.setPersonIds(new String[]{personId});
        voForInvite.setOrgIds(new String[]{orgId});
        voForInvite.setSiteIds(new String[]{siteId});
        voForInvite.setProductIds(new String[]{UASConstants.PRODUCT_ADC_ID, UASConstants.PRODUCT_EAM_ID});
        voForInvite.setRoleIds(new String[]{orgAdminRoleForDetail.getId(), siteAdminRoleForDetail.getId(), eamTrialRoleForDetail.getId()});
        voForInvite.setCreateUser(UASConstants.ADMIN_IN_SET);
        personAndUserClient.addOrgsAndSites(voForInvite);

        return true;
    }

    boolean initClassification(String orgId, String siteId, String loginName) {
        //初始化分类
        //环境监测 分类
//        String hjjcId = classificationClient.findByCode(MixType.CLASSICIATION_HJJC,null,null, UASConstants.PRODUCT_EAM_ID);;
//        if(StringUtils.isNotEmpty(hjjcId)){
//            //初始
//            ClassificationVoForSave classificationVoForSave = new ClassificationVoForSave() ;
//            classificationVoForSave.setName( MixType.CLASSICIATION_HJJC_CHILD);
//            classificationVoForSave.setCode(MixType.CLASSICIATION_HJJC_CHILD_CODE);
//            classificationVoForSave.setOrgId(orgId);
//            classificationVoForSave.setParentId(hjjcId);
//            classificationVoForSave.setProducts(java.util.Arrays.asList(UASConstants.PRODUCT_EAM_ID));
//            classificationClient.save(loginName,classificationVoForSave) ;
//        }
//        //仪表台账分类
//        String ybtzId = classificationClient.findByCode(MixType.CLASSICIATION_YBTZ, null,null, UASConstants.PRODUCT_EAM_ID);;
//        if(StringUtils.isNotEmpty(ybtzId)){
//            //初始 水表 、 电表 、燃气表
//            ClassificationVoForSave water = new ClassificationVoForSave() ;
//            water.setName( MixType.CLASSICIATION_YBTZ_WATER);
//            water.setCode(MixType.CLASSICIATION_YBTZ_WATER_CODE);
//            water.setOrgId(orgId);
//            water.setParentId(ybtzId);
//            water.setProductId(UASConstants.PRODUCT_EAM_ID);
//            classificationClient.save(loginName,water) ;
//
//            ClassificationVoForSave am = new ClassificationVoForSave() ;
//            am.setName( MixType.CLASSICIATION_YBTZ_AM);
//            am.setCode(MixType.CLASSICIATION_YBTZ_AM_CODE);
//            am.setOrgId(orgId);
//            am.setParentId(ybtzId);
//            am.setProducts(java.util.Arrays.asList(UASConstants.PRODUCT_EAM_ID));
//            classificationClient.save(loginName,am) ;
//
//            ClassificationVoForSave gas = new ClassificationVoForSave() ;
//            gas.setName( MixType.CLASSICIATION_YBTZ_GAS);
//            gas.setCode(MixType.CLASSICIATION_YBTZ_GAS_CODE);
//            gas.setOrgId(orgId);
//            gas.setParentId(ybtzId);
//            gas.setProducts(java.util.Arrays.asList(UASConstants.PRODUCT_EAM_ID));
//            classificationClient.save(loginName,gas) ;
//
//        }
        return true;
    }

    boolean initUgroup(String orgId, String siteId, String userId, String loginName, String siteCode) {
        //初始化用户组
        //维保工单 用户组初始化
        List<FieldDomainValueVo> woProjects = fieldDomainClient.findDomainValueByDomainNum(Common.FIELD_WO_PROJECT_TYPE);

        for (FieldDomainValueVo fieldDomainValueVo : woProjects
                ) {
            //创建用户组
            UgroupVoForSave ugroupVoForSave = new UgroupVoForSave();
            ugroupVoForSave.setOrgId(orgId);
            ugroupVoForSave.setSiteId(siteId);
            ugroupVoForSave.setCreateUser(loginName);
            ugroupVoForSave.setProductId(UASConstants.PRODUCT_EAM_ID);
            ugroupVoForSave.setCode(siteCode + Common.UGROUP_WO_PROJECTTYPE + "_" + fieldDomainValueVo.getValue());
            ugroupVoForSave.setName(Common.UGROUP_WO_PROJECTTYPE_NAME_PREFIX + fieldDomainValueVo.getDescription() + Common.UGROUP_NAME_SUFFIX_ALLOT);
            UgroupVoForDetail u = ugroupClient.add(ugroupVoForSave);

            //给用户组添加用户
            UgroupVoForUpdateUsers ugroupVoForUpdateUsers = new UgroupVoForUpdateUsers();
            ugroupVoForUpdateUsers.setCreateUser(loginName);
            ugroupVoForUpdateUsers.setUgroupId(u.getId());
            ugroupVoForUpdateUsers.setUserIds(userId.split(" "));
            ugroupClient.updateUsers(ugroupVoForUpdateUsers);

            //创建用户组和域的关系
            UserGroupDomainVo userGroupDomainVo = new UserGroupDomainVo();
            userGroupDomainVo.setOrgId(orgId);
            userGroupDomainVo.setSiteId(siteId);
            userGroupDomainVo.setAssociationType(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            userGroupDomainVo.setStatus(true);
            userGroupDomainVo.setDomainValue(fieldDomainValueVo.getValue());
            userGroupDomainVo.setDescription(fieldDomainValueVo.getDescription());
            userGroupDomainVo.setDomainNum(Common.FIELD_WO_PROJECT_TYPE);
            userGroupDomainVo.setCreator(loginName);
            userGroupDomainVo.setUserGroupId(ugroupVoForSave.getId());
            userGroupDomainClient.save(userGroupDomainVo);

        }

        // 报修工单 用户组初始化
        List<FieldDomainValueVo> roProjects = fieldDomainClient.findDomainValueByDomainNum(Common.FIELD_RO_PROJECT_TYPE);
        for (FieldDomainValueVo fieldDomainValueVo : roProjects
                ) {
            UgroupVoForSave ugroupVoForSave = new UgroupVoForSave();
            ugroupVoForSave.setOrgId(orgId);
            ugroupVoForSave.setSiteId(siteId);
            ugroupVoForSave.setCreateUser(loginName);
            ugroupVoForSave.setProductId(UASConstants.PRODUCT_EAM_ID);
            ugroupVoForSave.setCode(siteCode + Common.UGROUP_RO_PROJECTTYPE + "_" + fieldDomainValueVo.getValue());
            ugroupVoForSave.setName(Common.UGROUP_RO_PROJECTTYPE_NAME_PREFIX + fieldDomainValueVo.getDescription() + Common.UGROUP_NAME_SUFFIX_ALLOT);
            UgroupVoForDetail u = ugroupClient.add(ugroupVoForSave);

            //给用户组添加用户
            UgroupVoForUpdateUsers ugroupVoForUpdateUsers = new UgroupVoForUpdateUsers();
            ugroupVoForUpdateUsers.setCreateUser(loginName);
            ugroupVoForUpdateUsers.setUgroupId(u.getId());
            ugroupVoForUpdateUsers.setUserIds(userId.split(" "));
            ugroupClient.updateUsers(ugroupVoForUpdateUsers);

            UserGroupDomainVo userGroupDomainVo = new UserGroupDomainVo();
            userGroupDomainVo.setOrgId(orgId);
            userGroupDomainVo.setSiteId(siteId);
            userGroupDomainVo.setAssociationType(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            userGroupDomainVo.setStatus(true);
            userGroupDomainVo.setDomainValue(fieldDomainValueVo.getValue());
            userGroupDomainVo.setDescription(fieldDomainValueVo.getDescription());
            userGroupDomainVo.setDomainNum(Common.FIELD_RO_PROJECT_TYPE);
            userGroupDomainVo.setCreator(loginName);
            userGroupDomainVo.setUserGroupId(u.getId());
            userGroupDomainClient.save(userGroupDomainVo);
        }

        // 巡检工单 用户组初始化
        List<FieldDomainValueVo> poProjects = fieldDomainClient.findDomainValueByDomainNum(Common.FIELD_PO_TYPE);
        for (FieldDomainValueVo fieldDomainValueVo : poProjects
                ) {
            UgroupVoForSave ugroupVoForSave = new UgroupVoForSave();
            ugroupVoForSave.setOrgId(orgId);
            ugroupVoForSave.setSiteId(siteId);
            ugroupVoForSave.setCreateUser(loginName);
            ugroupVoForSave.setProductId(UASConstants.PRODUCT_EAM_ID);
            ugroupVoForSave.setCode(siteCode + Common.UGROUP_PO_TYPE + "_" + fieldDomainValueVo.getValue());
            ugroupVoForSave.setName(Common.UGROUP_PO_TYPE_NAME_PREFIX + fieldDomainValueVo.getDescription() + Common.UGROUP_NAME_SUFFIX_ALLOT);
            UgroupVoForDetail u = ugroupClient.add(ugroupVoForSave);

            //给用户组添加用户
            UgroupVoForUpdateUsers ugroupVoForUpdateUsers = new UgroupVoForUpdateUsers();
            ugroupVoForUpdateUsers.setCreateUser(loginName);
            ugroupVoForUpdateUsers.setUgroupId(u.getId());
            ugroupVoForUpdateUsers.setUserIds(userId.split(" "));
            ugroupClient.updateUsers(ugroupVoForUpdateUsers);

            UserGroupDomainVo userGroupDomainVo = new UserGroupDomainVo();
            userGroupDomainVo.setOrgId(orgId);
            userGroupDomainVo.setSiteId(siteId);
            userGroupDomainVo.setAssociationType(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            userGroupDomainVo.setStatus(true);
            userGroupDomainVo.setDomainValue(fieldDomainValueVo.getValue());
            userGroupDomainVo.setDescription(fieldDomainValueVo.getDescription());
            userGroupDomainVo.setDomainNum(Common.FIELD_PO_TYPE);
            userGroupDomainVo.setCreator(loginName);
            userGroupDomainVo.setUserGroupId(u.getId());
            userGroupDomainClient.save(userGroupDomainVo);
        }
        // 缺陷单 整改单 用户组初始化
        List<FieldDomainValueVo> defectProjects = fieldDomainClient.findDomainValueByDomainNum(Common.FIELD_DEFECT_TYPE);
        for (FieldDomainValueVo fieldDomainValueVo : defectProjects
                ) {

            //--------------缺陷单
            UgroupVoForSave ugroupVoForSave = new UgroupVoForSave();
            ugroupVoForSave.setOrgId(orgId);
            ugroupVoForSave.setSiteId(siteId);
            ugroupVoForSave.setCreateUser(loginName);
            ugroupVoForSave.setProductId(UASConstants.PRODUCT_EAM_ID);
            ugroupVoForSave.setCode(siteCode + Common.UGROUP_DEFECT_DOCUMENT_PROJECT + "_" + fieldDomainValueVo.getValue());
            ugroupVoForSave.setName(Common.UGROUP_DEFECT_DOCUMENT_PROJECT_NAME_PREFIX + fieldDomainValueVo.getDescription() + Common.UGROUP_NAME_SUFFIX_SURE);
            UgroupVoForDetail u = ugroupClient.add(ugroupVoForSave);

            //给用户组添加用户
            UgroupVoForUpdateUsers ugroupVoForUpdateUsers = new UgroupVoForUpdateUsers();
            ugroupVoForUpdateUsers.setCreateUser(loginName);
            ugroupVoForUpdateUsers.setUgroupId(u.getId());
            ugroupVoForUpdateUsers.setUserIds(userId.split(" "));
            ugroupClient.updateUsers(ugroupVoForUpdateUsers);

            UserGroupDomainVo userGroupDomainVo = new UserGroupDomainVo();
            userGroupDomainVo.setOrgId(orgId);
            userGroupDomainVo.setSiteId(siteId);
            userGroupDomainVo.setAssociationType(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            userGroupDomainVo.setStatus(true);
            userGroupDomainVo.setDomainValue(fieldDomainValueVo.getValue());
            userGroupDomainVo.setDescription(fieldDomainValueVo.getDescription());
            userGroupDomainVo.setDomainNum(Common.FIELD_DEFECT_TYPE);
            userGroupDomainVo.setCreator(loginName);
            userGroupDomainVo.setUserGroupId(u.getId());
            userGroupDomainClient.save(userGroupDomainVo);

            //---------------整改单
            UgroupVoForSave ugroupVoForSaveOther = new UgroupVoForSave();
            ugroupVoForSaveOther.setOrgId(orgId);
            ugroupVoForSaveOther.setSiteId(siteId);
            ugroupVoForSaveOther.setCreateUser(loginName);
            ugroupVoForSaveOther.setProductId(UASConstants.PRODUCT_EAM_ID);
            ugroupVoForSaveOther.setCode(siteCode + Common.UGROUP_DEFECT_OEDER_PROJECT + "_" + fieldDomainValueVo.getValue());
            ugroupVoForSaveOther.setName(Common.UGROUP_DEFECT_OEDER_PROJECT_NAME_PREFIX + fieldDomainValueVo.getDescription() + Common.UGROUP_NAME_SUFFIX_ALLOT);
            UgroupVoForDetail uo = ugroupClient.add(ugroupVoForSaveOther);

            //给用户组添加用户
            UgroupVoForUpdateUsers ugroupVoForUpdateUsersOther = new UgroupVoForUpdateUsers();
            ugroupVoForUpdateUsersOther.setCreateUser(loginName);
            ugroupVoForUpdateUsersOther.setUgroupId(uo.getId());
            ugroupVoForUpdateUsersOther.setUserIds(userId.split(" "));
            ugroupClient.updateUsers(ugroupVoForUpdateUsersOther);

            UserGroupDomainVo userGroupDomainVoOther = new UserGroupDomainVo();
            userGroupDomainVoOther.setOrgId(orgId);
            userGroupDomainVo.setSiteId(siteId);
            userGroupDomainVoOther.setAssociationType(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            userGroupDomainVoOther.setStatus(true);
            userGroupDomainVoOther.setDomainNum(Common.FIELD_DEFECT_TYPE);
            userGroupDomainVoOther.setDescription(fieldDomainValueVo.getDescription());
            userGroupDomainVoOther.setDomainValue(fieldDomainValueVo.getValue());
            userGroupDomainVoOther.setCreator(loginName);
            userGroupDomainVoOther.setUserGroupId(uo.getId());
            userGroupDomainClient.save(userGroupDomainVoOther);
        }

        // 例行工作单 用户组初始化
        List<FieldDomainValueVo> works = fieldDomainClient.findDomainValueByDomainNum(Common.FIELD_WORK_TYPE);
        for (FieldDomainValueVo fieldDomainValueVo : works
                ) {
            UgroupVoForSave ugroupVoForSave = new UgroupVoForSave();
            ugroupVoForSave.setOrgId(orgId);
            ugroupVoForSave.setSiteId(siteId);
            ugroupVoForSave.setCreateUser(loginName);
            ugroupVoForSave.setProductId(UASConstants.PRODUCT_EAM_ID);
            ugroupVoForSave.setCode(siteCode + Common.UGROUP_ROUTINE_WORK_ORDER_PROJECTTYPE + "_" + fieldDomainValueVo.getValue());
            ugroupVoForSave.setName(Common.UGROUP_ROUTINE_WORK_ORDER_PROJECTTYPE_NAME_PREFIX + fieldDomainValueVo.getDescription() + Common.UGROUP_NAME_SUFFIX_ALLOT);
            UgroupVoForDetail u = ugroupClient.add(ugroupVoForSave);


            //给用户组添加用户
            UgroupVoForUpdateUsers ugroupVoForUpdateUsers = new UgroupVoForUpdateUsers();
            ugroupVoForUpdateUsers.setCreateUser(loginName);
            ugroupVoForUpdateUsers.setUgroupId(u.getId());
            ugroupVoForUpdateUsers.setUserIds(userId.split(" "));
            ugroupClient.updateUsers(ugroupVoForUpdateUsers);

            UserGroupDomainVo userGroupDomainVo = new UserGroupDomainVo();
            userGroupDomainVo.setOrgId(orgId);
            userGroupDomainVo.setSiteId(siteId);
            userGroupDomainVo.setAssociationType(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
            userGroupDomainVo.setStatus(true);
            userGroupDomainVo.setDomainNum(Common.FIELD_WORK_TYPE);
            userGroupDomainVo.setDomainValue(fieldDomainValueVo.getValue());
            userGroupDomainVo.setDescription(fieldDomainValueVo.getDescription());
            userGroupDomainVo.setCreator(loginName);
            userGroupDomainVo.setUserGroupId(u.getId());
            userGroupDomainClient.save(userGroupDomainVo);
        }


        //派工工单 用户组初始化

        UgroupVoForSave ugroupVoForSave = new UgroupVoForSave();
        ugroupVoForSave.setOrgId(orgId);
        ugroupVoForSave.setSiteId(siteId);
        ugroupVoForSave.setCreateUser(loginName);
        ugroupVoForSave.setProductId(UASConstants.PRODUCT_EAM_ID);
        ugroupVoForSave.setCode(siteCode + Common.UGROUP_DISPATCH);
        ugroupVoForSave.setName(Common.UGROUP_DISPATCH_NAME);
        UgroupVoForDetail u = ugroupClient.add(ugroupVoForSave);


        //给用户组添加用户
        UgroupVoForUpdateUsers ugroupVoForUpdateUsers = new UgroupVoForUpdateUsers();
        ugroupVoForUpdateUsers.setCreateUser(loginName);
        ugroupVoForUpdateUsers.setUgroupId(u.getId());
        ugroupVoForUpdateUsers.setUserIds(userId.split(" "));
        ugroupClient.updateUsers(ugroupVoForUpdateUsers);


        UserGroupDomainVo userGroupDomainVo = new UserGroupDomainVo();
        userGroupDomainVo.setOrgId(orgId);
        userGroupDomainVo.setSiteId(siteId);
        userGroupDomainVo.setAssociationType(Common.USERGROUP_ASSOCIATION_TYPE_ALL);
        userGroupDomainVo.setStatus(true);
        userGroupDomainVo.setDomainNum(Common.UGROUP_DISPATCH);
        userGroupDomainVo.setDomainValue(Common.UGROUP_DISPATCH);
        userGroupDomainVo.setCreator(loginName);
        userGroupDomainVo.setUserGroupId(u.getId());
        userGroupDomainClient.save(userGroupDomainVo);
        return true;
    }

}
