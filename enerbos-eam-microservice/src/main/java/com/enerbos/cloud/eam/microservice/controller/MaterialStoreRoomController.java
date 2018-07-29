package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.microservice.domain.MaterialRfCollector;
import com.enerbos.cloud.eam.microservice.domain.MaterialStoreRoom;
import com.enerbos.cloud.eam.microservice.service.MaterialItemService;
import com.enerbos.cloud.eam.microservice.service.MaterialStoreRoomService;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVo;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForFilter;
import com.enerbos.cloud.eam.vo.MaterialStoreRoomVoForList;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
public class MaterialStoreRoomController {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    public MaterialStoreRoomService storeRoomService;

    @Autowired
    public MaterialItemService itemService;
    /**
     * 按照参数查询库房并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialStoreRoomVoForFilter 查询过滤条件实体类
     * @return
     */
    @RequestMapping(value = "/eam/micro/storeroom/findStoreRooms", method = RequestMethod.POST)
    public PageInfo<MaterialStoreRoomVoForList> findStoreRooms(
            @RequestBody MaterialStoreRoomVoForFilter materialStoreRoomVoForFilter) {

        logger.info("/eam/micro/storeroom/findStoreRooms.params : ",
                materialStoreRoomVoForFilter);
        PageInfo<MaterialStoreRoomVoForList> materialStoreRoomVoForListPageInfo = null;

        try {
            materialStoreRoomVoForListPageInfo = storeRoomService
                    .findStoreRooms(materialStoreRoomVoForFilter);

            if (materialStoreRoomVoForListPageInfo != null) {
                List<MaterialStoreRoomVoForList> materialStoreRoomVoForLists = materialStoreRoomVoForListPageInfo.getList();
                for (MaterialStoreRoomVoForList materialStoreRoomVoForList : materialStoreRoomVoForLists) {
                    MaterialRfCollector materialRfCollector = itemService.findcollect(materialStoreRoomVoForList.getId(), "storeroom", Common.EAM_PROD_IDS[0]);
                    if (materialRfCollector != null) {
                        materialStoreRoomVoForList.setCollect(true);
                    } else {
                        materialStoreRoomVoForList.setCollect(false);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("--------/eam/micro/storeroom/findStoreRooms-------",
                    e);
        }

        return materialStoreRoomVoForListPageInfo;
    }

    /**
     * 新建库房
     *
     * @param materialStoreRoomVo 新建的库房实体 {@link com.enerbos.cloud.eam.vo.MaterialStoreRoomVo}
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/storeroom/saveStoreRoom", method = RequestMethod.POST)
    public MaterialStoreRoomVo saveStoreRoom(
            @RequestBody MaterialStoreRoomVo materialStoreRoomVo) {

        logger.info(
                "/eam/micro/storeroom/saveStoreRoom   param: materialStoreRoomVo:{}",
                materialStoreRoomVo.toString());
        MaterialStoreRoom storeRoom = new MaterialStoreRoom();

        try {
            ReflectionUtils
                    .copyProperties(materialStoreRoomVo, storeRoom, null);
            storeRoom = storeRoomService.saveStoreRoom(storeRoom);
            materialStoreRoomVo.setId(storeRoom.getId());
        } catch (Exception e) {
            logger.error("-----/eam/micro/storeroom/saveStoreRoom ------", e);
        }
        return materialStoreRoomVo;
    }

    /**
     * 修改库房状态
     *
     * @param ids    库房id数组
     * @param status 状态(活动、不活动)
     * @return 修改是否成功
     */
    @RequestMapping(value = "/eam/micro/storeroom/changeStroeRoomStatus", method = RequestMethod.POST)
    public boolean changeStroeRoomStatus(
            @RequestParam(value = "ids", required = true) String[] ids,
            @RequestParam(value = "status", required = true) String status) {

        logger.info(
                "/eam/micro/storeroom/changeStroeRoomStatus  param ids={},status={}",
                ids, status);
        try {
            this.storeRoomService.changeStroeRoomStatus(ids, status);
        } catch (Exception e) {
            logger.error("/eam/micro/storeroom/changeStroeRoomStatus", e);
            return false;
        }

        return true;

    }

    /**
     * 删除库房
     *
     * @param ids 库房ID数组
     */
    @RequestMapping(value = "/eam/micro/storeroom/deleteStoreRoom", method = RequestMethod.POST)
    public String deleteStoreRoom(@RequestParam("ids") String ids[]) {

        try {
            storeRoomService.deleteStoreRoom(ids);

        } catch (Exception e) {
            logger.error("--/eam/micro/storeroom/deleteStoreRoom--", e);
            return e.getMessage() ;
        }
        return HttpStatus.OK.toString();
    }

    /**
     * 查询库房详细信息
     *
     * @param id 库房id
     * @return 返回库房实体
     */
    @RequestMapping(value = "/eam/micro/storeroom/findStoreRoomDetail", method = RequestMethod.GET)
    public MaterialStoreRoom findStoreRoomDetail(@RequestParam("id") String id) {
        MaterialStoreRoom materialStoreRoom = null;
        try {
            materialStoreRoom = storeRoomService.findStoreRoomDetail(id);
        } catch (Exception e) {
            logger.error("--/eam/micro/storeroom/findStoreRoomDetail--", e);
        }
        return materialStoreRoom;

    }

    /**
     * 修改库房记录
     *
     * @param materialStoreRoomVo 库房实体
     * @return 返回修改后的库房实体
     */
    @RequestMapping(value = "/eam/micro/storeroom/updateStoreRoom", method = RequestMethod.POST)
    public MaterialStoreRoom updateStoreRoom(
            @RequestBody MaterialStoreRoomVo materialStoreRoomVo) {

        logger.info(
                "/eam/micro/storeroom/updateStoreRoom micro param entity is : {}",
                materialStoreRoomVo.toString());
        String id = materialStoreRoomVo.getId();

        MaterialStoreRoom materialStoreRoom = storeRoomService
                .findStoreRoomDetail(id);
        try {

            if (materialStoreRoom != null) {

                materialStoreRoomVo.setCreateUser(materialStoreRoom.getCreateUser());
                materialStoreRoomVo.setStoreroomNum(materialStoreRoom.getStoreroomNum());
                ReflectionUtils.copyProperties(materialStoreRoomVo,
                        materialStoreRoom, null);
                materialStoreRoom = storeRoomService.updateStoreRoom(materialStoreRoom);
            } else {
                new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "修改的记录不存在");
            }
        } catch (Exception e) {
            logger.error("--/eam/micro/storeroom/updateStoreRoom--", e);
            return null;
        }
        return materialStoreRoom;

    }

    /**
     * 根据itemNum查询组织站点下可用的库房详细信息
     *
     * @param itemNum 物资台账编码
     * @param orgId   组织id
     * @param siteId  站点id
     * @return 库房列表
     */
    @RequestMapping(value = "/eam/micro/storeroom/findUsableStoreRoom", method = RequestMethod.GET)
    public PageInfo<MaterialStoreRoomVoForList> findUsableStoreRoom(@RequestParam("itemNum") String itemNum, @RequestParam(value = "orgId",required = false) String orgId, @RequestParam(value="siteId",required = false) String siteId,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize")Integer pageSize) {
        logger.info("/eam/micro/storeroom/findUsableStoreRoom   .params : itemNum:{},orgId:{},siteId:{} ",
                itemNum,orgId,siteId);
        PageInfo<MaterialStoreRoomVoForList> materialStoreRoomVoForListPageInfo = null;

        try {
            materialStoreRoomVoForListPageInfo = storeRoomService
                    .findUsableStoreRoom(itemNum,orgId,siteId,pageNum,pageSize);
        } catch (Exception e) {
            logger.error("--------/eam/micro/storeroom/findStoreRooms-------",
                    e);
        }
        return materialStoreRoomVoForListPageInfo ;
    }

    /**
     * 查询站点下是否有默认库房
     *
     * @return 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/storeroom/findhasdefault", method = RequestMethod.GET)
    boolean findhasdefault(@RequestParam("siteId") String siteId, @RequestParam("orgId") String orgId){

        boolean hasDefault = false ;
        try {
            hasDefault = storeRoomService
                    .findhasdefault(siteId,orgId);
        } catch (Exception e) {
            logger.error("--------/eam/micro/storeroom/findhasdefault-------",
                    e);
        }
        return hasDefault ;
    }
}
