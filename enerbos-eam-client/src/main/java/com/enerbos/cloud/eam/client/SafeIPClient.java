package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.SafeIPVo;
import com.enerbos.cloud.eam.vo.SafeIPVoForFilter;
import com.enerbos.cloud.eam.vo.SafeIPVoForList;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/7/14.
 */

@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = SaveIPClientFallback.class)
public interface SafeIPClient {
    /**
     * 根据过滤条和分页信息获取IP列表
     *
     * @return
     */
  //  @RequestMapping("/eam/micro/ip/getIPList")
    @RequestMapping(value = "/eam/micro/ip/getIPList", method = RequestMethod.POST)
    public abstract EnerbosPage<SafeIPVoForList> getipList(@RequestBody SafeIPVoForFilter safeIPVoForFilter);

    /**
     * 删除计划
     *
     * @param ids
     * @return
     */
    @RequestMapping("/eam/micro/ip/deleteIP")
    @ResponseBody
    public abstract boolean deleteIP(@RequestParam(value = "ids", required = false) String[] ids);

    /**
     * 新建IP
     *
     * @param safeIPVo
     *            新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping("/eam/micro/ip/saveIP")
    @ResponseBody
    public abstract SafeIPVo saveIP(@RequestBody @Valid SafeIPVo safeIPVo);
    /**
     * 修改IP
     *
     * @param safeIPVo
     *            修改的IP实体
     * @return 修改后的IP实体
     */
    @RequestMapping(value = "/eam/micro/ip/updateIP", method = RequestMethod.POST)
    public abstract SafeIPVo updateIP(@RequestBody @Valid SafeIPVo safeIPVo);

    /**
     * 查询IP详细信息
     *
     * @param id
     *            IPid
     * @return 返回IP实体
     */
    @RequestMapping(value = "/eam/micro/ip/findIPDetail", method = RequestMethod.GET)
    public abstract SafeIPVo findIPDetail(@RequestParam("id") String id);

    /**
     * 检查IP是否存在
     *
     * @param ip IP的ip
     * @param orgId 组织ID
     * @param siteId 站点ID
     * @param prod 产品
     * @return 是否存在的结果
     */
    @RequestMapping(value = "/eam/micro/ip/checkIp", method = RequestMethod.GET)
    public boolean checkIp(@RequestParam("ip") String ip, @RequestParam("orgId") String orgId, @RequestParam("siteId") String siteId, @RequestParam("prod") String prod);

}

@Component
class SaveIPClientFallback implements FallbackFactory<SafeIPClient> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public SafeIPClient create(Throwable throwable) {


        return new SafeIPClient() {

            @Override
            public EnerbosPage<SafeIPVoForList> getipList(@RequestBody SafeIPVoForFilter safeIPVoForFilter) {
                return null;
            }

            @Override
            public boolean deleteIP(@RequestParam(value = "ids", required = false) String[] ids) {
                return false;
            }

            @Override
            public SafeIPVo saveIP(@RequestBody @Valid SafeIPVo safeIPVo) {
                return null;
            }

            @Override
            public SafeIPVo updateIP(@RequestBody @Valid SafeIPVo safeIPVo) {
                return null;
            }

            @Override
            public SafeIPVo findIPDetail(@RequestParam("id") String id) {
                return null;
            }

            @Override
            public boolean checkIp(@RequestParam("ip") String ip, @RequestParam("orgId") String orgId, @RequestParam("siteId") String siteId, @RequestParam("prod") String prod) {
                return false;
            }
        };
    }
}
