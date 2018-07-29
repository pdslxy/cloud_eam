package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.vo.AssetEnergyPriceVo;
import com.enerbos.cloud.eam.vo.AssetEnergyPriceVoForFilter;
import com.enerbos.cloud.eam.microservice.domain.AssetEnergyPrice;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface AssetEnergyPriceService {

    /**
     * 根据筛选条件和分页信息获取能源价格列表
     * @return
     */
    public PageInfo<AssetEnergyPriceVo> getEnergyPriceList(AssetEnergyPriceVoForFilter assetEnergyPriceVoForFilter) ;

    /**
     * 新建能源价格
     * @param assetEnergyPrice 新建的实体
     * @return 返回添加的实体
     */
    public abstract AssetEnergyPrice saveEnergyPrice(AssetEnergyPrice assetEnergyPrice);

    /**
     * 修改能源价格
     * @param assetEnergyPrice 修改的能源价格
     * @return 修改后的能源价格
     */
    public abstract AssetEnergyPrice updateEnergyPrice(AssetEnergyPrice assetEnergyPrice);

    /**
     * 删除能源价格
     * @param ids 能源价格ID数组
     */
    public abstract void deleteEnergyPrice(String ids[]);


    /**
     * 查询能源价格详细信息
     * @param id 能源价格id
     * @return 返回能源价格实体
     */
    public abstract AssetEnergyPrice findEnergyPriceDetail(String id);



    /**
     * 批量变更状态
     *
     * @param ids    工单ID
     * @param status 指定的状态
     */
    boolean changeOrderStatus(List<String> ids, String status);


}
