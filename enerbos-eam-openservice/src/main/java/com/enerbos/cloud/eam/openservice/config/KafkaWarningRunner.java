package com.enerbos.cloud.eam.openservice.config;

import com.alibaba.fastjson.JSONObject;
import com.enerbos.cloud.ams.client.AssetClient;
import com.enerbos.cloud.ams.client.MeterClient;
import com.enerbos.cloud.ams.client.WarningRuleClient;
import com.enerbos.cloud.ams.vo.asset.AssetVoForDetail;
import com.enerbos.cloud.ams.vo.meter.MeterVoForSave;
import com.enerbos.cloud.ams.vo.warningRule.WarningRuleVoForDetail;
import com.enerbos.cloud.eam.client.CodeGeneratorClient;
import com.enerbos.cloud.eam.client.RepairOrderClient;
import com.enerbos.cloud.eam.client.WarningRepairClient;
import com.enerbos.cloud.eam.contants.Common;
import com.enerbos.cloud.eam.contants.RepairOrderCommon;
import com.enerbos.cloud.eam.vo.RepairOrderFlowVo;
import com.enerbos.cloud.eam.vo.WarningRepairVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 严作宇
 * @version 1.0
 * @date 17/9/28 下午3:00
 * @Description kafka 消息生成维保工单
 */
@Component
@ConfigurationProperties(locations = "classpath:warning-repair-message.yml",prefix="warningRepair")
public class KafkaWarningRunner   {

    private final static Log logger = LogFactory.getLog(KafkaWarningRunner.class);

    private static String  MSG_ID = "msg_out_repair" ; // 消息 id

    private static String BJ = "BJ" ; //报警

    private static  String YJ = "YJ" ; //预警
    private static String BJ_NAME  = "报警" ;// EM_BJSX EM_BJXX 通过缩写 判断报警 发消息用

    private static String YJ_NAME = "预警" ; //EM_YJSX EM_YJXX 通过缩写 判断预警 发消息用

    private Map<String ,String > messageMap  = new HashMap<>();

    /**
     * 报修工单 client
     */
    @Autowired
    private RepairOrderClient repairOrderClient ;

    /**
     * 生成编码 client
     */
    @Autowired
    private CodeGeneratorClient codeGeneratorClient ;

    /**
     * 报警规则 client
     */
    @Autowired
    private WarningRuleClient warningRuleClient  ;

    /**
     * 点 client
     */
    @Autowired
    private MeterClient meterClient ;

    /**
     * 设备 client
     */
    @Autowired
    private AssetClient assetClient ;

