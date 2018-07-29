package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.ams.client.LocationClient;
import com.enerbos.cloud.ams.vo.location.LocationVoForDetail;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.HeadArchivesClient;
import com.enerbos.cloud.eam.client.HeadArchivesLogClient;
import com.enerbos.cloud.eam.client.HeadArchivesTypeClient;
import com.enerbos.cloud.eam.vo.*;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/7/8.
 */

@RestController
@Api(description = "档案管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class HeadArchivesController {
    @Autowired
    public HeadArchivesClient headArchivesClient;

    @Autowired
    public HeadArchivesTypeClient headArchivesTypeClient;

    @Autowired
    public HeadArchivesLogClient headArchivesLogClient;

    @Autowired
    public LocationClient locationClient;

    Logger logger = Logger.getLogger(this.getClass());


    /**
     * 根据过滤条和分页信息获取档案列表
     *
     * @return
     */
    @ApiOperation(value = "分页查询档案管理列表", response = AssetEnergyPriceVo.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "filters", value = "所有查询条件")})
    @RequestMapping(value = "/eam/open/headArchives/getArchivesList", method = RequestMethod.POST)
    public EnerbosMessage getArchivesList(@RequestBody HeadArchivesVoForFilter headArchivesVoForFilter, Principal user) {
        try {
            EnerbosPage<HeadArchivesVo> pageInfo = headArchivesClient.getArchivesList(headArchivesVoForFilter);
            for (int i = 0; i < pageInfo.getList().size(); i++) {

                String type = pageInfo.getList().get(i).getMaterialType();
                if (StringUtils.isNotEmpty(type)) {
                    HeadArchivesTypeVo headArchivesTypeVo = headArchivesTypeClient.findArchivesTypeDetail(type);
                    if (headArchivesTypeVo != null) {
                        pageInfo.getList().get(i).setTypeName(headArchivesTypeVo.getTypeName());
                    }
                }

                String position = pageInfo.getList().get(i).getPosition();
                if (StringUtils.isNotEmpty(position)) {
//                    HeadArchivesTypeVo headArchivesTypeVo = headArchivesTypeClient.findArchivesTypeDetail(type);
                    LocationVoForDetail locationVoForDetail = locationClient.findById(user.getName(), position);
                    if (locationVoForDetail != null) {
                        pageInfo.getList().get(i).setPositionDescription(locationVoForDetail.getDescription());
                        pageInfo.getList().get(i).setPositioncode(locationVoForDetail.getCode());

                    }
                }


            }
            return EnerbosMessage.createSuccessMsg(pageInfo, "档案查询成功", "");
        } catch (Exception e) {
            logger.error("-----getArchivesList ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除档案
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据ID删除档案", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchives/deleteArchives", method = RequestMethod.POST)
    public EnerbosMessage deleteArchives(@ApiParam(value = "需要删除的档案ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = false) String[] ids) {
        try {
            boolean isdeleteok = headArchivesClient.deleteArchives(ids);
            return EnerbosMessage.createSuccessMsg(isdeleteok, "删除档案成功", "");
        } catch (Exception e) {
            logger.error("-----deleteArchives ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());

        }
    }

    /**
     * 新建档案
     *
     * @param headArchivesVo 新建的实体
     * @return 返回添加的实体
     */
    @ApiOperation(value = "新建档案", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchives/saveArchives", method = RequestMethod.POST)
    public EnerbosMessage saveArchives(@ApiParam(value = "档案对象", required = true) @RequestBody @Valid HeadArchivesVo headArchivesVo) {
        try {
            return EnerbosMessage.createSuccessMsg(
                    headArchivesClient.saveArchives(headArchivesVo), "新建档案成功", "");
        } catch (Exception e) {
            logger.error("-----saveArchives ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改档案
     *
     * @param headArchivesVo 修改的档案实体
     * @return 修改后的档案实体
     */
    @RequestMapping(value = "/eam/open/headArchives/updateArchives", method = RequestMethod.POST)
    public EnerbosMessage updateArchives(@ApiParam(value = "档案对象", required = true) @RequestBody @Valid HeadArchivesVo headArchivesVo, Principal user) {
        try {
            HeadArchivesLogVo vo = new HeadArchivesLogVo();
            vo.setPrincipal(user.getName());
            vo.setArchivesId(headArchivesVo.getId());
            vo.setDescription("更改了档案资料！");
            vo.setCreateDate(new Date());
            headArchivesLogClient.saveArchivesLog(vo);
            return EnerbosMessage.createSuccessMsg(headArchivesClient.updateArchives(headArchivesVo), "修改档案成功", "");

        } catch (Exception e) {
            logger.error("-------updateArchives--------,e");
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询档案详细信息
     *
     * @param id 档案id
     * @return 返回档案实体
     */
    @ApiOperation(value = "根据ID查询档案详细信息", response = HeadArchivesVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchives/findArchivesDetail", method = RequestMethod.GET)
    public EnerbosMessage findArchivesDetail(@RequestParam("id") String id, Principal user) {
        try {
            HeadArchivesVo archivesVo = headArchivesClient.findArchivesDetail(id);
            if (archivesVo != null) {

                String position = archivesVo.getPosition();

                //分类
                String type = archivesVo.getMaterialType();
                if (StringUtils.isNotEmpty(type)) {
                    HeadArchivesTypeVo headArchivesTypeVo = headArchivesTypeClient.findArchivesTypeDetail(type);
                    if (headArchivesTypeVo != null) {
                        archivesVo.setTypeName(headArchivesTypeVo.getTypeName());
                    }
                }

                //位置
                if (StringUtils.isNotEmpty(position)) {
                    LocationVoForDetail locationVoForDetail = locationClient.findById(user.getName(), position);
                    if (locationVoForDetail != null) {
                        archivesVo.setPositioncode(locationVoForDetail.getCode());
                        archivesVo.setPositionDescription(locationVoForDetail.getDescription());
                    }
                }
            }
            return EnerbosMessage.createSuccessMsg(archivesVo, "根据Id查询档案成功", "");
        } catch (Exception e) {
            logger.error("-------findArchivesDetail--------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改档案状态
     *
     * @param ids    档案id
     * @param status 状态
     * @return 修改后返回的实体类
     */
    @ApiOperation(value = "更新档案状态", response = Boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchives/updateArchivesStatus", method = RequestMethod.POST)
    public EnerbosMessage updateArchivesStatus(@RequestParam("ids") String[] ids,
                                               @RequestParam("status") String status) {
        try {
            Boolean b = headArchivesClient.updateArchivesStatus(ids, status);
            if (b) {
                return EnerbosMessage.createSuccessMsg(b, "修改档案状态成功", "");
            } else {

                return EnerbosMessage.createSuccessMsg(b, "修改档案状态失败", "");
            }
        } catch (Exception e) {
            logger.error("-----updateArchivesStatus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }


    /**
     * 批量导入档案
     *
     * @param
     */
    @ApiOperation(value = "更新档案状态", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchives/importArchives", method = RequestMethod.POST)
    public void importArchives(@RequestParam("request") HttpServletRequest request) {


    }
}
