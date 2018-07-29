package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.MeterRfCollector;
import com.enerbos.cloud.eam.microservice.service.MeterRfCollectorService;
import com.enerbos.cloud.eam.microservice.service.RepairOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/11/10.
 */
@RestController
public class EnvController {
    private static Logger logger = LoggerFactory.getLogger(EnvController.class);
    
    @Resource
    private RepairOrderService repairOrderService;
    
    @Resource
    private MeterRfCollectorService meterRfCollectorService;

    /**
     * 查询环境监测的报修工单总数
     *
     * @return
     */
    @RequestMapping("/eam/micro/Env/getRepairOrderCount")
    @ResponseBody
    public int getRepairOrderCount(
            @RequestParam("orgId") String orgId,@RequestParam("siteId") String siteId,@RequestParam("orderSource") String orderSource) {
        int count = repairOrderService.getRepairOrderCount(orgId,siteId,orderSource);
        return count;
    }

    /**
     * 添加收藏
     *
     * @param id        收藏id
     * @param eamProdId 产品id
     * @param personId  人员id
     */
    @RequestMapping(value = "/eam/micro/meter/collect", method = RequestMethod.POST)
    public void collect(@RequestParam("id") String id, @RequestParam("eamProdId") String eamProdId, @RequestParam("type") String type, @RequestParam("personId") String personId,@RequestParam("meterId") String meterId) {
        meterRfCollectorService.collect(id, eamProdId, personId,meterId);
    }

    @RequestMapping(value = "/eam/micro/meter/findByPersonId", method = RequestMethod.POST)
    public List<MeterRfCollector> findByPersonId(@RequestParam("personId") String personId) {
        List<MeterRfCollector> meterList=meterRfCollectorService.findByPersonId(personId);
        return meterList;
    }
    


}