    @Autowired
    private WarningRepairClient warningRepairClient ;
    @KafkaListener(topics = "KFTP_CD")
    public void listenRepairMessage(String data) throws  Exception{
        //先解析 msgid 和 msgbody
        JSONObject jsonObject  = JSONObject.parseObject(data) ;
        if(!jsonObject.containsKey("msgid") || !jsonObject.containsKey("msgbody")){
            logger.error("receive error message :"+data);
          //  throw  new EnerbosException(HttpStatus.INTERNAL_SERVER_ERROR.toString() , "消息错误") ;
            return ;
        }
        if(!MSG_ID.equals(jsonObject.get("msgid"))){
            logger.error("receive error message :"+data);
            logger.error("kafka create message is not a repair message");
            return ;
        }
        if(jsonObject.get("msgbody") == null ||"".equals(jsonObject.get("msgbody"))){
            logger.error("receive error message :"+data);
            logger.error("kafka create message : body is not correct");
            return ;
        }
        Object  msgBody =  jsonObject.get("msgbody");
        JSONObject jo = JSONObject.parseObject(msgBody.toString());


        String orgId = jo.get("orgid").toString() ;

        String siteId = jo.get("siteid").toString() ;

        String values =jo.get("value").toString() ;

        String tagName =jo.get("tagname").toString() ;

        String ruleId =jo.get("ruleId").toString();

        String time =  jo.get("time").toString();
        //拆分 tagname  取 类型
        String tagType = tagName.split("_")[1];


        //开始拆分 values 得到 测量值 "value":"10800117C64C9C98_temperature,0,2017-10-09 14:08:23,24.72,0"
        String value = values.split(",")[3];

        MeterVoForSave meter = meterClient.findByRefId(tagName);
        if(meter == null) {
            logger.error("receive error message :"+data);
            logger.error("没有找到相关点");
            return ;
        }
        String meterId  = meter.getId();
        AssetVoForDetail asset = assetClient.findByMeterId(meterId) ;
        if(asset == null ){
            logger.error("receive error message :"+data);
            logger.error("没有找到相关设备");
            return ;
        }
        String assetId = asset.getId() ;
        String assetName = asset.getName() ;
        String locationName = asset.getLocationName() ;

        // 根据 ruleId 获取 报警类型
         WarningRuleVoForDetail warning = warningRuleClient.findWarningRuleByRuleId( ruleId);
        if(warning == null) {
            logger.error("规则 id 不存在");
            logger.error("receive error message :"+data);
            return ;
        }
         String warningType = warning.getAction() ;
         Integer duration = warning.getDuration() ;
         String warningTypeName  ="";
         String level = "" ;
        //判断 1.是否生成过工单 2.生成工单的状态是不是未关闭  1&&2  !不生成工单
        WarningRepairVo warningRepair =   warningRepairClient.findByTagNameAndWaringType(tagName,warningType);
        if(warningRepair !=null){
           String  workOrderId = warningRepair.getWorkOrderId() ;
            RepairOrderFlowVo repairOrderFlowVo = repairOrderClient.findRepairOrderFlowVoById(workOrderId);
            if(repairOrderFlowVo !=null){
                if(!RepairOrderCommon.REPAIR_ORDER_STATUS_CANCEL.equals(repairOrderFlowVo.getWorkOrderStatus()) && !RepairOrderCommon.REPAIR_ORDER_STATUS_CLOSE.equals(repairOrderFlowVo.getWorkOrderStatus())){
                    //工单已经生成过了， 不需要再生成工单了
                    return;
                }
            }
        }

        //生成工单
        RepairOrderFlowVo repairOrderFlowVo = new RepairOrderFlowVo() ;
        //获取编码
        String workOrderNum = codeGeneratorClient.getCodegenerator(orgId,siteId, RepairOrderCommon.REPAIR_ORDER_MODEL_KEY) ;
        repairOrderFlowVo.setOrgId(orgId);
        repairOrderFlowVo.setSiteId(siteId);
        repairOrderFlowVo.setWorkOrderNum(workOrderNum);
        repairOrderFlowVo.setOperatorPersonId(Common.SYSTEM_USER);
        repairOrderFlowVo.setOperatorPerson(Common.SYSTEM_USER_LOGINNAME);
        //系统自动生成
        repairOrderFlowVo.setWorkOrderSource(RepairOrderCommon.SYSTEM_AUTO);
        // 工程类型
        repairOrderFlowVo.setProjectType(RepairOrderCommon.WARNING_PROJECT_TYPE);
        if(warningType.contains(YJ)){
            warningTypeName =  YJ_NAME ;
            level = RepairOrderCommon.LEVEL_MIDDLE ;
        }else if(warningType.contains(BJ)){
            warningTypeName = BJ_NAME ;
            level = RepairOrderCommon.LEVEL_HIGH ;
        }
        //报警预警 描述信息
        String description = createDescription(tagType,warningTypeName,locationName,assetName ,value ,duration) ;
        repairOrderFlowVo.setDescription(description);
        repairOrderFlowVo.setIncidentLevel(level);
        repairOrderFlowVo = repairOrderClient.reportRepairOrder(repairOrderFlowVo);

        String  workOrderId = repairOrderFlowVo.getId() ;
        WarningRepairVo warningRepairVo = new WarningRepairVo() ;
        warningRepairVo.setCreateUser(Common.SYSTEM_USER);
        warningRepairVo.setAssetId(assetId);
        warningRepairVo.setMeterId(meterId);
        warningRepairVo.setTagName(tagName);
        warningRepairVo.setDetecteValue(value);
        warningRepairVo.setWarningType(warningType);
        warningRepairVo.setWorkOrderId(workOrderId);
        warningRepairClient.add(warningRepairVo);
    }

    //替换 返回消息变量
    String createDescription(String tagType ,String  waningTypeName , String location ,String assetName , String value ,Integer duration) {
        String message = messageMap.get(tagType) ;
        return   String.format(message, waningTypeName,location,assetName,value,duration) ;
    }


    public Map<String, String> getMessageMap() {
        return messageMap;
    }

    public void setMessageMap(Map<String, String> messageMap) {
        this.messageMap = messageMap;
    }
}
