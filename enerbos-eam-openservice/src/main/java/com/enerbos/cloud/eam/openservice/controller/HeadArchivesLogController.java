package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.HeadArchivesLogClient;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVo;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVoForFilter;
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
public class HeadArchivesLogController {
    @Autowired
    public HeadArchivesLogClient headArchivesLogClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 根据过滤条和分页信息获取档案日志列表
     * @return
     */
    @ApiOperation(value = "分页查询档案日志管理列表", response = AssetEnergyPriceVo.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "filters", value = "所有查询条件") })
    @RequestMapping(value = "/eam/open/headArchivesLog/getArchivesLogList",method = RequestMethod.POST)
    public EnerbosMessage getArchivesLogList(@RequestBody HeadArchivesLogVoForFilter headArchivesLogVoForFilter){
        try {
            EnerbosPage<HeadArchivesLogVo> pageInfo = headArchivesLogClient.getArchivesLogList(headArchivesLogVoForFilter);
            return EnerbosMessage.createSuccessMsg(pageInfo, "档案日志查询成功", "");
        } catch (Exception e) {
            logger.error("-----getArchivesLogList ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除档案日志
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据ID删除档案日志", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchivesLog/deleteArchivesLog",method = RequestMethod.POST)
    public  EnerbosMessage deleteArchivesLog( @ApiParam(value = "需要删除的档案日志ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value ="ids",required= false)  String[] ids){
        try {
            boolean isdeleteok = headArchivesLogClient.deleteArchivesLog(ids);
            return EnerbosMessage.createSuccessMsg(isdeleteok, "删除档案日志成功", "");
        } catch (Exception e) {
            logger.error("-----deleteArchivesLog ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
            
        }
    }
    
    /**
     * 新建档案日志
     * @param headArchivesLogVo 新建的实体
     * @return 返回添加的实体
     */
    @ApiOperation(value = "新建档案日志", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchivesLog/saveArchivesLog", method = RequestMethod.POST)
    public  EnerbosMessage saveArchivesLog(@ApiParam(value = "档案日志对象", required = true) @RequestBody @Valid HeadArchivesLogVo headArchivesLogVo){
        try {
            return EnerbosMessage.createSuccessMsg(
                    headArchivesLogClient.saveArchivesLog(headArchivesLogVo), "新建档案日志成功", "");
        } catch (Exception e) {
            logger.error("-----saveArchivesLog ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改档案日志
     * @param headArchivesLogVo 修改的档案日志实体
     * @return 修改后的档案日志实体
     */
    @RequestMapping(value = "/eam/open/headArchivesLog/updateArchivesLog", method = RequestMethod.POST)
    public  EnerbosMessage updateArchivesLog(@ApiParam(value = "档案日志对象", required = true)  @RequestBody @Valid HeadArchivesLogVo headArchivesLogVo){
        try{
            return EnerbosMessage.createSuccessMsg(headArchivesLogClient.updateArchivesLog(headArchivesLogVo),"修改档案日志成功","");
        }catch (Exception e){
            logger.error("-------updateArchivesLog--------,e");
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询档案日志详细信息
     * @param id 档案日志id
     * @return 返回档案日志实体
     */
    @ApiOperation(value = "根据ID查询档案日志详细信息", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", value = "档案日志编码", dataType = "String", required = true) })
    @RequestMapping(value = "/eam/open/headArchivesLog/findArchivesLogDetail", method = RequestMethod.GET)
    public  EnerbosMessage findArchivesLogDetail(@RequestParam("id") String id){
        try{
            return EnerbosMessage.createSuccessMsg(headArchivesLogClient.findArchivesLogDetail(id),"根据Id查询档案日志成功","");
        }catch (Exception e){
            logger.error("-------findArchivesLogDetail--------,e");
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }
    /**
     *
     * 修改档案日志状态
     * @param ArchivesLogId 档案日志id
     * @param status 状态
     * @return 修改后返回的实体类
     */
    @ApiOperation(value = "更新档案日志状态", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/headArchivesLog/updateArchivesLogStatus", method = RequestMethod.POST)
    public  EnerbosMessage updateArchivesLogStatus(@RequestParam("energyPriceId") String ArchivesLogId,
                                                   @RequestParam("status") String status){
        try {
            HeadArchivesLogVo headArchivesLogVo = headArchivesLogClient.updateArchivesLogStatus(ArchivesLogId,status);
            return EnerbosMessage.createSuccessMsg(headArchivesLogVo, "修改档案日志状态成功", "");
        } catch (Exception e) {
            logger.error("-----updateArchivesLogStatus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }
}
