package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVoForFilter;
import com.enerbos.cloud.eam.vo.EnergyPriceChangHistoryFilterVo;
import com.enerbos.cloud.eam.vo.EnergyPriceChangHistoryVo;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.Map;


/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/7/8.
 */

@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = AssetEnergyPriceClientFallback.class)
public interface AssetEnergyPriceClient {

    /**
     * 根据过滤条和分页信息获取能源价格列表
     * @return
     */
    @RequestMapping(value = "/eam/micro/energyPrice/getEnergyPriceList",method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<AssetEnergyPriceVo> getEnergyPriceList(@RequestBody AssetEnergyPriceVoForFilter filterVo);

    /**
     * 删除计划
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/energyPrice/deleteEnergyPrice",method = RequestMethod.POST)
    @ResponseBody
    public abstract boolean deleteEnergyPrice(@RequestParam(value ="ids",required= false)  String[] ids) ;

    /**
     * 新建能源价格
     * @param vo 新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/energyPrice/saveEnergyPrice",method = RequestMethod.POST)
    @ResponseBody
    public abstract AssetEnergyPriceVo saveEnergyPrice(@RequestBody @Valid AssetEnergyPriceVo vo) ;

    /**
     * 修改能源价格
     * @param assetEnergyPriceVo 修改的能源价格实体
     * @return 修改后的能源价格实体
     */
    @RequestMapping(value = "/eam/micro/energyPrice/updateEnergyPrice", method = RequestMethod.POST)
    public abstract AssetEnergyPriceVo updateEnergyPrice(@RequestBody @Valid AssetEnergyPriceVo assetEnergyPriceVo);

    /**
     * 查询能源价格详细信息
     * @param id 能源价格id
     * @return 返回能源价格实体
     */
    @RequestMapping(value = "/eam/micro/energyPrice/findEnergyPriceDetail", method = RequestMethod.GET)
    public abstract AssetEnergyPriceVo findEnergyPriceDetail(@RequestParam("id") String id) ;

    /**
     * 修改能源价格状态
     * @param paramsMap
     * @return 修改后返回的实体类
     */
    @RequestMapping(value = "/eam/micro/energyPrice/updateEnergyPriceStatus", method = RequestMethod.POST)
    public abstract boolean updateEnergyPriceStatus(@RequestBody Map<String, Object> paramsMap);

    /**
     * 能源价格变更历史分页查询
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/energyPriceChangHistory/getPageList",method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<EnergyPriceChangHistoryVo> getEnergyPriceChangHistoryPageList(
            @RequestBody EnergyPriceChangHistoryFilterVo vo);


    /**
     * 能源价格变更历史保存
     * @param vo
     * @return
     */
    @RequestMapping(value = "/eam/micro/energyPriceChangHistory/save",method = RequestMethod.POST)
    @ResponseBody
    public abstract EnergyPriceChangHistoryVo saveEnergyPriceChangHistory(@RequestBody  EnergyPriceChangHistoryVo vo) ;


    /**
     * 根据能源id查询最近一次的变更记录
     * @param eneryPriceId
     * @return
     */
    @RequestMapping(value = "/eam/micro/energyPriceChangHistory/findMaxCreateDateById", method = RequestMethod.GET)
    public abstract EnergyPriceChangHistoryVo findMaxCreateDateById(@RequestParam("eneryPriceId") String eneryPriceId) ;

}
@Component
class AssetEnergyPriceClientFallback implements FallbackFactory<AssetEnergyPriceClient> {
    Logger logger = LoggerFactory.getLogger(this.getClass()) ;
    @Override
    public AssetEnergyPriceClient create(Throwable throwable) {

//        logger.error("aaaa-------------------------------------",throwable);
        return new AssetEnergyPriceClient() {
            @Override
            public EnerbosPage<AssetEnergyPriceVo> getEnergyPriceList(@RequestBody AssetEnergyPriceVoForFilter assetEnergyPriceVoForFilter) {
                return null;
            }

            @Override
            public boolean deleteEnergyPrice(@RequestParam(value = "ids", required = false) String[] ids) {
                return false;
            }

            @Override
            public AssetEnergyPriceVo saveEnergyPrice(@RequestBody @Valid AssetEnergyPriceVo vo) {
                return null;
            }

            @Override
            public AssetEnergyPriceVo updateEnergyPrice(@RequestBody @Valid AssetEnergyPriceVo assetEnergyPriceVo) {
                return null;
            }

            @Override
            public AssetEnergyPriceVo findEnergyPriceDetail(@RequestParam("id") String id) {
                return null;
            }

            @Override
            public boolean updateEnergyPriceStatus(@RequestBody Map<String, Object> paramsMap) {
                return false;
            }

            @Override
            public EnerbosPage<EnergyPriceChangHistoryVo> getEnergyPriceChangHistoryPageList(@RequestBody EnergyPriceChangHistoryFilterVo vo) {
                return null;
            }

            @Override
            public EnergyPriceChangHistoryVo saveEnergyPriceChangHistory(@RequestBody @Valid EnergyPriceChangHistoryVo vo) {
                return null;
            }

            @Override
            public EnergyPriceChangHistoryVo findMaxCreateDateById(@RequestParam("eneryPriceId") String eneryPriceId) {
                return null;
            }
        };
    }
}
