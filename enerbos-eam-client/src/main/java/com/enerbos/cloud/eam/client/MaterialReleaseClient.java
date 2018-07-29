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
 * @date 2017年4月6日 上午11:33:29
 * @Description 物资发放服务接口
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = FallBackMaterialReleaseClient.class)
public interface MaterialReleaseClient {

    /**
     * 按照参数查询物资方法并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialReleaseVoForFilter 物资发放列表实体
     *                                   {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseVoForFilter}
     * @return 返回列表结果集
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialRelease", method = RequestMethod.POST)
    EnerbosPage<MaterialReleaseVoForList> findMaterialRelease(
            @RequestBody MaterialReleaseVoForFilter materialReleaseVoForFilter);

    /**
     * 新建物资发放单
     *
     * @param materialReleaseVo 物资发放列表实体
     *                          {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseVo}
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/release/saveMaterialRelease", method = RequestMethod.POST)
    MaterialReleaseVo saveMaterialRelease(
            @RequestBody MaterialReleaseVo materialReleaseVo);

    /**
     * 根据ID删除物资发放
     *
     * @param ids 物资发放id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     * @return 删除是否成功
     */
    @RequestMapping(value = "/eam/micro/release/deleteMaterialRelease", method = RequestMethod.POST)
    boolean deleteMaterialRelease(@RequestParam("ids") String[] ids);

    /**
     * 查询物资发放详细信息
     *
     * @param id 物资发放id
     * @return 返回物资发放实体
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialReleaseById", method = RequestMethod.GET)
    MaterialReleaseVo findMaterialReleaseById(@RequestParam("id") String id);

    /**
     * 查询物资发放明细列表
     *
     * @param materialReleaseDetailVoForFilter 查询实体
     *                                         {@link com.enerbos.cloud.eam.vo.MaterialReleaseDetailVoForFilter }
     * @return 返回物资发放列表实体数据
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialReleaseDetail", method = RequestMethod.POST)
    EnerbosPage<MaterialReleaseDetailVoForList> findMaterialReleaseDetail(
            @RequestBody MaterialReleaseDetailVoForFilter materialReleaseDetailVoForFilter);

    /**
     * 新建物资发放明细单
     *
     * @param materialReleaseDetailVo 物资发放列表实体
     *                                {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseDetailVo}
     * @return EnerbosMessage 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/release/saveMaterialReleaseDetail", method = RequestMethod.POST)
    MaterialReleaseDetailVo saveMaterialReleaseDetail(
            @RequestBody MaterialReleaseDetailVo materialReleaseDetailVo);

    /**
     * 根据ID删除物资发放明细
     *
     * @param ids 物资发放明细id数组，前台传值用逗号分隔 例如：12243243423234,43aelfaoenflaen23432
     * @return EnerbosMessage 返回执行码及数据
     */
    @RequestMapping(value = "/eam/micro/release/deleteMaterialReleaseDetail", method = RequestMethod.POST)
    boolean deleteMaterialReleaseDetail(@RequestParam("ids") String[] ids);

    /**
     * 修改物资发放明细单
     *
     * @param materialReleaseVo 物资发放列表实体
     *                          {@linkplain com.enerbos.cloud.eam.vo.MaterialReleaseDetailVo}
     * @return 返回修改后的物资方法实体
     */
    @RequestMapping(value = "/eam/micro/release/updateMaterialRelease", method = RequestMethod.POST)
    MaterialReleaseVo updateMaterialRelease(@RequestBody MaterialReleaseVo materialReleaseVo);


    /**
     * 根据id查询物资发放单明细
     *
     * @param id 物资发放明细id
     * @return 物资发放明细
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialReleaseDetailById", method = RequestMethod.POST)
    MaterialReleaseDetailVo findMaterialReleaseDetailById(@RequestParam("id") String id);

    /**
     * 根据工单id查询物资发放情况
     *
     * @param id 工单id
     * @return
     */
    @RequestMapping(value = "/eam/micro/release/findMaterialReleaseDetailById", method = RequestMethod.GET)
    EnerbosPage<MaterialInventoryVoForReleaseList> findItemInReleaseByorderId(@RequestParam("id") String id, @RequestParam("pageSize") Integer pageSize, @RequestParam("pageNum") Integer pageNum);

    /**
     * 更具id改变状态
     *
     * @param ids
     * @param status
     * @return
     */
    @RequestMapping(value = "/eam/micro/release/updateMaterialReleaseStatus", method = RequestMethod.POST)
    String updateMaterialReleaseStatus(@RequestParam("ids") String[] ids, @RequestParam("status") String status);
}

@Component
class FallBackMaterialReleaseClient implements
        FallbackFactory<MaterialReleaseClient> {

    @Override
    public MaterialReleaseClient create(Throwable arg0) {
        return new MaterialReleaseClient() {


            @Override
            public EnerbosPage<MaterialReleaseDetailVoForList> findMaterialReleaseDetail(
                    MaterialReleaseDetailVoForFilter materialReleaseDetailVoForFilter) {

                return null;
            }

            @Override
            public MaterialReleaseVo findMaterialReleaseById(String id) {

                return null;
            }

            @Override
            public EnerbosPage<MaterialReleaseVoForList> findMaterialRelease(
                    MaterialReleaseVoForFilter materialReleaseVoForFilter) {

                return null;
            }

            @Override
            public MaterialReleaseVo saveMaterialRelease(MaterialReleaseVo materialReleaseVo) {
                return null;
            }

            @Override
            public boolean deleteMaterialRelease(String[] ids) {

                return false;
            }

            @Override
            public MaterialReleaseDetailVo saveMaterialReleaseDetail(
                    MaterialReleaseDetailVo materialReleaseDetailVo) {

                return null;
            }

            @Override
            public boolean deleteMaterialReleaseDetail(String[] ids) {
                return false;
            }

            @Override
            public MaterialReleaseVo updateMaterialRelease(MaterialReleaseVo materialReleaseVo) {
                return null;
            }


            @Override
            public MaterialReleaseDetailVo findMaterialReleaseDetailById(String id) {
                return null;
            }

            @Override
            public EnerbosPage<MaterialInventoryVoForReleaseList> findItemInReleaseByorderId(String id, Integer pageSize, Integer pageNum) {
                return null;
            }

            @Override
            public String updateMaterialReleaseStatus(String[] ids, String status) {
                return null;
            }

        };

    }

}
