package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.HeadArchivesLog;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVo;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVoForFilter;
import com.github.pagehelper.PageInfo;

/**
 * Created by Enerbos on 2016/10/19.
 */
public interface HeadArchivesLogService {

    /**
     * 根据筛选条件和分页信息获取档案日志列表
     * @return
     */
    public PageInfo<HeadArchivesLogVo> getArchivesLogList(HeadArchivesLogVoForFilter headArchivesVoLogForFilter) ;

    /**
     * 新建档案日志
     * @param headArchives 新建的实体
     * @return 返回添加的实体
     */
    public abstract HeadArchivesLog saveArchivesLog(HeadArchivesLog headArchives);

    /**
     * 修改档案日志
     * @param headArchives 修改的档案日志
     * @return 修改后的档案日志
     */
    public abstract HeadArchivesLog updateArchivesLog(HeadArchivesLog headArchives);

    /**
     * 删除档案日志
     * @param ids 档案日志ID数组
     */
    public abstract void deleteArchivesLog(String ids[]);


    /**
     * 查询档案日志详细信息
     * @param id 档案日志id
     * @return 返回档案日志实体
     */
    public abstract HeadArchivesLog findArchivesLogDetail(String id);


}
