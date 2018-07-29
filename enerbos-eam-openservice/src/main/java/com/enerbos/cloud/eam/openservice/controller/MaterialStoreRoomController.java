package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.MaterialStoreRoomClient;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVo;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForList;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.client.SiteClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.uas.vo.site.SiteVoForDetail;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
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
 * @Description 库房管理
 */
@RestController
@Api(description = "库房管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class MaterialStoreRoomController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public MaterialStoreRoomClient materialStoreRoomClient;

    @Autowired
    public PersonAndUserClient personAndUserClient;

    @Autowired
    public SiteClient siteClient;

    @Autowired
    private DiscoveryClient client;

    /**
     * 分页查询库房列表
     *
     * @param materialStoreRoomVoForFilter 库房查询条件实体{@link com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForFilter}
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "分页查询库房列表", response = MaterialStoreRoomVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/storeroom/findStoreRooms", method = RequestMethod.POST)
    public EnerbosMessage findStoreRooms(
            @RequestBody MaterialStoreRoomVoForFilter materialStoreRoomVoForFilter) {

        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info(
                "/eam/open/inventory/findInventorysNotInItems, host: [{}:{}], service_id: {}, param：materialStoreRoomVoForFilter=: {}",
                instance.getHost(), instance.getPort(),
                instance.getServiceId(), materialStoreRoomVoForFilter);
        try {
            EnerbosPage<MaterialStoreRoomVoForList> pageInfo = materialStoreRoomClient
                    .findStoreRooms(materialStoreRoomVoForFilter);

            List<MaterialStoreRoomVoForList> materialStoreRoomVoForList = pageInfo
                    .getList();

            if (materialStoreRoomVoForFilter != null) {

                for (MaterialStoreRoomVoForList storeRoom : materialStoreRoomVoForList) {

                    String personId = storeRoom.getPersonId();

                    PersonAndUserVoForDetail pvo = personAndUserClient.findByPersonId(personId);

                    if (pvo != null) {
                        String personName = pvo.getName();
                        storeRoom.setPersonName(personName);
                    }
                }
            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "库房查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/storeroom/findStoreRooms ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }

    /**
     * 新建库房
     *
     * @param materialStoreRoomVo 库房实体{@link com.enerbos.cloud.eam.vo.MaterialStoreRoomVo}
     * @param user                用户
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "新建库房", response = MaterialStoreRoomVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/storeroom/saveStoreRoom", method = RequestMethod.POST)
    public EnerbosMessage saveStoreRoom(
            @ApiParam(value = "库房对象", required = true) @RequestBody @Valid MaterialStoreRoomVo materialStoreRoomVo,
            Principal user) {

        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/storeroom/saveStoreRoom, host: [{}:{}], service_id: {}, param：materialStoreRoomVoForFilter=: {}",
                instance.getHost(), instance.getPort(),
                instance.getServiceId(), materialStoreRoomVo);
        try {
            materialStoreRoomVo.setCreateUser(user.getName());
            return EnerbosMessage.createSuccessMsg(
                    materialStoreRoomClient.saveStoreRoom(materialStoreRoomVo),
                    "新建库房成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/storeroom/saveStoreRoom ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 修改库房管理
     *
     * @param materialStoreRoomVo 库房实体{@link com.enerbos.cloud.eam.vo.MaterialStoreRoomVo}
     * @param user                用户
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "修改库房管理", response = MaterialStoreRoomVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/storeroom/updateStoreRoom", method = RequestMethod.POST)
    public EnerbosMessage updateStoreRoom(
            @ApiParam(value = "库房对象", required = true) @RequestBody @Valid MaterialStoreRoomVo materialStoreRoomVo,
            Principal user) {

        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/storeroom/updateStoreRoom, host: [{}:{}], service_id: {}, param：materialStoreRoomVoForFilter=: {}",
                instance.getHost(), instance.getPort(),
                instance.getServiceId(), materialStoreRoomVo);
        try {

            return EnerbosMessage.createSuccessMsg(
                    materialStoreRoomClient.updateStoreRoom(materialStoreRoomVo),
                    "修改库房成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/storeroom/updateStoreRoom ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改库房状态
     *
     * @param ids    库房id
     * @param status 状态（活动、不活动）
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "修改库房状态", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/storeroom/changeStroeRoomStatus", method = RequestMethod.POST)
    public EnerbosMessage changeStroeRoomStatus(
            @ApiParam(value = "库房对象", required = true) @RequestParam(value = "ids", required = true) String[] ids,
            @RequestParam(value = "status", required = true) String status) {

        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/storeroom/changeStroeRoomStatus, host: [{}:{}], service_id: {}, param：ids=: {},status={}",
                instance.getHost(), instance.getPort(),
                instance.getServiceId(), ids, status);
        try {
            return EnerbosMessage.createSuccessMsg(materialStoreRoomClient
                    .changeStroeRoomStatus(ids, status), "更新库房状态成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/storeroom/changeStroeRoomStatus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据ID删除库房
     *
     * @param ids 库房id数组
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID删除库房", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/storeroom/deleteStoreRoom", method = RequestMethod.POST)
    public EnerbosMessage deleteStoreRoom(
            @ApiParam(value = "需要删除的库存ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = true) String ids[]) {

        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info("/eam/open/storeroom/saveStoreRoom, host: [{}:{}], service_id: {}, param：ids=: {}",
                instance.getHost(), instance.getPort(),
                instance.getServiceId(), ids);

        try {
            String message = materialStoreRoomClient.deleteStoreRoom(ids);

            if (message.equals(HttpStatus.OK.toString())) {
                return EnerbosMessage.createSuccessMsg(true, "删除库房成功", "");
            } else {

                return EnerbosMessage.createSuccessMsg(false, message, "");

            }
        } catch (Exception e) {
            logger.error("-----/eam/open/storeroom/deleteStoreRoom------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 根据ID查询库房详细信息
     *
     * @param id 库房id
     * @return EnerbosMessage 返回执行码及数据
     */
    @ApiOperation(value = "根据ID查询库房详细信息", response = MaterialStoreRoomVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "库房编码", dataType = "String", required = true)})
    @RequestMapping(value = "/eam/open/storeroom/findStoreRoomById", method = RequestMethod.GET)
    public EnerbosMessage findStoreRoomDetail(@RequestParam("id") String id) {
        try {
            MaterialStoreRoomVo materialStoreRoomVo = materialStoreRoomClient
                    .findStoreRoomDetail(id);
            if (materialStoreRoomVo != null) {
                String siteId = materialStoreRoomVo.getSiteId();

                SiteVoForDetail siteVo = siteClient.findById(siteId);
                if (siteVo != null) {
                    materialStoreRoomVo.setSiteName(siteVo.getName());
                }

                String personid = materialStoreRoomVo.getPersonId();
                PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personid);
                if (personVo != null) {
                    materialStoreRoomVo.setPersonName(personVo.getName());
                }

                return EnerbosMessage.createSuccessMsg(materialStoreRoomVo,
                        "根据ID查询库存详细信息成功", "");
            } else {
                return EnerbosMessage.createSuccessMsg(null, "此id不存在", "");
            }

        } catch (Exception e) {
            logger.error("-----findStoreRoomDetail ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }


    /**
     * 根据itemNum查询组织站点下可用的库房详细信息
     *
     * @param itemNum 物资台账ID
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "根据itemNum查询组织站点下可用的库房详细信息", response = MaterialStoreRoomVoForList.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/storeroom/findUsableStoreRoom", method = RequestMethod.GET)
    public EnerbosMessage findUsableStoreRoom(@RequestParam("itemNum") String itemNum, @RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize")Integer pageSize) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info(
                "/eam/open/inventory/findUsableStoreRoom, host: [{}:{}], service_id: {}, param：itemNum=: {}",
                instance.getHost(), instance.getPort(),
                instance.getServiceId(), itemNum);

        try {

            EnerbosPage<MaterialStoreRoomVoForList> pageInfo = materialStoreRoomClient
                    .findUsableStoreRoom(itemNum, orgId, siteId,pageNum,pageSize);

            List<MaterialStoreRoomVoForList> materialStoreRoomVoForList = pageInfo
                    .getList();

            if (materialStoreRoomVoForList != null) {

                for (MaterialStoreRoomVoForList storeRoom : materialStoreRoomVoForList) {

                    String personId = storeRoom.getPersonId();

                    PersonAndUserVoForDetail pvo = personAndUserClient.findByPersonId(personId);

                    if (pvo != null) {
                        String personName = pvo.getName();
                        storeRoom.setPersonName(personName);
                    }
                }
            }

            return EnerbosMessage.createSuccessMsg(pageInfo, "库房查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/storeroom/findUsableStoreRoom ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询站点下是否有默认库房
     *
     * @return 返回执行码及数据
     */
    @ApiOperation(value = "查询站点下是否有默认库房", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/storeroom/findhasdefault", method = RequestMethod.GET)
    public EnerbosMessage findhasdefault(@RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId) {
        ServiceInstance instance = client.getLocalServiceInstance();

        logger.info(
                "/eam/open/inventory/findhasdefault, host: [{}:{}], service_id: {}, param：siteId=: {}",
                instance.getHost(), instance.getPort(),
                instance.getServiceId(),siteId);

        try {

            return EnerbosMessage.createSuccessMsg(materialStoreRoomClient.findhasdefault(siteId,orgId), "库房查询成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/open/storeroom/findhasdefault ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


}
