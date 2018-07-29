package com.enerbos.cloud.eam.openservice.controller;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.vo.field.FieldDomainValueVo;
import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.AssetEnergyPriceClient;
import com.enerbos.cloud.eam.vo.*;
import com.enerbos.cloud.uas.client.PersonAndUserClient;
import com.enerbos.cloud.uas.vo.personanduser.PersonAndUserVoForDetail;
import com.enerbos.cloud.util.PrincipalUserUtils;
import io.swagger.annotations.*;
import io.swagger.util.Json;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

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
public class AssetEnergyPriceController {
    @Autowired
    public AssetEnergyPriceClient assetEnergyPriceClient;


    @Autowired
    private PersonAndUserClient personAndUserClient;

    Logger logger = Logger.getLogger(this.getClass());


    /**
     * 根据过滤条和分页信息获取能源价格列表
     * @return
     */
    @ApiOperation(value = "分页查询能源价格管理列表", response = AssetEnergyPriceVo.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "filters", value = "所有查询条件") })
    @RequestMapping(value = "/eam/open/energyPrice/getEnergyPriceList",method = RequestMethod.POST)
    public EnerbosMessage getEnergyPriceList(@RequestBody AssetEnergyPriceVoForFilter assetEnergyPriceVoForFilter){
        try {
            EnerbosPage<AssetEnergyPriceVo> pageInfo = assetEnergyPriceClient.getEnergyPriceList(assetEnergyPriceVoForFilter);
            return EnerbosMessage.createSuccessMsg(pageInfo, "能源价格查询成功", "");
        } catch (Exception e) {
            logger.error("-----findItems ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除能源价格
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据ID删除能源价格", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/energyPrice/deleteEnergyPrice",method = RequestMethod.POST)
    public  EnerbosMessage deleteEnergyPrice( @ApiParam(value = "需要删除的能源价格ID,支持批量.多个用逗号分隔", required = true) @RequestParam(value ="ids",required= false)  String[] ids){
        try {
            boolean isdeleteok = assetEnergyPriceClient.deleteEnergyPrice(ids);
            return EnerbosMessage.createSuccessMsg(isdeleteok, "删除能源价格成功", "");
        } catch (Exception e) {
            logger.error("-----deleteInventory ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 新建能源价格
     * @param assetEnergyPriceVo 新建的实体
     * @return 返回添加的实体
     */
    @ApiOperation(value = "新建能源价格", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/energyPrice/saveEnergyPrice", method = RequestMethod.POST)
    public  EnerbosMessage saveEnergyPrice(@ApiParam(value = "能源价格对象", required = true) @RequestBody @Valid AssetEnergyPriceVo assetEnergyPriceVo){
        try {
            return EnerbosMessage.createSuccessMsg(
                    assetEnergyPriceClient.saveEnergyPrice(assetEnergyPriceVo), "新建能源价格成功", "");
        } catch (Exception e) {
            logger.error("-----saveEnergyPrice ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 修改能源价格
     * @param assetEnergyPriceVo 修改的能源价格实体
     * @return 修改后的能源价格实体
     */
    @RequestMapping(value = "/eam/open/energyPrice/updateEnergyPrice", method = RequestMethod.POST)
    public  EnerbosMessage updateEnergyPrice(@ApiParam(value = "能源价格对象", required = true)  @RequestBody @Valid AssetEnergyPriceVo assetEnergyPriceVo){
        try{
            return EnerbosMessage.createSuccessMsg(assetEnergyPriceClient.updateEnergyPrice(assetEnergyPriceVo),"修改能源价格成功","");
        }catch (Exception e){
            logger.error("-------updateEnergyPrice--------,e");
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询能源价格详细信息
     * @param id 能源价格id
     * @return 返回能源价格实体
     */
    @ApiOperation(value = "根据ID查询能源价格详细信息", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", value = "能源价格编码", dataType = "String", required = true) })
    @RequestMapping(value = "/eam/open/energyPrice/findEnergyPriceDetail", method = RequestMethod.GET)
    public  EnerbosMessage findEnergyPriceDetail(@RequestParam("id") String id){
        try{
            return EnerbosMessage.createSuccessMsg(assetEnergyPriceClient.findEnergyPriceDetail(id),"根据Id查询能源价格成功","");
        }catch (Exception e){
            logger.error("-------findEnergyPriceDetail--------,e");
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }
    /**
     *
     * 修改能源价格状态
     * @param ids 能源价格id
     * @param status 状态
     * @return 修改后返回的实体类
     */
    @ApiOperation(value = "更新能源价格状态", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/energyPrice/updateEnergyPriceStatus", method = RequestMethod.POST)
    public  EnerbosMessage updateEnergyPriceStatus(@ApiParam(value = "ID列表", required = true) String[] ids,
                                                   @ApiParam(value = "工单状态", required = true) String status,
                                                   @ApiParam(value = "变更描述", required = false)
                                                           String description ){
        try {
            Map<String, Object> paramsMap = new HashMap<>();
            Assert.notEmpty(ids, "请选择要变更状态的工单！");
            Assert.notNull(status, "状态不能为空！");
            paramsMap.put("ids", ids);
            paramsMap.put("status", status);
            Boolean assetEnergyPriceVo = assetEnergyPriceClient.updateEnergyPriceStatus(paramsMap);
            return EnerbosMessage.createSuccessMsg(assetEnergyPriceVo, "修改能源价格状态成功", "");
        } catch (Exception e) {
            logger.error("-----updateEnergyPriceStatus ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }

    }
    //价格变更
    @ApiOperation(value = "价格变更", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/energyPrice/updatePrice", method = RequestMethod.POST)
    public EnerbosMessage findChangPrice(@ApiParam(value = "能源价格变更历史", required = true) @RequestBody EnergyPriceChangHistoryVo historyVo, Principal user) {
        try {

            String personId = PrincipalUserUtils.getPersonId(Json.pretty(user).toString());
            PersonAndUserVoForDetail personVo = personAndUserClient.findByPersonId(personId);
            Assert.notNull(personVo, "账号信息获取失败，请刷新后重试！");
            Assert.notNull(historyVo.getOrgId(),"组织ID不能为空");//组织ID
            Assert.notNull(historyVo.getSiteId(),"站点ID不能为空");//站点ID
            Assert.notNull(historyVo.getEneryPriceId(),"能源价格ID不能为空");              //能源价格ID
            Assert.notNull(historyVo.getChangPriceAfter(),"能源价格不能为空");             //能源价格
                 AssetEnergyPriceVo vo=assetEnergyPriceClient.findEnergyPriceDetail(historyVo.getEneryPriceId());
                 vo.setPrice(historyVo.getChangPriceAfter());
               AssetEnergyPriceVo  vo1=assetEnergyPriceClient.saveEnergyPrice(vo);
               if(vo1!=null){//存储变更记录
                   historyVo.setCreateDate(new Date());
                   historyVo.setCreateUser(personVo.getLoginName());
                   assetEnergyPriceClient.saveEnergyPriceChangHistory(historyVo);
               }
            return EnerbosMessage.createSuccessMsg(vo1, "价格变更成功", "");
        } catch (Exception e) {
            logger.error("-----/eam/air/findPatrolPlans ------", e);
            return EnerbosMessage.createErrorMsg(getErrorStatusCode(e), e.getMessage(), e.getStackTrace().toString());
        }
    }



    //价格变更界面加载
    @ApiOperation(value = "根据ID查询能源价格变更记录", response = AssetEnergyPriceVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({ @ApiImplicitParam(paramType = "query", name = "id", value = "能源价格ID", dataType = "String", required = true) })
    @RequestMapping(value = "/eam/open/energyPrice/findPriceChangeData", method = RequestMethod.GET)
    public  EnerbosMessage findPriceChangeData(@RequestParam("id") String id){
        try{
            //根据ID查询
            AssetEnergyPriceVo vo= assetEnergyPriceClient.findEnergyPriceDetail(id);
            if(vo==null){
                return  EnerbosMessage.createErrorMsg("500","该能源价格ID查询异常",null);
            }
            //查询上次更新时间
            EnergyPriceChangHistoryVo vo1=assetEnergyPriceClient.findMaxCreateDateById(id);


            if(vo1==null){
                vo1=new EnergyPriceChangHistoryVo();
            }
            vo1.setChangPriceBefore(vo.getPrice());
            vo1.setEneryPriceId(vo.getId());
            if(!StringUtils.isEmpty(vo1.getEneryPriceId())){
                AssetEnergyPriceVo vo1l =assetEnergyPriceClient.findEnergyPriceDetail(vo1.getEneryPriceId());
                vo1.setPriceUnit(vo1l.getPriceUnit());
            }

            return EnerbosMessage.createSuccessMsg(vo1,"根据Id查询能源价格成功","");
        }catch (Exception e){
            logger.error("-------/eam/open/energyPrice/findPriceChangeData--------e:",e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }
    //价格变更记录加载
    @ApiOperation(value = "分页查询能源价格变更列表", response = AssetEnergyPriceVo.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
    @RequestMapping(value = "/eam/open/energyPrice/findPriceChangeDataList",method = RequestMethod.POST)
    public EnerbosMessage findPriceChangeDataList(@RequestBody EnergyPriceChangHistoryFilterVo filterVo){
        try {
            EnerbosPage<EnergyPriceChangHistoryVo> pageInfo = assetEnergyPriceClient.getEnergyPriceChangHistoryPageList(filterVo);

            pageInfo.getList().forEach(vo->{
                if(!StringUtils.isEmpty(vo.getEneryPriceId())){
                    AssetEnergyPriceVo vo1 =assetEnergyPriceClient.findEnergyPriceDetail(vo.getEneryPriceId());
                    vo.setPriceUnit(vo1.getPriceUnit());
                }
            });
            return EnerbosMessage.createSuccessMsg(pageInfo, "能源价格查询成功", "");
        } catch (Exception e) {
            logger.error("-----findItems ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    //获取错误码
    private String getErrorStatusCode(Exception e) {
        String message = e.getCause().getMessage().substring(e.getCause().getMessage().indexOf("{"));
        String statusCode = "";
        try {
            JSONObject jsonMessage = JSONObject.parseObject(message);
            if (jsonMessage != null) {
                statusCode = jsonMessage.get("status").toString();
            }
        } catch (Exception jsonException) {
            logger.error("-----getErrorStatusCode----", jsonException);
        }
        return statusCode;
    }
}
