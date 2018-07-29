package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.Contract;
import com.enerbos.cloud.eam.microservice.repository.jpa.ContractRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.ContractDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.PatrolPlanDao;
import com.enerbos.cloud.eam.microservice.service.ConstructionService;
import com.enerbos.cloud.eam.microservice.service.ContractService;
import com.enerbos.cloud.eam.microservice.service.PatrolPointService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.ContractForFilterVo;
import com.enerbos.cloud.eam.vo.ContractForSaveVo;
import com.enerbos.cloud.eam.vo.ContractVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2017-2018
 * Company       北京翼虎能源科技有限公司
 *
 * @author 李晓阳
 * @version 1.0
 * @date 2017/9/4
 * @Description
 */
@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private ContractDao contractDao;


    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<ContractVo> findContractList(ContractForFilterVo contractVoForFilter) throws Exception {
        PageHelper.startPage(contractVoForFilter.getPageNum(),
                contractVoForFilter.getPageSize());

        String word = contractVoForFilter.getWords();
        Map<String, Object> filters = new HashMap<>();
        filters = EamCommonUtil.reModelToMap(contractVoForFilter);
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        return new PageInfo<ContractVo>(contractDao.findContractListByFilters(filters));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteContractByIds(String[] ids,String userId) throws EnerbosException {
        for (String id : ids) {
            Contract contract = contractRepository.findOne(id);
            if (StringUtils.hasLength(contract.getProcessInstanceId())) throw new RuntimeException("所选合同已经进入流程，不能删除");
            if (!contract.getCreateUser().equals(userId)) throw new RuntimeException(contract.getContractNum()+"的创建人不是当前登录用户");
            contractRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public ContractVo saveOrUpdate(ContractForSaveVo contractForSaveVo) throws EnerbosException {
        //保存或更新巡检点的信息
        Contract pr = new Contract();
        BeanUtils.copyProperties(contractForSaveVo, pr);
        Contract c = contractRepository.save(pr);
        ContractVo contractVo = new ContractVo();
        BeanUtils.copyProperties(c,contractVo);
        return contractVo;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Contract findContractById(String contractId) throws EnerbosException {
        Contract contract = contractRepository.findOne(contractId);
        return contract;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void save(Contract contract) throws EnerbosException {
        contractRepository.save(contract);
    }


}
