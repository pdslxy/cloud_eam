package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.FieldDomainClient;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.MaterialGoodsReceiveClient;
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
 */
@RestController
@Api(description = "物资接收(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaterialGoodsReceiveController {

    @Autowired
    public MaterialGoodsReceiveClient materialGoodsReceiveClient;

    @Autowired
    public DiscoveryClient client;

    @Autowired
    public MaterialStoreRoomClient materialStoreRoomClient;

    @Autowired
    public FieldDomainClient fieldDomainClient;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 分页查询物资接收列表
     *
     * @param materialGoodsReceiveVoForFilter 查询条件实体 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForFilter}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "分页查询物资接收列表", response = MaterialGoodsReceiveVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/goodsreceive/findGoodsReceives", method = RequestMethod.POST)
    public EnerbosMessage findGoodsReceives(@RequestBody
                                                    MaterialGoodsReceiveVoForFilter materialGoodsReceiveVoForFilter) {
        try {

            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/goodsreceive/findGoodsReceives ： Host：{},Port:{}  param:{}",
                    instance.getHost(), instance.getPort(),
                    materialGoodsReceiveVoForFilter);
            EnerbosPage<MaterialGoodsReceiveVoForList> pageInfo = materialGoodsReceiveClient
                    .findGoodsReceives(materialGoodsReceiveVoForFilter);

            if (pageInfo != null) {

                List<MaterialGoodsReceiveVoForList> list = pageInfo.getList();

                for (MaterialGoodsReceiveVoForList goodsReceive : list) {
                    String status = goodsReceive.getStatus();

                    FieldDomainValueVo statusVo = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("receiveStatus", status, goodsReceive.getSiteId(), goodsReceive.getOrgId(), "EAM");
                    if (statusVo != null) {
                        goodsReceive.setStatus(statusVo.getDescription());
                    } else {
                        goodsReceive.setStatus("");
                    }
                }

            }

            return EnerbosMessage.createSuccessMsg(pageInfo, "物资接收查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/goodsreceive/findGoodsReceives------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 新建物资接收
     *
     * @param materialGoodsReceiveVo 物资接受实体 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVo}
     * @param user                   用户
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "新建物资接收", response = MaterialGoodsReceiveVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/goodsreceive/saveGoodsReceive", method = RequestMethod.POST)
    public EnerbosMessage saveGoodsReceive(
            @ApiParam(value = "物资接收对象", required = true) @RequestBody @Valid MaterialGoodsReceiveVo materialGoodsReceiveVo,
            Principal user) {
        try {

            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/goodsreceive/saveGoodsReceive ： Host：{},Port:{}  param:{}",
                    instance.getHost(), instance.getPort(),
                    materialGoodsReceiveVo);

            materialGoodsReceiveVo.setCreateUser(user.getName());

            return EnerbosMessage.createSuccessMsg(materialGoodsReceiveClient
                    .saveGoodsReceive(materialGoodsReceiveVo), "新建物资接收成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/goodsreceive/saveGoodsReceive ------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改物资接收
     *
     * @param materialGoodsReceiveVo 修改实体  {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVo}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "修改物资接收", response = MaterialGoodsReceiveVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/goodsreceive/updateGoodsReceive", method = RequestMethod.POST)
    public EnerbosMessage updateGoodsReceive(
            @ApiParam(value = "物资接收对象", required = true) @RequestBody @Valid MaterialGoodsReceiveVo materialGoodsReceiveVo) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/goodsreceive/updateGoodsReceive ： Host：{},Port:{}  param:{}",
                instance.getHost(), instance.getPort(), materialGoodsReceiveVo);
        try {
            return EnerbosMessage
                    .createSuccessMsg(materialGoodsReceiveClient
                                    .updateGoodsReceive(materialGoodsReceiveVo),
                            "更新物资接收成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/goodsreceive/updateGoodsReceive ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 根据id删除 支持批量删除
     *
     * @param ids id数组
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资接收", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/goodsreceive/deleteGoodsReceive", method = RequestMethod.POST)
    public EnerbosMessage deleteGoodsReceive(
            @ApiParam(value = "需要删除的物资接收ID,支持批量.多个用逗号分隔", required = true) @RequestParam("ids") String ids[]) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/goodsreceive/deleteGoodsReceive ： Host：{},Port:{}  param:{}",
                instance.getHost(), instance.getPort(), ids);

        try {

            return EnerbosMessage.createSuccessMsg(
                    materialGoodsReceiveClient.deleteGoodsReceive(ids),
                    "删除物资接收成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/goodsreceive/deleteGoodsReceive------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据id查询详细信息
     *
     * @param id 物资接收id
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询物资接收详细信息", response = MaterialGoodsReceiveVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "物资接收编码", dataType = "String", required = true)})
    @RequestMapping(value = "/eam/open/goodsreceive/findGoodsreceiveById", method = RequestMethod.GET)
    public EnerbosMessage findGoodsreceiveById(
            @RequestParam(value = "id", required = true) String id) {


        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info(
                "/eam/open/goodsreceive/deleteGoodsReceive ： Host：{},Port:{}  param:{}",
                instance.getHost(), instance.getPort(), id);
        try {

            MaterialGoodsReceiveVo materialGoodsReceiveVo = materialGoodsReceiveClient
                    .findGoodsreceiveById(id);

            if (materialGoodsReceiveVo != null) {


                String status = materialGoodsReceiveVo.getStatus();

                FieldDomainValueVo statusVo = fieldDomainClient.findDomainValueByDomainValueNumAndSiteIdAndProId("receiveStatus", status, materialGoodsReceiveVo.getSiteId(), materialGoodsReceiveVo.getOrgId(), "EAM");
                if (statusVo != null) {
                    materialGoodsReceiveVo.setStatusName(statusVo.getDescription());
                } else {
                    materialGoodsReceiveVo.setStatusName("");
                }


                String storeroomid = materialGoodsReceiveVo.getStoreroomId();

                MaterialStoreRoomVo materialStoreRoomVo = materialStoreRoomClient.findStoreRoomDetail(storeroomid);
                if (materialGoodsReceiveVo != null) {
                    materialGoodsReceiveVo.setStoreroomName(materialStoreRoomVo.getStoreroomName());
                    materialGoodsReceiveVo.setStoreroomNum(materialStoreRoomVo.getStoreroomNum());
                }


                return EnerbosMessage.createSuccessMsg(materialGoodsReceiveVo,
                        "根据ID查询物资接收详细信息成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(null,
                        "id不存在", "");
            }

        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/goodsreceive/findGoodsreceiveDetail ------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询物资接收单明细并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param id       物资接收id
     * @param pageNum  页数
     * @param pageSize 一页显示的行数
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "分页查询物资接收明细列表", response = MaterialGoodsReceiveDetailVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "物资接收id", dataType = "String", required = true),
            @ApiImplicitParam(paramType = "query", name = "pageNum", value = "当前页", dataType = "int", required = true, defaultValue = "0"),
            @ApiImplicitParam(paramType = "query", name = "pageSize", value = "每页行数", dataType = "int", required = true, defaultValue = "10")})
    @RequestMapping(value = "/eam/open/goodsreceive/findGoodsReceiveDetailByGoodsReceiveId", method = RequestMethod.GET)
    public EnerbosMessage findGoodsReceiveDetailByGoodsReceiveId(
            @RequestParam("id") String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize) {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/goodsreceive/findGoodsReceiveDetailByGoodsReceiveId ： Host：{},Port:{}  param:id:{} pageNum :{} pageSize ：{}",
                instance.getHost(), instance.getPort(), id, pageNum, pageSize);

        try {
            EnerbosPage<MaterialGoodsReceiveDetailVoForList> pageInfo = materialGoodsReceiveClient
                    .findGoodsReceiveDetailByGoodsReceiveId(id, pageNum,
                            pageSize);
            return EnerbosMessage.createSuccessMsg(pageInfo, "物资接收查询成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/goodsreceive/findGoodsReceiveDetailByGoodsReceiveId ------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 新建物资接收单明细
     *
     * @param materialGoodsReceiveDetailVo 新建的实体 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVo}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "新建物资接收明细", response = MaterialGoodsReceiveDetailVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/goodsreceive/saveGoodsReceiveDetail", method = RequestMethod.POST)
    public EnerbosMessage saveGoodsReceiveDetail(
            @ApiParam(value = "物资接收对象", required = true) @RequestBody @Valid MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo,
            Principal user) {
        try {

            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/goodsreceive/saveGoodsReceiveDetail ： Host：{},Port:{}  param:materialGoodsReceiveDetailVo:{} user :{} ",
                    instance.getHost(), instance.getPort(),
                    materialGoodsReceiveDetailVo, user);

            materialGoodsReceiveDetailVo.setCreateUser(user.getName());
            return EnerbosMessage.createSuccessMsg(materialGoodsReceiveClient
                            .saveGoodsReceiveDetail(materialGoodsReceiveDetailVo),
                    "新建物资接收明细成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/goodsreceive/saveGoodsReceiveDetail------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改物资接收单明细
     *
     * @param materialGoodsReceiveDetailVo 修改的物资接收单明细 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVo}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "修改物资接收明细", response = MaterialGoodsReceiveDetailVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/goodsreceive/updateGoodsReceiveDetail", method = RequestMethod.POST)
    public EnerbosMessage updateGoodsReceiveDetail(
            @RequestBody MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo, Principal user) {

        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info(
                "/eam/open/goodsreceive/updateGoodsReceiveDetail ： Host：{},Port:{}  param:materialGoodsReceiveDetailVo:{}   ",
                instance.getHost(), instance.getPort(),
                materialGoodsReceiveDetailVo);
        try {
            materialGoodsReceiveDetailVo.setChangeUser(user.getName());
            return EnerbosMessage.createSuccessMsg(materialGoodsReceiveClient
                            .updateGoodsReceiveDetail(materialGoodsReceiveDetailVo),
                    "更新物资接收明细成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/goodsreceive/updateGoodsReceiveDetail ------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 删除物资接收单明细
     *
     * @param ids 物资接收单明细ID
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除物资接收明细", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/goodsreceive/deleteGoodsReceiveDetail", method = RequestMethod.POST)
    public EnerbosMessage deleteGoodsReceiveDetail(
            @RequestParam("ids") String ids[]) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/goodsreceive/deleteGoodsReceiveDetail ： Host：{},Port:{}  param:ids:{}   ",
                    instance.getHost(), instance.getPort(), ids);

            boolean isSuccess = materialGoodsReceiveClient
                    .deleteGoodsReceiveDetail(ids);
            return EnerbosMessage.createSuccessMsg(isSuccess, "删除物资接收明细成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/goodsreceive/deleteGoodsReceiveDetail ------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 更改物资接收状态
     *
     * @param ids    物资接收id
     * @param status 状态(已接受，部分接收，草稿，取消)
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID修改状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/goodsreceive/updateGoodsReceiveStatus", method = RequestMethod.POST)
    public EnerbosMessage updateGoodsReceiveStatus(
            @RequestParam("ids") String[] ids,
            @RequestParam("status") String status) {
        try {
            ServiceInstance instance = client.getLocalServiceInstance();
            logger.info(
                    "/eam/open/goodsreceive/updateGoodsReceiveStatus ： Host：{},Port:{}  param:ids:{},status:{}   ",
                    instance.getHost(), instance.getPort(), ids, status);

            boolean isSuccess = materialGoodsReceiveClient
                    .updateGoodsReceiveStatus(ids, status);
            return EnerbosMessage.createSuccessMsg(isSuccess, "修改物资接收状态成功", "");
        } catch (Exception e) {
            logger.error(
                    "-----/eam/open/goodsreceive/updateGoodsReceiveStatus ------",
                    e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

}
