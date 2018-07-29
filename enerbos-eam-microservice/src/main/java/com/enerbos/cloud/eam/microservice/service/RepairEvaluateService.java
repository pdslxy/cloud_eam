package com.enerbos.cloud.eam.microservice.service;


import com.enerbos.cloud.eam.microservice.domain.RepairEvaluate;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/9/19.
 */

public interface RepairEvaluateService {

    /**
     * 报修评价
     * @param repairEvaluate 新建的实体
     * @return 返回添加的实体
     */
    public abstract RepairEvaluate saveRepairEvaluate(RepairEvaluate repairEvaluate);


    /***
     * 按照报修工单查询评价内容
     * @param repairOrderId
     * @return
     */
    public RepairEvaluate findRepairEvalueByOrderId(String repairOrderId);


    /**
     * 报修小程序首页统计已完成，为分派，处理中，已评价的数据
     * @param map 站点id和人员id
     * @return 查询结果
     */
    public Map<String,Object> findCountRepairAndEvaluate(Map<String,String> map);

}
