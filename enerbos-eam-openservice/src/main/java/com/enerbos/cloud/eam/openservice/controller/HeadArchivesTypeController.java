package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.HeadArchivesTypeClient;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVo;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForFilter;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForList;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
@Api(description = "能源管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class HeadArchivesTypeController {
    @Autowired
    public HeadArchivesTypeClient headArchivesTypeClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 根据过滤条和分页信息获取档案类型列表
     *
     * @return
     */
    @ApiOperation(value = "分页查询档案类型管理列表", response = AssetEnergyPriceVo.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "filters", value = "所有查询条件")})
    @RequestMapping(value = "/eam/open/headArchivesType/getArchivesTypeList", method = RequestMethod.POST)
    public EnerbosMessage getArchivesTypeList(@RequestBody HeadArchivesTypeVoForFilter headArchivesTypeVoForFilter) {
        try {
            EnerbosPage<HeadArchivesTypeVo> pageInfo = headArchivesTypeClient.getArchivesTypeList(headArchivesTypeVoForFilter);
            return EnerbosMessage.createSuccessMsg(pageInfo, "档案类型查询成功", "");
        } catch (Exception e) {
            logger.error("-----getArchivesTypeList ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     *
     * 查询档案类型树
     *
     * @return
     */
    @ApiOperation(value = "查询档案类型树", response = HeadArchivesTypeVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/headArchivesType/getArchivesTypeTree", method = RequestMethod.GET)
    public EnerbosMessage getArchivesTypeTree(@RequestParam(value = "siteId",required = false) String siteId,@RequestParam("orgId") String orgId ) {
        try {
            List<HeadArchivesTypeVoForList> treelist = headArchivesTypeClient.getArchivesTypeTree(siteId, orgId);
            return EnerbosMessage.createSuccessMsg(treelist, "档案类型树查询成功", "");
        } catch (Exception e) {
            logger.error("-----getArchivesTypeTree ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除档案类型
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据ID删除档案类型", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchivesType/deleteArchivesType", method = RequestMethod.POST)
    public EnerbosMessage deleteArchivesType(@ApiParam(value = "需要删除的档案类型ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value = "ids", required = false) String[] ids) {
        try {
            logger.info("-------------{}",ids);
            boolean isdeleteok = headArchivesTypeClient.deleteArchivesType(ids);
           if(isdeleteok){
               return EnerbosMessage.createSuccessMsg(isdeleteok, "删除档案类型成功", "");
           }else{
               return EnerbosMessage.createSuccessMsg(isdeleteok, "不能删除档案类型", "");
           }

        } catch (Exception e) {
            logger.error("-----deleteArchivesType ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());

        }
    }

    /**
     * 新建档案类型
     *
     * @param headArchivesTypeVo 新建的实体
     * @return 返回添加的实体
     */
    @ApiOperation(value = "新建档案类型", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchivesType/saveArchivesType", method = RequestMethod.POST)
    public EnerbosMessage saveArchivesType(@ApiParam(value = "档案类型对象", required = true) @RequestBody @Valid HeadArchivesTypeVo headArchivesTypeVo) {
        logger.info("---------------------------------{}",headArchivesTypeVo);
        try {
            return EnerbosMessage.createSuccessMsg(
                    headArchivesTypeClient.saveArchivesType(headArchivesTypeVo), "新建档案类型成功", "");
        } catch (Exception e) {
            logger.error("-----saveArchivesType ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改档案类型
     *
     * @param headArchivesTypeVo 修改的档案类型实体
     * @return 修改后的档案类型实体
     */
    @RequestMapping(value = "/eam/open/headArchivesType/updateArchivesType", method = RequestMethod.POST)
    public EnerbosMessage updateArchivesType(@ApiParam(value = "档案类型对象", required = true) @RequestBody @Valid HeadArchivesTypeVo headArchivesTypeVo) {
        try {
            return EnerbosMessage.createSuccessMsg(headArchivesTypeClient.updateArchivesType(headArchivesTypeVo), "修改档案类型成功", "");
        } catch (Exception e) {
            logger.error("-------updateArchivesType--------,e");
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询档案类型详细信息
     *
     * @param id 档案类型id
     * @return 返回档案类型实体
     */
    @ApiOperation(value = "根据ID查询档案类型详细信息", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "档案类型编码", dataType = "String", required = true)})
    @RequestMapping(value = "/eam/open/headArchivesType/findArchivesTypeDetail", method = RequestMethod.GET)
    public EnerbosMessage findArchivesTypeDetail(@RequestParam("id") String id) {
        try {
            return EnerbosMessage.createSuccessMsg(headArchivesTypeClient.findArchivesTypeDetail(id), "根据Id查询档案类型成功", "");
        } catch (Exception e) {
            logger.error("-------findArchivesTypeDetail--------,e");
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改档案类型状态
     *
     * @param ArchivesTypeId 档案类型id
     * @param status         状态
     * @return 修改后返回的实体类
     */
    @ApiOperation(value = "更新档案类型状态", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchivesType/updateArchivesTypeStatus", method = RequestMethod.POST)
    public EnerbosMessage updateArchivesTypeStatus(@RequestParam("energyPriceId") String ArchivesTypeId,
                                                   @RequestParam("status") String status) {
        try {
            HeadArchivesTypeVo headArchivesTypeVo = headArchivesTypeClient.updateArchivesTypeStatus(ArchivesTypeId, status);
            return EnerbosMessage.createSuccessMsg(headArchivesTypeVo, "修改档案类型状态成功", "");
        } catch (Exception e) {
            logger.error("-----updateArchivesTypeStatus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }
}
