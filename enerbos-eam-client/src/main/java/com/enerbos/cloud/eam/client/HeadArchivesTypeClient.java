package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVo;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForFilter;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForList;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/8/10.
 */

@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = HeadArchivesTypeClientFallback.class)
public interface HeadArchivesTypeClient {

    /**
     * 根据过滤条和分页信息获取档案类型管理列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/getArchivesTypeList", method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<HeadArchivesTypeVo> getArchivesTypeList(
            @RequestBody HeadArchivesTypeVoForFilter headArchivesTypeVoForFilter);

    /**
     * 删除档案类型
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/deleteArchivesType", method = RequestMethod.POST)
    @ResponseBody
    public abstract boolean deleteArchivesType(
            @RequestParam(value = "ids", required = false) String[] ids);

    /**
     * 新建档案类型
     *
     * @param headArchivesTypeVo 新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/saveArchivesType", method = RequestMethod.POST)
    @ResponseBody
    public abstract HeadArchivesTypeVo saveArchivesType(
            @RequestBody @Valid HeadArchivesTypeVo headArchivesTypeVo);

    /**
     * 修改档案类型
     *
     * @param headArchivesTypeVo 修改的档案类型实体
     * @return 修改后的档案类型实体
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/updateArchivesType", method = RequestMethod.POST)
    public abstract HeadArchivesTypeVo updateArchivesType(
            @RequestBody @Valid HeadArchivesTypeVo headArchivesTypeVo);

    /**
     * 查询档案类型详细信息
     *
     * @param id 档案类型id
     * @return 返回档案类型实体
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/findArchivesTypeDetail", method = RequestMethod.GET)
    public abstract HeadArchivesTypeVo findArchivesTypeDetail(@RequestParam("id") String id);

    /**
     * 修改档案类型状态
     *
     * @param ArchivesTypeId 档案类型id
     * @param status         状态
     * @return 修改后返回的实体类
     */

    @RequestMapping(value = "/eam/micro/headArchivesType/updateArchivesTypeStatus", method = RequestMethod.POST)
    public abstract HeadArchivesTypeVo updateArchivesTypeStatus(
            @RequestParam("ArchivesTypeId") String ArchivesTypeId,
            @RequestParam("status") String status);

    /**
     * 查询档案类型树
     *
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/getArchivesTypeTree", method = RequestMethod.GET)
    List<HeadArchivesTypeVoForList> getArchivesTypeTree(@RequestParam(value = "siteId",required = false) String siteId, @RequestParam("orgId") String orgId);

    /**
     * 根据名称组织站点查询
     * @param name
     * @param orgId
     * @param siteId
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/findByName", method = RequestMethod.GET)
    HeadArchivesTypeVo findByName(@RequestParam("name") String name, @RequestParam("orgId") String orgId,@RequestParam("siteId") String siteId);

    /**
     * 批量保存档案分类
     * @param initEamSet
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesType/saveBatchType", method = RequestMethod.POST)
    List<HeadArchivesTypeVo> saveBatchType(@RequestBody  InitEamSet initEamSet);
}

@Component
class HeadArchivesTypeClientFallback implements FallbackFactory<HeadArchivesTypeClient> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public HeadArchivesTypeClient create(Throwable throwable) {
        return new HeadArchivesTypeClient() {
            @Override
            public EnerbosPage<HeadArchivesTypeVo> getArchivesTypeList(@RequestBody HeadArchivesTypeVoForFilter headArchivesTypeVoForFilter) {
                return null;
            }

            @Override
            public boolean deleteArchivesType(@RequestParam(value = "ids", required = false) String[] ids) {
                return false;
            }

            @Override
            public HeadArchivesTypeVo saveArchivesType(@RequestBody @Valid HeadArchivesTypeVo headArchivesTypeVo) {
                return null;
            }

            @Override
            public HeadArchivesTypeVo updateArchivesType(@RequestBody @Valid HeadArchivesTypeVo headArchivesTypeVo) {
                return null;
            }

            @Override
            public HeadArchivesTypeVo findArchivesTypeDetail(@RequestParam("id") String id) {
                return null;
            }

            @Override
            public HeadArchivesTypeVo updateArchivesTypeStatus(@RequestParam("ArchivesTypeId") String ArchivesTypeId, @RequestParam("status") String status) {
                return null;
            }

            @Override
            public List<HeadArchivesTypeVoForList> getArchivesTypeTree(String siteId, String orgId) {
                return null;
            }

            @Override
            public HeadArchivesTypeVo findByName(String name, String orgId, String siteId) {
                return null;
            }

            @Override
            public List<HeadArchivesTypeVo> saveBatchType(InitEamSet initEamSet) {
                return null;
            }
        };
    }
}
