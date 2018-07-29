package com.enerbos.cloud.eam.client;

import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.contants.InitEamSet;
import com.enerbos.cloud.eam.vo.HeadArchivesVo;
import com.enerbos.cloud.eam.vo.HeadArchivesVoForFilter;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/8/1.
 */
@FeignClient(name = "enerbos-eam-microservice", fallbackFactory = HeadArchivesClientFallback.class)
public interface HeadArchivesClient {
    /**
     * 根据过滤条和分页信息获取档案管理列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchives/getArchivesList",method = RequestMethod.POST)
    @ResponseBody
    public abstract EnerbosPage<HeadArchivesVo> getArchivesList(
            @RequestBody HeadArchivesVoForFilter headArchivesVoForFilter);

    /**
     * 删除计划
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchives/deleteArchives",method = RequestMethod.POST)
    @ResponseBody
    public abstract boolean deleteArchives(
            @RequestParam(value = "ids", required = false) String[] ids);

    /**
     * 新建档案
     *
     * @param headArchivesVo
     *            新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping(value = "/eam/micro/headArchives/saveArchives",method = RequestMethod.POST)
    @ResponseBody
    public abstract HeadArchivesVo saveArchives(
            @RequestBody @Valid HeadArchivesVo headArchivesVo);

    /**
     * 修改档案
     *
     * @param headArchivesVo
     *            修改的档案实体
     * @return 修改后的档案实体
     */
    @RequestMapping(value = "/eam/micro/headArchives/updateArchives", method = RequestMethod.POST)
    public abstract HeadArchivesVo updateArchives(
            @RequestBody @Valid HeadArchivesVo headArchivesVo);

    /**
     * 查询档案详细信息
     *
     * @param id
     *            档案id
     * @return 返回档案实体
     */
    @RequestMapping(value = "/eam/micro/headArchives/findArchivesDetail", method = RequestMethod.GET)
    public abstract HeadArchivesVo findArchivesDetail(@RequestParam("id") String id);

    /**
     *
     * 修改档案状态
     *
     * @param ids
     *            档案id
     * @param status
     *            状态
     * @return 修改后返回的实体类
     */

    @RequestMapping(value = "/eam/micro/headArchives/updateArchivesStatus", method = RequestMethod.POST)
    public abstract Boolean updateArchivesStatus(
            @RequestParam("ids") String[] ids,
            @RequestParam("status") String status);

    /**
     *
     * 批量导入档案
     * @param
     */
    @RequestMapping(value = "/eam/micro/headArchives/importArchives", method = RequestMethod.POST)
    public void importArchives(@RequestParam("request") HttpServletRequest request);

    /**
     * 批量导入
     * @param initEamSet
     * @return
     */
    @RequestMapping(value = "/eam/micro/headArchives/saveBatchArchives", method = RequestMethod.POST)
    Boolean saveBatchArchives(@RequestBody InitEamSet initEamSet);

    @RequestMapping(value = "/eam/micro/headArchives/findByArchivesNum", method = RequestMethod.GET)
    HeadArchivesVo findByArchivesNum(@RequestParam("archivesNum")  String archivesNum,@RequestParam("orgId")  String orgId,@RequestParam("siteId") String siteId);
}
@Component
class HeadArchivesClientFallback implements FallbackFactory<HeadArchivesClient> {
    Logger logger = LoggerFactory.getLogger(this.getClass()) ;
    @Override
    public HeadArchivesClient create(Throwable throwable) {
        return new HeadArchivesClient() {
            @Override
            public EnerbosPage<HeadArchivesVo> getArchivesList(@RequestBody HeadArchivesVoForFilter headArchivesVoForFilter) {
                return null;
            }

            @Override
            public boolean deleteArchives(@RequestParam(value = "ids", required = false) String[] ids) {
                return false;
            }

            @Override
            public HeadArchivesVo saveArchives(@RequestBody @Valid HeadArchivesVo headArchivesVo) {
                return null;
            }

            @Override
            public HeadArchivesVo updateArchives(@RequestBody @Valid HeadArchivesVo headArchivesVo) {
                return null;
            }

            @Override
            public HeadArchivesVo findArchivesDetail(@RequestParam("id") String id) {
                return null;
            }

            @Override
            public Boolean updateArchivesStatus(String[] ids, String status) {
                return null;
            }

            @Override
            public void importArchives(@RequestParam("request") HttpServletRequest request) {
           
            }

            /**
             * 批量导入
             *
             * @param initEamSet
             * @return
             */
            @Override
            public Boolean saveBatchArchives(@RequestBody InitEamSet initEamSet) {
                return null;
            }

            @Override
            public HeadArchivesVo findByArchivesNum(String archivesNum, String orgId, String siteId) {
                return null;
            }
        };
    }}
