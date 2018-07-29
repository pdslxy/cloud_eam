package com.enerbos.cloud.eam.microservice.controller;

import com.enerbos.cloud.eam.microservice.domain.SafeIP;
import com.enerbos.cloud.eam.microservice.service.SafeIPService;
import com.enerbos.cloud.eam.vo.SafeIPVo;
import com.enerbos.cloud.eam.vo.SafeIPVoForFilter;
import com.enerbos.cloud.eam.vo.SafeIPVoForList;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

/**
 * Created by Enerbos on 2016/10/17.
 */

@RestController
public class SafeIPController {

    private static final Logger logger = LoggerFactory.getLogger(SafeIPController.class);

    @Autowired
    private SafeIPService safeIPService;

    /**
     * 根据过滤条和分页信息获取IP列表
     *
     * @return
     */
    @RequestMapping(value = "/eam/micro/ip/getIPList",method = RequestMethod.POST)
    @ResponseBody
    public PageInfo<SafeIPVoForList> getipList(@RequestBody SafeIPVoForFilter safeIPVoForFilter) {
        logger.info("我请求的IP:");
        PageInfo<SafeIPVoForList> pageInfo = safeIPService
                .getIPList(safeIPVoForFilter);
        return pageInfo;
    }

    /**
     * 删除IP
     *
     * @param ids ip的集合
     * @return 结果
     */
    @RequestMapping("/eam/micro/ip/deleteIP")
    @ResponseBody
    public boolean deleteIP(@RequestParam(value = "ids", required = false) String[] ids) {
        try {
            safeIPService.deleteIP(ids);
        } catch (Exception e) {
            logger.error("-------deleteipById--------------", e);
            return false;
        }
        return true;
    }

    /**
     * 新建IP
     *
     * @param safeIPVo 新建的实体
     * @return 返回添加的实体
     */
    @RequestMapping("/eam/micro/ip/saveIP")
    @ResponseBody
    public SafeIPVo saveip(@RequestBody @Valid SafeIPVo safeIPVo) {
        SafeIP safeIP = new SafeIP();
        safeIP.setProductId(String.join(",", safeIPVo.getProductArray()));

        try {
            ReflectionUtils.copyProperties(safeIPVo, safeIP, null);
        } catch (Exception e) {
            logger.error("-----saveip ------", e);
        }
        safeIP.setCreateDate(new Date());
        safeIP = safeIPService.saveIP(safeIP);
        safeIPVo.setId(safeIP.getId());
        return safeIPVo;

    }

    /**
     * 更新IP的信息
     *
     * @param safeIPVo 修改的IP
     * @return 修改后的IP实体
     */
    @RequestMapping(value = "/eam/micro/ip/updateIP", method = RequestMethod.POST)
    public SafeIP updateIP(@RequestBody @Valid SafeIPVo safeIPVo) {
        SafeIP safeIP = new SafeIP();
        try {
            ReflectionUtils.copyProperties(safeIPVo, safeIP, null);
        } catch (Exception e) {
            logger.error("-----updateip ------", e);
        }
        return safeIPService.updateIP(safeIP);
    }

    /**
     * 查询IP的详细信息
     *
     * @param id IP的id
     * @return 返回IP的实体
     */
    @RequestMapping(value = "/eam/micro/ip/findIPDetail", method = RequestMethod.GET)
    public SafeIP findIPDetail(@RequestParam("id") String id) {
        return safeIPService.findIPetail(id);
    }

    /**
     * 检查IP是否存在
     *
     * @param ip IP的ip
     * @param orgId 组织ID
     * @param siteId 站点ID
     * @param prod 产品
     * @return 是否存在的结果
     */
    @RequestMapping(value = "/eam/micro/ip/checkIp", method = RequestMethod.GET)
    public boolean checkIp(@RequestParam("ip") String ip, @RequestParam("orgId") String orgId, @RequestParam("siteId") String siteId, @RequestParam("prod") String prod) {
        return safeIPService.checkIP(ip,orgId,siteId,prod);
    }


}
