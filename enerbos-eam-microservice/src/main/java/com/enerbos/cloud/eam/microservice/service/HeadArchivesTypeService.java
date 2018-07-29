package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.HeadArchivesType;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVo;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForFilter;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForList;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface HeadArchivesTypeService {

    /**
     * 根据筛选条件和分页信息获取档案类型列表
     *
     * @return
     */
    public PageInfo<HeadArchivesTypeVo> getArchivesTypeList(HeadArchivesTypeVoForFilter headArchivesVoTypeForFilter);

    /**
     * 新建档案类型
     *
     * @param headArchives 新建的实体
     * @return 返回添加的实体
     */
    public abstract HeadArchivesType saveArchivesType(HeadArchivesType headArchives);

    /**
     * 修改档案类型
     *
     * @param headArchives 修改的档案类型
     * @return 修改后的档案类型
     */
    public abstract HeadArchivesType updateArchivesType(HeadArchivesType headArchives);

    /**
     * 删除档案类型
     *
     * @param ids 档案类型ID数组
     */
    public abstract boolean deleteArchivesType(String ids[]);


    /**
     * 查询档案类型详细信息
     *
     * @param id 档案类型id
     * @return 返回档案类型实体
     */
    public abstract HeadArchivesType findArchivesTypeDetail(String id);


    /**
     * 查询所有内容
     *
     * @param siteId 站点
     * @param orgId  组织
     * @return
     */
    List<HeadArchivesTypeVoForList> findArchivesTypeAll(String siteId, String orgId);

    /**
     * 获取tree
     *
     * @param archivesTypeVoForLists
     * @param parentId
     * @return
     */
    List<HeadArchivesTypeVoForList> getArchivesTypeTree(List<HeadArchivesTypeVoForList> archivesTypeVoForLists, String parentId);

    /**
     * 根据名称查询
     * @param name
     * @param orgId
     * @param siteId
     * @return
     */
    HeadArchivesTypeVo findByName(String name, String orgId, String siteId);

    /**
     * 批量保存分类
     * @param headArchivesTypeVos
     * @return
     */
    List<HeadArchivesTypeVo> saveBatchType(List<HeadArchivesTypeVo> headArchivesTypeVos) throws Exception;
}
