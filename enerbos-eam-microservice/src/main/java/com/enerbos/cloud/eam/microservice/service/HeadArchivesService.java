package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.HeadArchives;
import com.enerbos.cloud.eam.vo.HeadArchivesVo;
import com.enerbos.cloud.eam.vo.HeadArchivesVoForFilter;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface HeadArchivesService {

    /**
     * 根据筛选条件和分页信息获取档案管理列表
     * @return
     */
    public PageInfo<HeadArchivesVo> getArchivesList(HeadArchivesVoForFilter headArchivesVoForFilter) ;

    /**
     * 新建档案管理
     * @param headArchives 新建的实体
     * @return 返回添加的实体
     */
    public abstract HeadArchives saveArchives(HeadArchives headArchives);

    /**
     * 修改档案管理
     * @param headArchives 修改的档案管理
     * @return 修改后的档案管理
     */
    public abstract HeadArchives updateArchives(HeadArchives headArchives);

    /**
     * 删除档案管理
     * @param ids 档案管理ID数组
     */
    public abstract void deleteArchives(String ids[]);


    /**
     * 查询档案管理详细信息
     * @param id 档案管理id
     * @return 返回档案管理实体
     */
    public abstract HeadArchives findArchivesDetail(String id);
    /**
     *
     * 批量导入档案
     * @param
     */
    void saveBatchArchives(List<HeadArchivesVo> headArchivesVoList) throws Exception;


    HeadArchivesVo findByArchivesNum(String archivesNum, String orgId, String siteId);
}
