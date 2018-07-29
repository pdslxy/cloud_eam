package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.microservice.domain.HeadArchivesType;
import com.enerbos.cloud.eam.microservice.service.HeadArchivesTypeService;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVo;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForFilter;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForList;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Enerbos on 2016/10/19.
 */

@RestController
public class HeadArchivesTypeController {

    private static final Logger logger = LoggerFactory
            .getLogger(HeadArchivesTypeController.class);

    @Autowired
    private HeadArchivesTypeService headArchivesTypeService;


    /**
     * 根据过滤条和分页信息获取档案类型管理列表
     *
     * @return
     */
    @RequestMapping("/eam/micro/headArchivesType/getArchivesTypeList")
    @ResponseBody
    public PageInfo<HeadArchivesTypeVo> getArchivesTypeList(
            @RequestBody HeadArchivesTypeVoForFilter headArchivesTypeVoForFilter) {
        PageInfo<HeadArchivesTypeVo> pageInfo = headArchivesTypeService.getArchivesTypeList(headArchivesTypeVoForFilter);
        return pageInfo;
    }

    /**
     * 删除档案类型
     *
     * @param ids
     * @return
     */
    @RequestMapping("/eam/micro/headArchivesType/deleteArchivesType")
    @ResponseBody
    public boolean deleteArchivesType(
            @RequestParam(value = "ids", required = false) String[] ids) {
        try {
           return headArchivesTypeService.deleteArchivesType(ids);

        } catch (Exception e) {
            logger.error("-------deleteArchivesType--------------", e);
            return false;
        }
    }

    /**
     * 新建档案类型
     *
     * @param headArchivesTypeVo 新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping("/eam/micro/headArchivesType/saveArchivesType")
    @ResponseBody
    public HeadArchivesTypeVo saveArchivesType(
            @RequestBody @Valid HeadArchivesTypeVo headArchivesTypeVo) {
        logger.info("---------------------------------{}",headArchivesTypeVo);
        HeadArchivesType headArchivesType = new HeadArchivesType();
        try {
            ReflectionUtils.copyProperties(headArchivesTypeVo, headArchivesType, null);
        } catch (Exception e) {
            logger.error("-----saveArchivesType ------", e);
        }
        headArchivesType = headArchivesTypeService.saveArchivesType(headArchivesType);
        headArchivesType.setId(headArchivesType.getId());
        return headArchivesTypeVo;
    }

    /**
     * 修改档案类型
     *
     * @param headArchivesTypeVo 修改的档案类型实体
     * @return 修改后的档案类型实体
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/updateArchivesType", method = RequestMethod.POST)
    public HeadArchivesType updateArchivesType(
            @RequestBody @Valid HeadArchivesTypeVo headArchivesTypeVo) {

        HeadArchivesType headArchivesType = new HeadArchivesType();
        try {
            headArchivesType = this.findArchivesTypeDetail(headArchivesTypeVo.getId());
            headArchivesType.setTypeName(headArchivesTypeVo.getTypeName());
        } catch (Exception e) {
            logger.error("-----updateEnergyPrice ------", e);
        }
        return headArchivesTypeService.updateArchivesType(headArchivesType);
    }

    /**
     * 查询档案类型详细信息
     *
     * @param id 档案类型id
     * @return 返回档案类型实体
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/findArchivesTypeDetail", method = RequestMethod.GET)
    public HeadArchivesType findArchivesTypeDetail(@RequestParam("id") String id) {
        return headArchivesTypeService.findArchivesTypeDetail(id);
    }

    /**
     * 修改档案类型状态
     *
     * @param archivesTypeId 档案类型id
     * @param status         状态
     * @return 修改后返回的实体类
     */

    @RequestMapping(value = "/eam/micro/headArchivesType/updateArchivesTypeStatus", method = RequestMethod.POST)
    public HeadArchivesType updateArchivesTypeStatus(
            @RequestParam("archivesTypeId") String archivesTypeId,
            @RequestParam("status") String status) {
        HeadArchivesType headArchivesType = headArchivesTypeService.findArchivesTypeDetail(archivesTypeId);
        //headArchivesType.setStatus(status);
        return headArchivesTypeService.saveArchivesType(headArchivesType);
    }

    /**
     * 查询类型树
     *
     * @param siteId
     * @param orgId
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/getArchivesTypeTree", method = RequestMethod.GET)
    List<HeadArchivesTypeVoForList> getArchivesTypeTree(@RequestParam(value = "siteId",required = false) String siteId, @RequestParam("orgId") String orgId) {

        List<HeadArchivesTypeVoForList> archivesTypeVoForLists = headArchivesTypeService.findArchivesTypeAll(siteId, orgId);

        archivesTypeVoForLists = headArchivesTypeService.getArchivesTypeTree(archivesTypeVoForLists, null);

        return archivesTypeVoForLists;
    }


    /**
     * 根据名称组织站点查询
     * @param name
     * @param orgId
     * @param siteId
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/findByName", method = RequestMethod.GET)
    HeadArchivesTypeVo findByName(@RequestParam("name") String name, @RequestParam("orgId") String orgId,@RequestParam("siteId") String siteId){
        return headArchivesTypeService.findByName(name ,orgId,siteId);
    }

    /**
     * 批量保存档案分类
     * @param initEamSet
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/saveBatchType", method = RequestMethod.POST)
    List<HeadArchivesTypeVo> saveBatchType(@RequestBody InitEamSet initEamSet) throws Exception {
        return headArchivesTypeService.saveBatchType(initEamSet.getHeadArchivesTypeVos());
    }

}
