package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.Contract;
import com.enerbos.cloud.eam.vo.ContractForFilterVo;
import com.enerbos.cloud.eam.vo.ContractForSaveVo;
import com.enerbos.cloud.eam.vo.ContractVo;
import com.github.pagehelper.PageInfo;

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
public interface ContractService {
    /**
     * 根据筛选条件和分页信息获取合同列表
     *
     * @return
     */
    PageInfo<ContractVo> findContractList(ContractForFilterVo contractVoForFilter) throws Exception;

    /**
     * 根据id数组删除合同
     *
     * @param ids
     */
    void deleteContractByIds(String[] ids,String userId) throws EnerbosException;

    /**
     * 保存合同和关联的巡检项信息
     *
     * @param contractVo
     */
    ContractVo saveOrUpdate(ContractForSaveVo contractVo) throws EnerbosException;

    /**
     * 根据id查询合同
     *
     * @param contractId
     */
    Contract findContractById(String contractId) throws EnerbosException;

    /**
     * 保存方法
     *
     * @param contract
     * @throws EnerbosException
     */
    void save(Contract contract) throws EnerbosException;
}
