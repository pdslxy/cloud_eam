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
 * @date 2017/3/31 10:20
 * @Description 物资接收单
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = MaterialGoodsReceiveClientFallback.class)
public interface MaterialGoodsReceiveClient {

    /**
     * 按照参数查询物资接收单并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param materialGoodsReceiveVoForFilter 查询条件参数{@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVoForFilter}
     * @return 封装的实体类集合
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/findGoodsReceives", method = RequestMethod.POST)
    public EnerbosPage<MaterialGoodsReceiveVoForList> findGoodsReceives(
            @RequestBody MaterialGoodsReceiveVoForFilter materialGoodsReceiveVoForFilter);

    /**
     * 新建物资接收单
     *
     * @param materialGoodsReceiveVo 新建的实体{@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVo}
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/saveGoodsReceive", method = RequestMethod.POST)
    public MaterialGoodsReceiveVo saveGoodsReceive(
            @RequestBody MaterialGoodsReceiveVo materialGoodsReceiveVo);

    /**
     * 修改物资接收单记录
     *
     * @param materialGoodsReceiveVo 修改物资接收单实体 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveVo}
     * @return 修改后的物资接收单实体
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/updateGoodsReceive", method = RequestMethod.POST)
    public MaterialGoodsReceiveVo updateGoodsReceive(
            @RequestBody MaterialGoodsReceiveVo materialGoodsReceiveVo);

    /**
     * 删除物资
     *
     * @param ids 物资ID数组
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/deleteGoodsReceive", method = RequestMethod.POST)
    public boolean deleteGoodsReceive(@RequestParam("ids") String ids[]);

    /**
     * 查询物资接收单详细信息
     *
     * @param id 物资接收单id
     * @return 返回物资接收单实体
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/findGoodsreceiveById", method = RequestMethod.GET)
    public MaterialGoodsReceiveVo findGoodsreceiveById(
            @RequestParam(value = "id", required = true) String id);


    /**
     * 查询物资接收单明细并按照指定的每页显示条数进行分页 <br>
     * 把分好的数据封装到集合中
     *
     * @param id       物资接收单id
     * @param pageNum  页数
     * @param pageSize 一页显示的行数
     * @return 封装的实体类集合
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/findGoodsReceiveDetailByGoodsReceiveId", method = RequestMethod.GET)
    public EnerbosPage<MaterialGoodsReceiveDetailVoForList> findGoodsReceiveDetailByGoodsReceiveId(
            @RequestParam("id") String id,
            @RequestParam("pageNum") Integer pageNum,
            @RequestParam("pageSize") Integer pageSize);

    /**
     * 新建物资接收单明细
     *
     * @param materialGoodsReceiveDetailVo 新建的实体{@link  com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVo}
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/saveGoodsReceiveDetail", method = RequestMethod.POST)
    public MaterialGoodsReceiveDetailVo saveGoodsReceiveDetail(
            @RequestBody MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo);

    /**
     * 修改物资接收单明细
     *
     * @param materialGoodsReceiveDetailVo 修改的物资接收单明细 {@link com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVo}
     * @return 修改后的物资接收单明细
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/updateGoodsReceiveDetail", method = RequestMethod.POST)
    public MaterialGoodsReceiveDetailVo updateGoodsReceiveDetail(
            @RequestBody MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo);

    /**
     * 删除物资接收单明细
     *
     * @param ids 物资接收单明细ID
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/deleteGoodsReceiveDetail", method = RequestMethod.POST)
    public boolean deleteGoodsReceiveDetail(@RequestParam("ids") String ids[]);

    /**
     * 修改物资接收状态
     *
     * @param ids    物资接收id
     * @param status 状态(已接受，部分接收，草稿，取消)
     * @return 修改是否成功
     */
    @RequestMapping(value = "/eam/micro/goodsreceive/updateGoodsReceiveStatus", method = RequestMethod.POST)
    public boolean updateGoodsReceiveStatus(@RequestParam("ids") String[] ids,
                                            @RequestParam("status") String status);

}

@Component
class MaterialGoodsReceiveClientFallback implements
        FallbackFactory<MaterialGoodsReceiveClient> {


    @Override
    public MaterialGoodsReceiveClient create(Throwable cus) {


        return new MaterialGoodsReceiveClient() {

            @Override
            public EnerbosPage<MaterialGoodsReceiveVoForList> findGoodsReceives(
                    MaterialGoodsReceiveVoForFilter materialGoodsReceiveVoForFilter) {

                return null;
            }

            @Override
            public MaterialGoodsReceiveVo saveGoodsReceive(
                    MaterialGoodsReceiveVo materialGoodsReceiveVo) {

                return null;
            }

            @Override
            public MaterialGoodsReceiveVo updateGoodsReceive(
                    MaterialGoodsReceiveVo materialGoodsReceiveVo) {

                return null;
            }

            @Override
            public boolean deleteGoodsReceive(String[] ids) {

                return false;
            }

            @Override
            public MaterialGoodsReceiveVo findGoodsreceiveById(String id) {

                return null;
            }


            @Override
            public EnerbosPage<MaterialGoodsReceiveDetailVoForList> findGoodsReceiveDetailByGoodsReceiveId(
                    String id, Integer pageNum, Integer pageSize) {

                return null;
            }

            @Override
            public MaterialGoodsReceiveDetailVo saveGoodsReceiveDetail(
                    MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo) {

                return null;
            }

            @Override
            public MaterialGoodsReceiveDetailVo updateGoodsReceiveDetail(
                    MaterialGoodsReceiveDetailVo materialGoodsReceiveDetailVo) {

                return null;
            }

            @Override
            public boolean deleteGoodsReceiveDetail(String[] ids) {
                return false;

            }

            @Override
            public boolean updateGoodsReceiveStatus(String[] ids, String status) {
                return false;
            }

        };
    }

}
