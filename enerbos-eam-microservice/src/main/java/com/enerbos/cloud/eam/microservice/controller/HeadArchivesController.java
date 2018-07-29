package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.microservice.domain.HeadArchives;
import com.enerbos.cloud.eam.microservice.service.HeadArchivesService;
import com.enerbos.cloud.eam.vo.HeadArchivesVo;
import com.enerbos.cloud.eam.vo.HeadArchivesVoForFilter;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by Enerbos on 2016/10/19.
 */

@RestController
public class HeadArchivesController {

    private static final Logger logger = LoggerFactory
            .getLogger(HeadArchivesController.class);

    @Autowired
    private HeadArchivesService headArchivesService;


    /**
     * 根据过滤条和分页信息获取档案管理列表
     *
     * @return
     */
    @RequestMapping("/eam/micro/headArchives/getArchivesList")
    @ResponseBody
    public PageInfo<HeadArchivesVo> getArchivesList(
            @RequestBody HeadArchivesVoForFilter headArchivesVoForFilter) {
        PageInfo<HeadArchivesVo> pageInfo = headArchivesService.getArchivesList(headArchivesVoForFilter);
        return pageInfo;
    }

    /**
     * 删除档案
     *
     * @param ids
     * @return
     */
    @RequestMapping("/eam/micro/headArchives/deleteArchives")
    @ResponseBody
    public boolean deleteArchives(
            @RequestParam(value = "ids", required = false) String[] ids) {
        try {
            headArchivesService.deleteArchives(ids);
        } catch (Exception e) {
            logger.error("-------deleteArchives--------------", e);
            return false;
        }
        return true;
    }

    /**
     * 新建档案
     *
     * @param headArchivesVo 新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping("/eam/micro/headArchives/saveArchives")
    @ResponseBody
    public HeadArchivesVo saveArchives(
            @RequestBody @Valid HeadArchivesVo headArchivesVo) {
        HeadArchives headArchives = new HeadArchives();
        try {
            ReflectionUtils.copyProperties(headArchivesVo, headArchives, null);
        } catch (Exception e) {
            logger.error("-----saveArchives ------", e);
        }
        headArchives.setCreateDate(new Date());
        headArchives = headArchivesService.saveArchives(headArchives);
        headArchivesVo.setId(headArchives.getId());
        return headArchivesVo;
    }

    /**
     * 修改档案
     *
     * @param headArchivesVo 修改的档案实体
     * @return 修改后的档案实体
     */
    @RequestMapping(value = "/eam/micro/headArchives/updateArchives", method = RequestMethod.POST)
    public HeadArchives updateArchives(
            @RequestBody @Valid HeadArchivesVo headArchivesVo) {
        HeadArchives headArchives = new HeadArchives();
        try {
            ReflectionUtils.copyProperties(headArchivesVo, headArchives, null);
        } catch (Exception e) {
            logger.error("-----updateArchives ------", e);
        }
        return headArchivesService.updateArchives(headArchives);
    }

    /**
     * 查询档案详细信息
     *
     * @param id 档案id
     * @return 返回档案实体
     */
    @RequestMapping(value = "/eam/micro/headArchives/findArchivesDetail", method = RequestMethod.GET)
    public HeadArchives findArchivesDetail(@RequestParam("id") String id) {
        return headArchivesService.findArchivesDetail(id);
    }

    /**
     * 修改档案状态
     *
     * @param ids    档案id
     * @param status 状态
     * @return 修改后返回的实体类
     */

    @RequestMapping(value = "/eam/micro/headArchives/updateArchivesStatus", method = RequestMethod.POST)
    public Boolean updateArchivesStatus(
            @RequestParam("ids") String[] ids,
            @RequestParam("status") String status) {
        try {
            for (String id : ids) {
                HeadArchives headArchives = headArchivesService.findArchivesDetail(id);
                headArchives.setStatus(status);
                headArchivesService.saveArchives(headArchives);
            }
            return true;
        } catch (Exception e) {
            logger.error("-----updateArchivesStatus ------", e);
            return false;
        }
    }

    /**
     * 批量导入档案
     *
     * @param
     */
    @RequestMapping(value = "/eam/micro/headArchives/saveBatchArchives", method = RequestMethod.POST)
    public Boolean  importArchives(@RequestBody InitEamSet initEamSet ) throws Exception {

        headArchivesService.saveBatchArchives(initEamSet.getHeadArchivesVoList()) ;
        return true ;
    }
    @RequestMapping(value = "/eam/micro/headArchives/findByArchivesNum", method = RequestMethod.GET)
    HeadArchivesVo findByArchivesNum(@RequestParam("archivesNum")  String archivesNum,@RequestParam("orgId")  String orgId,@RequestParam("siteId") String siteId){
        return headArchivesService.findByArchivesNum(archivesNum,orgId,siteId);
    }

}
