package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.ContractForFilterVo;
import com.enerbos.cloud.eam.vo.ContractForSaveVo;
import com.enerbos.cloud.eam.vo.ContractVo;
import com.enerbos.cloud.eam.vo.ContractForWorkFlowVo;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = ContractClientFallback.class)
public interface ContractClient {
    /**
     * 根据过滤条和分页信息获取巡检路线列表
     * @return
     */
    @RequestMapping(value = "/eam/micro/contract/findPage",method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<ContractVo> findContractList(@RequestBody ContractForFilterVo contractVoForFilter);

    /**
     * 删除巡检路线
     * @return
     */
    @RequestMapping(value = "/eam/micro/contract/deleteByIds")
    @ResponseBody
    public abstract String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids,@RequestParam(value = "userId", required = true) String userId);

    /**
     * 新增或更新巡检路线
     * @return
     */
    @RequestMapping(value = "/eam/micro/contract/saveOrUpdate",method = RequestMethod.POST)
    @ResponseBody
    public abstract ContractVo saveOrUpdate(@RequestBody ContractForSaveVo contractForSaveVo);

    @RequestMapping(value = "/eam/micro/contract/findContractVoById", method = RequestMethod.GET)
    ContractVo findContractVoById(@RequestParam(value = "id", required = true)String id);

    @RequestMapping(value = "/eam/micro/contract/findContractWorkFlowById", method = RequestMethod.GET)
    ContractForWorkFlowVo findContractWorkFlowById(@RequestParam("id")String id);


    @RequestMapping(value = "/eam/micro/contract/saveContractFlow", method = RequestMethod.POST)
    ContractForWorkFlowVo saveContractFlow(@RequestBody ContractForWorkFlowVo contractForWorkFlowVo);

}

@Component
class ContractClientFallback implements FallbackFactory<ContractClient> {

    @Override
    public ContractClient create(Throwable throwable) {
        return new ContractClient() {
            @Override
            public EnerbosPage<ContractVo> findContractList(@RequestBody ContractForFilterVo contractVoForFilter) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public String deleteByIds(@RequestParam(value = "ids", required = true) String[] ids, @RequestParam(value = "userId", required = true) String userId) {
                throw new RuntimeException(throwable.getMessage());
            }


            @Override
            public ContractVo saveOrUpdate(@RequestBody ContractForSaveVo contractForSaveVo) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public ContractVo findContractVoById(@RequestParam(value = "id", required = true) String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public ContractForWorkFlowVo findContractWorkFlowById(@RequestParam("id") String id) {
                throw new RuntimeException(throwable.getMessage());
            }

            @Override
            public ContractForWorkFlowVo saveContractFlow(@RequestBody ContractForWorkFlowVo contractForWorkFlowVo) {
                throw new RuntimeException(throwable.getMessage());
            }


        };
    }
}
