package com.enerbos.cloud.eam.openservice.service;

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
public interface PushService {

    /**
     * 给App端推送消息
     *
     * @param targets 目标设备(多个)
     * @param title   标题
     * @param content 内容
     * @param buildUpContent 用于组合的短信内容
     * @throws com.enerbos.cloud.common.EnerbosException
     */
    void sendPush(String[] targets, String title, String content, Map<String, String> extras, String buildUpContent);

    /**
     * 给APP端推送 自定义消息或透传消息，sdk默认不做任何处理，不会有通知提示
     * 消息推送：直接在通知栏显示
     * 透传消息：需要APP程序接收，在应用内显示，不会再通知栏显示
     * @param targets 目标设备或者处理人（多个）
     * @param title 标题
     * @param content 内容
     * @param extras map 参数
     */
    void sendThroughMsgPush(String[] targets, String title, String content, Map<String, String> extras);

}
