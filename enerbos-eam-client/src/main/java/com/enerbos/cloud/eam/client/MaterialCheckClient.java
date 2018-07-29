package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.*;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * All rights Reserved, Designed By 翼虎能源
 * <p>
 * Copyright: Copyright(C) 2015-2017
 * <p>
 * Company 北京翼虎能源科技有限公司
 *
 * @author 刘秀朋
 * @version 1.0
 * @date 2017年7月17日 下午1:26:07
 * @Description
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = MaterialCheckClientClientFallback.class)
public interface MaterialCheckClient {

    /**
     * 查询 物资盘点列表
     *
     * @param materialCheckVoForFilter 物资盘点列表实体 {@link com.enerbos.cloud.eam.vo.MaterialCheckVoForFilter}
     * @return 物资盘点列表实体集合
     */
    @RequestMapping(value = "/eam/micro/check/findMaterialChecks", method = RequestMethod.POST)
    public EnerbosPage<MaterialCheckVoForList> findMaterialChecks(
            @RequestBody MaterialCheckVoForFilter materialCheckVoForFilter);

    /**
     * 新建物资盘点
     *
     * @param materialCheckVo 新建物资盘点实体 {@link com.enerbos.cloud.eam.vo.MaterialCheckVo}
     * @return 物资盘点实体
     */
    @RequestMapping(value = "/eam/micro/check/saveMaterialCheck", method = RequestMethod.POST)
    MaterialCheckVo saveMaterialCheck(@RequestBody MaterialCheckVo materialCheckVo);

    /**
     * 修改物资盘点
     *
     * @param materialCheckVo 物资盘点实体{@link com.enerbos.cloud.eam.vo.MaterialCheckVo}
     * @return 修改后的物资盘点实体
     */
    @RequestMapping(value = "/eam/micro/check/updateMaterialCheck", method = RequestMethod.POST)
    MaterialCheckVo updateMaterialCheck(@RequestBody MaterialCheckVo materialCheckVo);

    /**
     * 根据ID删除物资盘点
     *
     * @param ids 物资盘点id数组
     * @return 删除是否成功
     */
    @RequestMapping(value = "/eam/micro/check/deleteMaterialCheck", method = RequestMethod.POST)
    boolean deleteMaterialCheck(@RequestParam("ids") String[] ids);

    /**
     * 修改物资盘点状态
     *
     * @param ids    物资盘点ids
     * @param status 状态（盘点中、待确认、盘点汇报、库存调整、完成、取消、驳回）
     * @return 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/check/updateMaterialCheckStatus", method = RequestMethod.POST)
    boolean updateMaterialCheckStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status);

    /**
     * 根据ID查询物资盘点详细信息
     *
     * @param id 物资盘点id
     * @return 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/check/findMaterialCheckById", method = RequestMethod.GET)
    MaterialCheckVo findMaterialCheckById(@RequestParam("id") String id);

    /**
     * 根据物资盘点id查询盘点明细
     *
     * @param id       物资盘点id
     * @param pageNum  页数
     * @param pageSize 每页显示数据
     * @return 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/check/findMaterialCheckDetail", method = RequestMethod.GET)
    EnerbosPage<MaterialCheckDetailVoForList> findMaterialCheckDetail(@RequestParam("id") String id, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);

    /**
     * 根据ID删除物资盘点明细
     *
     * @param ids 物资盘点明细id数组
     * @return 是否成功
     */
    @RequestMapping(value = "/eam/micro/check/deleteMaterialCheckDetail", method = RequestMethod.POST)
    boolean deleteMaterialCheckDetail(@RequestParam("ids") String[] ids);
    /**
     * 新建物资盘点明细
     *
     * @param materialCheckDetailVo 新建物资盘点明细实体 {@link com.enerbos.cloud.eam.vo.MaterialCheckDetailVo}
     * @return 物资盘点明细实体
     */
    @RequestMapping(value = "/eam/micro/check/saveMaterialCheckDetail", method = RequestMethod.POST)
    MaterialCheckDetailVo saveMaterialCheckDetail(@RequestBody  MaterialCheckDetailVo materialCheckDetailVo);

    /**
     * 根据物资库存id查询 盘点记录
     *
     * @param id       物资库存id
     * @param pageNum  页数
     * @param pageSize 每页显示数
     * @return 查询结果列表
     */
    @RequestMapping(value = "/eam/micro/check/findMaterialCheckByInvtoryId", method = RequestMethod.GET)
    EnerbosPage<MaterialCheckVoForInventoryList> findMaterialCheckByInvtoryId(@RequestParam("id") String id, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize);
}

@Component
class MaterialCheckClientClientFallback implements
        FallbackFactory<MaterialCheckClient> {

    @Override
    public MaterialCheckClient create(Throwable cus) {

        return new MaterialCheckClient() {
            @Override
            public EnerbosPage<MaterialCheckVoForList> findMaterialChecks(MaterialCheckVoForFilter materialCheckVoForFilter) {
                return null;
            }

            @Override
            public MaterialCheckVo saveMaterialCheck(MaterialCheckVo materialCheckVo) {
                return null;
            }

            @Override
            public MaterialCheckVo updateMaterialCheck(MaterialCheckVo materialCheckVo) {
                return null;
            }

            @Override
            public boolean deleteMaterialCheck(String[] ids) {
                return false;
            }

            @Override
            public boolean updateMaterialCheckStatus(String[] ids, String status) {
                return false;
            }

            @Override
            public MaterialCheckVo findMaterialCheckById(String id) {
                return null;
            }

            @Override
            public EnerbosPage<MaterialCheckDetailVoForList> findMaterialCheckDetail(String id, Integer pageNum, Integer pageSize) {
                return null;
            }

            @Override
            public boolean deleteMaterialCheckDetail(String[] ids) {
                return false;
            }

            @Override
            public MaterialCheckDetailVo saveMaterialCheckDetail(MaterialCheckDetailVo materialCheckDetailVo) {
                return null;
            }

            @Override
            public EnerbosPage<MaterialCheckVoForInventoryList> findMaterialCheckByInvtoryId(String id, Integer pageNum, Integer pageSize) {
                return null;
            }

        };
    }

}