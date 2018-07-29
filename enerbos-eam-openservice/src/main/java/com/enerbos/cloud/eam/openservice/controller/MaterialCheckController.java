package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.MaterialCheckClient;
import com.enerbos.cloud.eam.client.MaterialStoreRoomClient;
import com.enerbos.cloud.eam.vo.*;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017/3/31 10:20
 * @Description 物资盘点
 */
@RestController
@Api(description = "物资盘点(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaterialCheckController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MaterialCheckClient materialCheckClient;

    @Autowired
    public DiscoveryClient client;

    @Autowired
    public FieldDomainClient fieldDomainClient;

    @Autowired
    public MaterialStoreRoomClient materialStoreRoomClient ;

    /**
     * 分页查询物资盘点列表
     *
     * @param materialCheckVoForFilter 物资盘点查询条件实体{@link com.enerbos.cloud.eam.vo.MaterialCheckVoForFilter}
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "分页查询物资盘点列表", response = MaterialCheckVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/check/findMaterialChecks", method = RequestMethod.POST)
    public EnerbosMessage findMaterialChecks(@RequestBody MaterialCheckVoForFilter materialCheckVoForFilter) {

        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/findMaterialChecksHost：{},Port:{}    :   param --->materialCheckVoForFilter:{} ", instance.getHost(), instance.getPort(), materialCheckVoForFilter);

        try {
            EnerbosPage<MaterialCheckVoForList> pageInfo = materialCheckClient
                    .findMaterialChecks(materialCheckVoForFilter);

            if(pageInfo!=null){

                List<MaterialCheckVoForList> materialCheckVoForLists = pageInfo.getList() ;

                for (MaterialCheckVoForList materialCheckVo : materialCheckVoForLists){
                    String status = materialCheckVo.getStatus() ;
                    FieldDomainValueVo statusVo = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("checkStatus", status, materialCheckVo.getSiteId(), materialCheckVo.getOrgId(), "EAM");
                    if(statusVo!=null){
                        materialCheckVo.setStatus(statusVo.getDescription());
                    }else{
                        materialCheckVo.setStatus("");
                    }
                }
            }
            //checkStatus


            return EnerbosMessage.createSuccessMsg(pageInfo, "物资盘点查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/check/findMaterialChecks------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 新建物资盘点
     *
     * @param materialCheckVo 新建物资盘点实体 {@link com.enerbos.cloud.eam.vo.MaterialCheckVo}
     * @param user            用户
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "新建物资盘点", response = MaterialCheckVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/check/saveMaterialCheck", method = RequestMethod.POST)
    public EnerbosMessage saveMaterialCheck(
            @ApiParam(value = "物资盘点对象", required = true) @RequestBody @Valid MaterialCheckVo materialCheckVo,
            Principal user) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/saveMaterialCheck Host：{},Port:{} :   param --->materialCheckVoForFilter:{} ,user:{} ", instance.getHost(), instance.getPort(), materialCheckVo, user);
        try {
            materialCheckVo.setCreateUser(user.getName());
            return EnerbosMessage.createSuccessMsg(
                    materialCheckClient.saveMaterialCheck(materialCheckVo),
                    "新建物资盘点成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/check/saveMaterialCheck------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改物资盘点
     *
     * @param materialCheckVo 物资盘点实体{@link com.enerbos.cloud.eam.vo.MaterialCheckVo}
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "修改物资盘点", response = MaterialCheckVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/check/updateMaterialCheck", method = RequestMethod.POST)
    public EnerbosMessage updateMaterialCheck(
            @ApiParam(value = "物资盘点对象", required = true) @RequestBody @Valid MaterialCheckVo materialCheckVo) {

        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/updateMaterialCheck Host：{},Port:{} :   param --->materialCheckVo:{} ", instance.getHost(), instance.getPort(), materialCheckVo);

        try {
            return EnerbosMessage.createSuccessMsg(
                    materialCheckClient.updateMaterialCheck(materialCheckVo), "更新物资盘点成功",
                    "");
        } catch (Exception e) {
            logger.error("-----/eam/open/check/updateMaterialCheck ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 根据ID删除物资盘点
     *
     * @param ids 物资盘点id数组
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资盘点", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/check/deleteMaterialCheck", method = RequestMethod.GET)
    public EnerbosMessage deleteMaterialCheck(
            @ApiParam(value = "需要删除的物资盘点ID,支持批量.多个用逗号分隔") @RequestParam(value = "ids", required = true) String[] ids) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/deleteMaterialCheck Host：{},Port:{} :   param --->ids:{} ", instance.getHost(), instance.getPort(), ids);


        try {
            boolean isSuccess = materialCheckClient.deleteMaterialCheck(ids);
            if (isSuccess) {
                return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资盘点成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资盘点失败", "");
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/check/deleteMaterialCheck ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 根据ID查询物资盘点详细信息
     *
     * @param id 物资盘点id
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询物资盘点详细信息", response = MaterialCheckVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "库存编码", dataType = "String", required = true)})
    @RequestMapping(value = "/eam/open/check/findMaterialCheckById", method = RequestMethod.GET)
    public EnerbosMessage findMaterialCheckById(@RequestParam("id") String id) {

        try {
            MaterialCheckVo materialCheckVo = materialCheckClient.findMaterialCheckById(id);
            if (materialCheckVo != null) {

                String status = materialCheckVo.getStatus() ;
                FieldDomainValueVo statusVo = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("checkStatus", status, materialCheckVo.getSiteId(), materialCheckVo.getOrgId(), "EAM");
                if(statusVo!=null){
                    materialCheckVo.setStatus(statusVo.getDescription());
                }else{
                    materialCheckVo.setStatus("");
                }
                String storeroomId = materialCheckVo.getStoreroomId() ;
                MaterialStoreRoomVo materialStoreRoomVo =materialStoreRoomClient.findStoreRoomDetail(storeroomId);

                if(materialStoreRoomVo!=null){
                    materialCheckVo.setStoreroomName(materialStoreRoomVo.getStoreroomName());
                    materialCheckVo.setStoreroomNum(materialStoreRoomVo.getStoreroomNum());
                }

                return EnerbosMessage.createSuccessMsg(materialCheckVo, "查询物资盘点详细信息成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(materialCheckVo, "查询物资盘点详细信息失败", "");
            }
        } catch (Exception e) {
            logger.error("-----findItemDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改物资盘点状态
     *
     * @param ids    物资盘点ids
     * @param status 状态（盘点中、待确认、盘点汇报、库存调整、完成、取消、驳回）
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "修改物资盘点状态", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/check/updateMaterialCheckStatus", method = RequestMethod.POST)
    public EnerbosMessage updateMaterialCheckStatus(
            @RequestParam("ids") String[] ids,
            @RequestParam("status") String status) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/updateMaterialCheckStatus Host：{},Port:{} :   param --->ids:{}，status:{} ", instance.getHost(), instance.getPort(), ids, status);


        try {
            boolean isSuccess = materialCheckClient.updateMaterialCheckStatus(ids,
                    status);
            if (isSuccess) {
                return EnerbosMessage.createSuccessMsg(isSuccess, "修改物资盘点状态成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isSuccess, "修改物资盘点状态失败", "");
            }
        } catch (Exception e) {
            logger.error("-----updateMaterialCheckStatus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }


    /**
     * 根据物资盘点id查询盘点明细
     *
     * @param id       物资盘点id
     * @param pageNum  页数
     * @param pageSize 每页显示数据
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据物资盘点id查询盘点明细", response = MaterialCheckDetailVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/check/findMaterialCheckDetail", method = RequestMethod.GET)
    public EnerbosMessage findMaterialCheckDetail(@RequestParam("id") String id, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/findMaterialCheckDetail Host：{},Port:{} :   param --->id:{}，pageNum:{},pageSize:{} ", instance.getHost(), instance.getPort(), id, pageNum, pageSize);


        try {
            EnerbosPage<MaterialCheckDetailVoForList> materialCheckDetailVoForList = materialCheckClient.findMaterialCheckDetail(id, pageNum, pageSize);
            return EnerbosMessage.createSuccessMsg(materialCheckDetailVoForList, "修改物资盘点状态成功", "");
        } catch (Exception e) {
            logger.error("-----findMaterialCheckDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }


    /**
     * 根据ID删除物资盘点明细
     *
     * @param ids 物资盘点明细id数组
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资盘点明细", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/check/deleteMaterialCheckDetail", method = RequestMethod.GET)
    public EnerbosMessage deleteMaterialCheckDetail(
            @ApiParam(value = "需要删除的物资盘点明细ID,支持批量.多个用逗号分隔") @RequestParam(value = "ids", required = true) String[] ids) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/deleteMaterialCheckDetail Host：{},Port:{} :   param --->ids:{} ", instance.getHost(), instance.getPort(), ids);


        try {
            boolean isSuccess = materialCheckClient.deleteMaterialCheckDetail(ids);
            if (isSuccess) {
                return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资盘点成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资盘点失败", "");
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/check/deleteMaterialCheckDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 新建物资盘点明细
     *
     * @param materialCheckDetailVo 新建物资盘点实体 {@link com.enerbos.cloud.eam.vo.MaterialCheckDetailVo}
     * @param user                  用户
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "新建物资盘点明细", response = MaterialCheckDetailVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/check/saveMaterialCheckDetail", method = RequestMethod.POST)
    public EnerbosMessage saveMaterialCheckDetail(
            @ApiParam(value = "物资盘点对象", required = true) @RequestBody @Valid MaterialCheckDetailVo materialCheckDetailVo,
            Principal user) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/saveMaterialCheckDetail Host：{},Port:{} :   param --->materialCheckVoForFilter:{} ,user:{} ", instance.getHost(), instance.getPort(), materialCheckDetailVo, user);

        try {

            materialCheckDetailVo.setCreateUser(user.getName());

            return EnerbosMessage.createSuccessMsg(
                    materialCheckClient.saveMaterialCheckDetail(materialCheckDetailVo),
                    "新建物资盘点成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/check/saveMaterialCheck------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 根据物资库存id查询 盘点记录
     *
     * @param id       物资库存id
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据物资库存id查询 盘点记录", response = MaterialCheckVoForInventoryList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/check/findMaterialCheckByInvtoryId", method = RequestMethod.GET)
    public EnerbosMessage findMaterialCheckByInvtoryId(
            @ApiParam(value = "物资库存id", required = true) @RequestParam("id") String id, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/check/findMaterialCheckByInvtoryId Host：{},Port:{} :   param --->id:{} ,pageNum:{},pageSize{} ", instance.getHost(), instance.getPort(), id, pageNum,pageSize);
        try {
//            materialCheckDetailVo.setSiteId(PrincipalUserUtils.getSiteIdByUser(Json
//                    .pretty(user).toString()));
//            materialCheckDetailVo.setOrgId(PrincipalUserUtils.getOrgIdByUser(Json
//                    .pretty(user).toString()));
//            materialCheckDetailVo.setCreateUser(user.getName());
            EnerbosPage<MaterialCheckVoForInventoryList> page = materialCheckClient.findMaterialCheckByInvtoryId(id,pageNum,pageSize) ;
            return EnerbosMessage.createSuccessMsg(page,"根据物资库存id查询 盘点记录成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/check/findMaterialCheckByInvtoryId------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


}
