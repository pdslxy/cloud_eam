package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.CodeGenerator;
import com.enerbos.cloud.eam.microservice.repository.jpa.CodeGeneratorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.CodeGeneratorDao;
import com.enerbos.cloud.eam.microservice.service.CodeGeneratorService;
import com.enerbos.cloud.eam.vo.CodeGeneratorVo;
import com.enerbos.cloud.util.ReflectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Service
public class CodeGeneratorServiceImpl implements CodeGeneratorService {

	@Autowired
	private CodeGeneratorRepository codeGeneratorRepository;
	@Autowired
	private CodeGeneratorDao codeGeneratorDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public String getCode(String orgId,String siteId,String modelKey) {
		Map<String, Object> params=new HashMap<>();
		params.put("orgId", orgId);
		params.put("siteId", siteId);
		params.put("modelKey", modelKey);
		
		CodeGenerator codeGenerator=codeGeneratorDao.findBySiteIdAndModelKey(params);

        if (codeGenerator == null) {
            throw new EnerbosException("","编码生成规则内容读取失败！");
        }

        String delimiter = codeGenerator.getDelimiter() == null ? "" : codeGenerator.getDelimiter().trim();

        BigDecimal currentCode = new BigDecimal(codeGenerator.getCurrentCode() == null ? "1000" : codeGenerator.getCurrentCode());
        currentCode = currentCode.add(BigDecimal.ONE);

        StringBuilder sb = new StringBuilder();
        sb.append(codeGenerator.getPrifix()).append(delimiter)
                .append(currentCode.longValue());

        codeGenerator.setCurrentCode(String.valueOf(currentCode.longValue()));
        codeGeneratorRepository.save(codeGenerator);

		return sb.toString();
	}

    @Override
    public CodeGeneratorVo save(CodeGeneratorVo codeGeneratorVo) {
        CodeGenerator codeGeneratorEntity = new CodeGenerator();
        BeanUtils.copyProperties(codeGeneratorVo, codeGeneratorEntity);
        codeGeneratorEntity = codeGeneratorRepository.save(codeGeneratorEntity);
        BeanUtils.copyProperties(codeGeneratorEntity, codeGeneratorVo);
        return codeGeneratorVo;
    }

    /**
     * 批量生成编码规则
     *
     * @param codeGeneratorVos
     */
    @Override
    public void saveBatchCodeGenertors(List<CodeGeneratorVo> codeGeneratorVos) throws Exception{
        List<CodeGenerator> codeGenerators = new ArrayList<CodeGenerator>();
        ReflectionUtils.copyProperties(codeGeneratorVos,codeGenerators,new CodeGenerator());
        codeGeneratorRepository.save(codeGenerators);
    }

    /**
     * 查询组织站点内所有 编码
     *
     * @param orgId
     * @param siteId
     * @return
     */
    @Override
    public List<CodeGeneratorVo> getAllCodes(String orgId, String siteId) {
        return codeGeneratorDao.findAllCodes(orgId,siteId);
    }
}