package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.Contract;
import com.enerbos.cloud.eam.microservice.service.ContractService;
import com.enerbos.cloud.eam.vo.ContractForFilterVo;
import com.enerbos.cloud.eam.vo.ContractForSaveVo;
import com.enerbos.cloud.eam.vo.ContractForWorkFlowVo;
import com.enerbos.cloud.eam.vo.ContractVo;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RestController
public class ContractController {
    private static Logger logger = LoggerFactory.getLogger(ContractController.class);

    @Autowired
    private ContractService contractService;

    /**
     * 查询合同列表
     *
     * @param contractVoForFilter
     * @return
     */
    @RequestMapping(value = "/eam/micro/contract/findPage", method = RequestMethod.POST)
    public PageInfo<ContractVo> findPage(@RequestBody ContractForFilterVo contractVoForFilter) {
        PageInfo<ContractVo> pageInfo = null;
        try {
            pageInfo = contractService.findContractList(contractVoForFilter);
        } catch (Exception e) {
            logger.debug("-------/contract/findPage--------------", e);
        }
        return pageInfo;
    }

    /**
     * 删除合同
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/contract/deleteByIds")
    public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids, @RequestParam(value = "userId", required = true) String userId) {
        try {
            contractService.deleteContractByIds(ids, userId);
            return "success";
        } catch (Exception e) {
            logger.debug("-------/contract/deleteByIds--------------", e);
            return e.getMessage();
        }
    }


    /**
     * 保存合同
     *
     * @param contractForSaveVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/contract/saveOrUpdate", method = RequestMethod.POST)
    public ContractVo saveOrUpdate(@RequestBody ContractForSaveVo contractForSaveVo) {
        try {
            return contractService.saveOrUpdate(contractForSaveVo);
        } catch (Exception e) {
            logger.debug("-------/contract/saveOrUpdate--------------", e);
        }
        return null;
    }

    /**
     * findContractVoById:根据ID查询合同
     *
     * @return EnerbosMessage返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/contract/findContractVoById", method = RequestMethod.GET)
    public ContractVo findContractVoById(@RequestParam(value = "id", required = true) String id) {
        Contract contract = null;
        ContractVo contractVo = new ContractVo();
        try {
            contract = contractService.findContractById(id);
            if (null != contract) {
                BeanUtils.copyProperties(contract, contractVo);
            }
        } catch (Exception e) {
            logger.error("-----/eam/open/contract/findContractVoById ------", e);
        }
        return contractVo;
    }

    /**
     * findContractCommitById:根据ID查询合同-提报
     *
     * @param id
     * @return ContractForCommitVo 合同-提报VO
     */
    @RequestMapping(value = "/eam/micro/contract/findContractWorkFlowById", method = RequestMethod.GET)
    ContractForWorkFlowVo findContractWorkFlowById(@RequestParam("id") String id) {
        ContractForWorkFlowVo contractForWorkFlowVo = new ContractForWorkFlowVo();
        try {
            Contract contract = contractService.findContractById(id);
            if (null == contract || contract.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            ReflectionUtils.copyProperties(contract, contractForWorkFlowVo, null);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/findContractWorkFlowById-----", e);
        }
        return contractForWorkFlowVo;
    }

    /**
     * findContractCommitById:根据ID查询合同-工单提报
     *
     * @param contractForWorkFlowVo {@link com.enerbos.cloud.eam.vo.ContractForWorkFlowVo}
     * @return ContractForCommitVo 合同-工单提报VO
     */
    @RequestMapping(value = "/eam/micro/contract/saveContractFlow", method = RequestMethod.POST)
    ContractForWorkFlowVo saveContract(@RequestBody ContractForWorkFlowVo contractForWorkFlowVo) {
        try {
            Contract contract = contractService.findContractById(contractForWorkFlowVo.getId());
            if (null == contract || contract.getId() == null) {
                throw new EnerbosException("", "工单不存在，先创建！");
            }
            contract.setStatus(contractForWorkFlowVo.getStatus());
            contract.setProcessInstanceId(contractForWorkFlowVo.getProcessInstanceId());
            contractService.save(contract);
        } catch (Exception e) {
            logger.error("-------/eam/micro/workorder/saveContractCheckAccept-----", e);
        }
        return contractForWorkFlowVo;
    }
}
