package com.enerbos.cloud.eam.openservice.service.impl;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.enerbos.cloud.eam.openservice.service.PushService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/26
 * @Description App消息推送
 */
@Service
public class PushServiceImpl implements PushService {

    private static final Logger logger = LoggerFactory.getLogger(PushServiceImpl.class);

    @Value("${jpush.masterSecret}")
    private String masterSecret;

    @Value("${jpush.appKey}")
    private String appKey;

    @Value("${jpush.maxRetryTimes}")
    private Integer maxRetryTimes;

    @Value("${jpush.pushSwitch}")
    private String pushSwitch;

    @Value("${jpush.pushProduction}")
    private boolean pushProduction;

    @Override
    public void sendPush(String[] targets, String title, String content, Map<String, String> extras,String buildUpContent) {

        logger.info("-----------APP信息推送-----------------");
        logger.info("工单类型：{},人员ID(targets):{},title:{},content:{},extras：{},buildUpContent:{}",
                   extras.get("notificationType"), Arrays.toString(targets).toString(),title,content,extras,buildUpContent);

        if(pushSwitch != null && pushSwitch.equals("open")) {
            JPushClient jpushClient = new JPushClient(masterSecret, appKey, maxRetryTimes);
            try {
                if(buildUpContent.length() < 58){
                    buildUpContent = buildUpContent+",请及时处理";
                }else{
                    buildUpContent = buildUpContent+"...,请及时处理";
                }
                PushPayload payload = PushPayload.newBuilder()
                        .setPlatform(Platform.all())
                        .setAudience(Audience.alias(targets))
                        .setNotification(Notification.newBuilder()
                                .addPlatformNotification(AndroidNotification.newBuilder().setTitle(title).setAlert(content).addExtras(extras).build())
                                .addPlatformNotification(IosNotification.newBuilder().setAlert(title).addExtras(extras).build())
                                .build())
                        //给成功绑定设备的手机号，推送短信
                        .setSMS(SMS.newBuilder()
                                .setContent(buildUpContent)
                                .setDelayTime(0)//立即发送短信
                                .build())
                        .build();
                payload.resetOptionsApnsProduction(pushProduction);
                PushResult result = jpushClient.sendPush(payload);
                logger.info("Got result - " + result);

            } catch (APIConnectionException e) {
                // Connection error, should retry later
                logger.error("Connection error, should retry later", e);

            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                logger.error("Should review the error, and fix the request", e);
                logger.info("HTTP Status: " + e.getStatus());
                logger.info("Error Code: " + e.getErrorCode());
                logger.info("Error Message: " + e.getErrorMessage());
            }catch (Exception e){
                logger.info("极光推送失败：", e);
            }
        }else {
            logger.info("极光推送关闭：");
        }
    }

    @Override
    public void sendThroughMsgPush(String[] targets, String title, String content, Map<String, String> extras) {
        if(pushSwitch != null && pushSwitch.equals("open")) {
            JPushClient jpushClient = new JPushClient(masterSecret, appKey, maxRetryTimes);
            try {
                PushPayload payload = buildPushObject_all_all_alert(targets,title,content,extras);
                PushResult result = jpushClient.sendPush(payload);
                logger.info("Got result - " + result);

            } catch (APIConnectionException e) {
                // Connection error, should retry later
                logger.error("Connection error, should retry later", e);

            } catch (APIRequestException e) {
                // Should review the error, and fix the request
                logger.error("Should review the error, and fix the request", e);
                logger.info("HTTP Status: " + e.getStatus());
                logger.info("Error Code: " + e.getErrorCode());
                logger.info("Error Message: " + e.getErrorMessage());
            }
        }
    }

    public static PushPayload buildPushObject_all_all_alert(String[] targets, String title, String content, Map<String, String> extras) {

        logger.info("----------buildPushObject_all_all_alert");
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(targets))
                /**
                 * Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，sdk默认不做任何处理，不会有通知提示。
                 * 建议看文档http://docs.jpush.io/guideline/faq/的 [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                 */
                .setMessage(Message.newBuilder()
                        .setMsgContent(content)
                        .setTitle(title)
                        .setContentType("2")
                        .addExtras(extras).build())
                .build();
        return payload;

    }

}
