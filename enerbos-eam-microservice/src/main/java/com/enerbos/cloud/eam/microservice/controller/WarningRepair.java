package com.enerbos.cloud.eam.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.enerbos.cloud.eam.microservice.service.WarningRepairService;
import com.enerbos.cloud.eam.vo.WarningRepairVo;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 严作宇
 * @version 1.0
 * @date 17/10/12 下午4:40
 * @Description
 */
@RestController
public class WarningRepair {

    @Autowired
    private WarningRepairService warningRepairService ;

    /**
     * 根据点位名称和 报警类型查找 关联工单的记录
     * @param tagName
     * @param warningType
     * @return
     */
    @RequestMapping(value = "/eam/micro/warningRepairs/findByTagNameAndWaringType" ,method = RequestMethod.GET)
    public WarningRepairVo findByTagNameAndWaringType(@RequestParam("tagName") String tagName , @RequestParam("warningType") String warningType){
        return warningRepairService.findByTagNameAndWaringType(tagName,warningType);
    }

    /**
     * 添加一个报警工单关联记录
     * @param warningRepairVo
     * @return
     */
    @RequestMapping(value = "/eam/micro/warningRepairs/add" ,method = RequestMethod.POST)
    public WarningRepairVo add(@RequestBody  WarningRepairVo warningRepairVo) throws Exception{
        return warningRepairService.add(warningRepairVo) ;
    }
}
