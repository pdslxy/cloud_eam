package com.enerbos.cloud.eam.microservice.service;

import com.enerbos.cloud.eam.microservice.domain.SafeIP;
import com.enerbos.cloud.eam.vo.SafeIPVoForFilter;
import com.enerbos.cloud.eam.vo.SafeIPVoForList;
import com.github.pagehelper.PageInfo;

/**
 * Created by Enerbos on 2016/10/17.
 */
public interface SafeIPService {

    /**
     * 根据筛选条件和分页信息获取IP列表
     * @return
     */
    public PageInfo<SafeIPVoForList> getIPList(SafeIPVoForFilter SafeIPVoForFilter) ;

    /**
     * 新建IP
     * @param SafeIP 新建的实体
     * @return 返回添加的实体
     */
    public abstract SafeIP saveIP(SafeIP SafeIP);

    /**
     * 修改IP
     * @param SafeIP 修改的IP
     * @return 修改后的IP
     */
    public abstract SafeIP updateIP(SafeIP SafeIP);

    /**
     * 删除IP
     * @param ids IPID数组
     */
    public abstract void deleteIP(String ids[]);

    /**
     * 获取某一个ip详情
     * @param id
     * @return
     */

    public SafeIP findIPetail(String id);
    

    /**
     * 检查IP是否存在
     */
    public boolean checkIP(String ip, String orgId, String siteId, String prod);
}
