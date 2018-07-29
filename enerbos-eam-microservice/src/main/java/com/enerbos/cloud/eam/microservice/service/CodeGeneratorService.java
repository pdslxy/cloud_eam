package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.CodeGeneratorVo;

import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年06月30日
 * @Description 获取编码
 */
public interface CodeGeneratorService {
    /**
     * getCode: 获取编码
     *
     * @param siteId   站点ID
     * @param modelKey 模块编码
     * @return String 返回编码
     */
    String getCode(String orgId,String siteId, String modelKey);

    /**
     * 保存编码规则
     *
     * @param codeGeneratorVo
     * @return
     */
    CodeGeneratorVo save(CodeGeneratorVo codeGeneratorVo);

    /**
     * 批量生成编码规则
     * @param codeGeneratorVos
     */
    void saveBatchCodeGenertors(List<CodeGeneratorVo> codeGeneratorVos) throws Exception;

    /**
     * 查询组织站点内所有 编码
     * @param orgId
     * @param siteId
     * @return
     */
    List<CodeGeneratorVo> getAllCodes(String orgId, String siteId);
}