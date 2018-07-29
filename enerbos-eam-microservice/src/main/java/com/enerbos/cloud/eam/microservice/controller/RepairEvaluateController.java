package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.RepairEvaluate;
import com.enerbos.cloud.eam.microservice.service.RepairEvaluateService;
import com.enerbos.cloud.eam.vo.RepairEvaluateVo;
import com.enerbos.cloud.util.ReflectionUtils;
import org.hibernate.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/19.
 */
@RestController
public class RepairEvaluateController {

    private static final Logger logger = LoggerFactory.getLogger(RepairEvaluateController.class);

    @Autowired
    private RepairEvaluateService repairEvaluateService;


    /**
     * createRepairOrder: 添加评价
     * @param repairEvaluateVo 评价VO
     * @return repairEvaluateVo 评价vo
     */
    @RequestMapping(method = RequestMethod.POST, value = "/eam/micro/saveRepairEvaluate")
    public RepairEvaluateVo saveRepairEvaluate(@RequestBody RepairEvaluateVo repairEvaluateVo) {
        RepairEvaluate repairEvaluate = new RepairEvaluate();
        try {
            ReflectionUtils.copyProperties(repairEvaluateVo, repairEvaluate, null);
        } catch (Exception e) {
            logger.error("-----saveRepairEvaluate ------", e);
        }
        repairEvaluateService.saveRepairEvaluate(repairEvaluate);
        return repairEvaluateVo;
    }

    /***
     * 根据报修工单查询评价
     * @param repairOrderId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/eam/micro/findRepairEvalueByOrderId")
    private RepairEvaluateVo findRepairEvalueByOrderId(@RequestParam("repairOrderId") String repairOrderId){
        RepairEvaluateVo repairEvaluateVo = new RepairEvaluateVo();
        RepairEvaluate repairEvaluate=repairEvaluateService.findRepairEvalueByOrderId(repairOrderId);
        try {
            if(repairEvaluate!=null){
            ReflectionUtils.copyProperties(repairEvaluate, repairEvaluateVo, null);
            }else{
                return  null;
            }
        } catch (Exception e) {
            logger.error("-----saveRepairEvaluate ------", e);
        }
        
        return repairEvaluateVo;
    }
    ///**
    // * 查询某个人关联的项目
    // * @param personSiteVo 查询条件
    // * @return List<OrderPersonVo> 
    // */
    //@RequestMapping(method = RequestMethod.POST, value = "/eam/micro/findPersonSiteListByFilter")
    //public List<PersonSiteVo> findPersonSiteListByFilter(@RequestBody PersonSiteVo personSiteVo) {
    //    //不捕获异常，如有异常直接向外层扔出
    //    List<PersonSiteVo> result = personSiteService.findListByFilter(personSiteVo);
    //    return result;
    //}

}
