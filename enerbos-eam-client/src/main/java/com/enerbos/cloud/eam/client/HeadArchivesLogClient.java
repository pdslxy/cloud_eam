package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVo;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVoForFilter;
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
 * @date 2017/8/10.
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = HeadArchivesLogClientFallback.class)
public interface HeadArchivesLogClient {
    /**
     * 根据过滤条和分页信息获取档案日志管理列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesLog/getArchivesLogList",method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<HeadArchivesLogVo> getArchivesLogList(
            @RequestBody HeadArchivesLogVoForFilter headArchivesLogVoForFilter);

    /**
     * 删除档案日志
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchivesLogType/deleteArchivesLogType",method = RequestMethod.POST)
    @ResponseBody
    public abstract boolean deleteArchivesLog(
            @RequestParam(value = "ids", required = false) String[] ids);

    /**
     * 新建档案日志
     *
     * @param headArchivesLogVo
     *            新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/headArchivesLog/saveArchivesLog",method = RequestMethod.POST)
    @ResponseBody
    public abstract HeadArchivesLogVo saveArchivesLog(
            @RequestBody @Valid HeadArchivesLogVo headArchivesLogVo);

    /**
     * 修改档案日志
     *
     * @param headArchivesLogVo
     *            修改的档案日志实体
     * @return 修改后的档案日志实体
     */
    @RequestMapping(value = "/eam/micro/headArchivesLog/updateArchivesLog", method = RequestMethod.POST)
    public abstract HeadArchivesLogVo updateArchivesLog(
            @RequestBody @Valid HeadArchivesLogVo headArchivesLogVo);

    /**
     * 查询档案日志详细信息
     *
     * @param id
     *            档案日志id
     * @return 返回档案日志实体
     */
    @RequestMapping(value = "/eam/micro/headArchivesLog/findArchivesLogDetail", method = RequestMethod.GET)
    public abstract HeadArchivesLogVo findArchivesLogDetail(@RequestParam("id") String id);

    /**
     *
     * 修改档案日志状态
     *
     * @param ArchivesLogId
     *            档案日志id
     * @param status
     *            状态
     * @return 修改后返回的实体类
     */

    @RequestMapping(value = "/eam/micro/headArchivesLog/updateArchivesLogStatus", method = RequestMethod.POST)
    public abstract HeadArchivesLogVo updateArchivesLogStatus(
            @RequestParam("ArchivesLogId") String ArchivesLogId,
            @RequestParam("status") String status);
}
@Component
class HeadArchivesLogClientFallback implements FallbackFactory<HeadArchivesLogClient> {
    Logger logger = LoggerFactory.getLogger(this.getClass()) ;
    @Override
    public HeadArchivesLogClient create(Throwable throwable) {
        return new HeadArchivesLogClient() {
            @Override
            public EnerbosPage<HeadArchivesLogVo> getArchivesLogList(@RequestBody HeadArchivesLogVoForFilter headArchivesLogVoForFilter) {
                return null;
            }

            @Override
            public boolean deleteArchivesLog(@RequestParam(value = "ids", required = false) String[] ids) {
                return false;
            }

            @Override
            public HeadArchivesLogVo saveArchivesLog(@RequestBody @Valid HeadArchivesLogVo headArchivesLogVo) {
                return null;
            }

            @Override
            public HeadArchivesLogVo updateArchivesLog(@RequestBody @Valid HeadArchivesLogVo headArchivesLogVo) {
                return null;
            }

            @Override
            public HeadArchivesLogVo findArchivesLogDetail(@RequestParam("id") String id) {
                return null;
            }

            @Override
            public HeadArchivesLogVo updateArchivesLogStatus(@RequestParam("ArchivesLogId") String ArchivesLogId, @RequestParam("status") String status) {
                return null;
            }
        };
    }}

