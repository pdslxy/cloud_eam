package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.microservice.service.CodeGeneratorService;
import com.enerbos.cloud.eam.vo.CodeGeneratorVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0
 * @date 2017年6月30日
 * @Description 生成编码接口
 */
@RestController
public class CodeGeneratorController {

    private final static Log logger = LogFactory.getLog(CodeGeneratorController.class);

    @Autowired
    private CodeGeneratorService codeGeneratorService;

    /**
     * getCodegenerator:获取编码
     *
     * @param siteId   站点ID
     * @param modelKey 模块ID
     * @return String 编码
     */
    @RequestMapping(value = "/eam/micro/getCodegenerator", method = RequestMethod.GET)
    public String getCodegenerator(@RequestParam("orgId") String orgId,
                                   @RequestParam(value = "siteId",required = false) String siteId,
                                   @RequestParam("modelKey") String modelKey) {
        String result = "";
        try {
            result = codeGeneratorService.getCode(orgId,siteId, modelKey);
        } catch (EnerbosException e) {
            logger.error("-------/eam/micro/getCodegenerator-----", e);
        } catch (Exception e) {
            logger.error("-------/eam/micro/getCodegenerator-----", e);
        }
        return result;
    }


    /**
     * 新增编码规则
     *
     * @param codeGeneratorVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/codeGenerator/add", method = RequestMethod.POST)
    public CodeGeneratorVo add(@RequestBody @Valid CodeGeneratorVo codeGeneratorVo) {
        codeGeneratorVo = codeGeneratorService.save(codeGeneratorVo);
        return codeGeneratorVo;
    }

    /**
     * 批量生成编码规则
     * @param initEamSet
     * @return
     */
    @RequestMapping(value = "/eam/micro/codeGenerator/saveBatchCodeGenertors", method = RequestMethod.POST)
    Boolean saveBatchCodeGenertors(@RequestBody InitEamSet initEamSet) throws Exception {

       codeGeneratorService.saveBatchCodeGenertors(initEamSet.getCodeGeneratorVos());
        return true ;
    }

    /**
     * 查询组织站点内所有 编码
     * @param orgId
     * @param siteId
     * @return
     */
    @RequestMapping(value = "/eam/micro/getCodes", method = RequestMethod.GET)
    List<CodeGeneratorVo> getAllCodes(@RequestParam("orgId") String orgId, @RequestParam("siteId") String siteId) {
      return  codeGeneratorService.getAllCodes(orgId,siteId) ;
    }


}
