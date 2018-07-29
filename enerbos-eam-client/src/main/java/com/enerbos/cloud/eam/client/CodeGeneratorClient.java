package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.vo.CodeGeneratorVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
 * @Description 获取编码
 */
@FeignClient(name = "enerbos-eam-microservice", fallback = CodeGeneratorClientFallback.class)
public interface CodeGeneratorClient {
	
	/**
	 * getCodegenerator:获取编码
	 * @param siteId 站点ID
	 * @param modelKey 模块ID
	 * @return String 编码
	 */
	@RequestMapping(value = "/eam/micro/getCodegenerator", method = RequestMethod.GET)
	public String getCodegenerator(@RequestParam("orgId") String orgId,
								   @RequestParam(value = "siteId",required = false) String siteId,@RequestParam("modelKey") String modelKey);

	/**
	 * 新增编码规则
	 *
	 * @param codeGeneratorVo
	 * @return
	 */
	@RequestMapping(value = "/eam/micro/codeGenerator/add", method = RequestMethod.POST)
	CodeGeneratorVo add(@RequestBody @Valid CodeGeneratorVo codeGeneratorVo);

	/**
	 * 批量生成编码规则
	 * @param initEamSet
	 * @return
	 */
	@RequestMapping(value = "/eam/micro/codeGenerator/saveBatchCodeGenertors", method = RequestMethod.POST)
	Boolean saveBatchCodeGenertors(@RequestBody InitEamSet initEamSet);

	@RequestMapping(value = "/eam/micro/getCodes", method = RequestMethod.GET)
	List<CodeGeneratorVo> getCodes(@RequestParam("orgId") String orgId, @RequestParam(value = "siteId") String siteId);
}
@Component
class CodeGeneratorClientFallback implements CodeGeneratorClient{

	@Override
	public String getCodegenerator(String orgId,String siteId, String modelKey) {
		return null;
	}

	@Override
	public CodeGeneratorVo add(CodeGeneratorVo codeGeneratorVo) {
		return null;
	}

	/**
	 * 批量生成编码规则
	 *
	 * @param initEamSet
	 * @return
	 */
	@Override
	public Boolean saveBatchCodeGenertors(@RequestBody InitEamSet initEamSet) {
		return null;
	}

	@Override
	public List<CodeGeneratorVo> getCodes(@RequestParam("orgId") String orgId, @RequestParam(value = "siteId", required = false) String siteId) {
		return null;
	}

}