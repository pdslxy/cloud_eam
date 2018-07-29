package com.enerbos.cloud.eam.openservice.controller;

import com.enerbos.cloud.common.EnerbosMessage;
import com.enerbos.cloud.common.EnerbosPage;
import com.enerbos.cloud.eam.client.SafeIPClient;
import com.enerbos.cloud.eam.vo.SafeIPVo;
import com.enerbos.cloud.eam.vo.SafeIPVoForFilter;
import com.enerbos.cloud.eam.vo.SafeIPVoForList;
import com.enerbos.cloud.uas.client.ProductClient;
import com.enerbos.cloud.uas.vo.product.ProductVo;
import io.swagger.annotations.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Company    北京翼虎能源科技有限公司
 *
 * @author dongjingjing
 * @version 1.0
 * @date 2017/7/8.
 */

@RestController
@Api(description = "IP管理(请求Headers中需要包含 Authorization : Bearer 用户Token)")
public class SafeIPController {

    @Autowired
    private SafeIPClient safeIPClient;

    @Autowired
    private ProductClient productClient;

    Logger logger = LoggerFactory.getLogger(this.getClass());


    /**
     * 根据过滤条和分页信息获取IP列表
     *
     * @return
     */
    @ApiOperation(value = "分页查询IP管理列表", response = SafeIPVoForList.class, responseContainer = "List", notes = "数据统一包装在 EnerbosMessage.data->EnerbosPage.list")
  //  @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "filters", value = "所有查询条件")})
    @RequestMapping(value = "/eam/open/ip/getIPList", method = RequestMethod.POST)
    public EnerbosMessage getIPList(@RequestBody SafeIPVoForFilter safeIPVoForFilter) {
        try {
            EnerbosPage<SafeIPVoForList> pageInfo = safeIPClient.getipList(safeIPVoForFilter);
            List<SafeIPVoForList> listSafeIp=new ArrayList<>();
            if(pageInfo!=null) {
                listSafeIp= pageInfo.getList();
            }

            for (SafeIPVoForList safeip : listSafeIp) {

                String product = safeip.getProduct();
                //判断产品是否为空
//                logger.info("产品id:========"+product);
                if (StringUtils.isNotEmpty(product)) {
                   String[] productIds = product.split(",");
                    List<String> productName = new ArrayList<String>();
                    for (int i = 0; i < productIds.length; i++) {
//                        logger.info("产品id:========"+productIds[i]); todo  此处应该根据编码查询产品名称
                        ProductVo productVo = productClient.findById(productIds[i]);
//                        logger.info("请求的产品对象：{}",productVo);
                        if (productVo != null) {
                            productName.add(productVo.getCode());
                        }
                    }

                    safeip.setProduct(StringUtils.join(productName, ","));
                }
            }

            return EnerbosMessage.createSuccessMsg(pageInfo, "IP查询成功", "");
        } catch (Exception e) {
            logger.error("-----findItems ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 删除IP
     *
     * @param ids
     * @return
     */
    @ApiOperation(value = "根据ID删除IP", response = List.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/ip/deleteIP", method = RequestMethod.POST)
    public EnerbosMessage deleteIP(@ApiParam(value = "需要删除的IPID,支持批量.多个用逗号分隔", required = true) @RequestParam("ids") String[] ids) {
        try {
            boolean isdeleteok = safeIPClient.deleteIP(ids);
            return EnerbosMessage.createSuccessMsg(isdeleteok, "删除IP功", "");
        } catch (Exception e) {
            logger.error("-----deleteInventory ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }


    /**
     * 新建IP
     *
     * @param safeIPVo 新建的实体
     * @return 返回添加的实体
     */
    @ApiOperation(value = "新建IP", response = SafeIPVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @RequestMapping(value = "/eam/open/ip/saveIP", method = RequestMethod.POST)
    public EnerbosMessage saveIP(@ApiParam(value = "IP对象", required = true) @RequestBody @Valid SafeIPVo safeIPVo,Principal user) {
        try {
            safeIPVo.setCreator(user.getName());
            return EnerbosMessage.createSuccessMsg(safeIPClient.saveIP(safeIPVo), "新建IP成功", "");

        } catch (Exception e) {
            logger.error("-----saveIP ------", e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 修改IP
     *
     * @param safeIPVo 修改的IP实体
     * @return 修改后的IP实体
     */
    @RequestMapping(value = "/eam/open/ip/updateIP", method = RequestMethod.POST)
    @ApiOperation(value = "更新IP", response = SafeIPVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    public EnerbosMessage updateIP(@ApiParam(value = "IP对象", required = true) @RequestBody @Valid SafeIPVo safeIPVo) {
        try {
            return EnerbosMessage.createSuccessMsg(safeIPClient.updateIP(safeIPVo), "修改IP成功", "");
        } catch (Exception e) {
            logger.error("-------updateIP--------",e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e
                    .getStackTrace().toString());
        }
    }

    /**
     * 查询IP详细信息
     *
     * @param id IPid
     * @return 返回IP实体
     */
    @ApiOperation(value = "根据ID查询IP详细信息", response = SafeIPVo.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "id", value = "IP", dataType = "String", required = true)})
    @RequestMapping(value = "/eam/open/ip/findIPDetail", method = RequestMethod.GET)
    public EnerbosMessage findIPDetail(@ApiParam(value = "IP的ID", required = true)@RequestParam("id") String id) {
        try {
            return EnerbosMessage.createSuccessMsg(safeIPClient.findIPDetail(id), "根据Id查询IP成功", "");
        } catch (Exception e) {
            logger.error("-------findIPDetail--------",e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }

    /**
     * 检查IP是否已经存在
     *
     * @param ip IP
     * @param orgId 组织ID
     * @param siteId 站点ID
     * @param prod 产品
     * @return 返回是否存在
     */
    @ApiOperation(value = "根据IP查询此IP是否已经存在", response = boolean.class, notes = "返回数据统一包装在 EnerbosMessage.data")
    @ApiImplicitParams({@ApiImplicitParam(paramType = "query", name = "ip", value = "IP", dataType = "String", required = true),
                        @ApiImplicitParam(paramType = "query", name = "orgId", value = "组织ID", dataType = "String", required = true),
                        @ApiImplicitParam(paramType = "query", name = "siteId", value = "站点ID", dataType = "String", required = true),
                        @ApiImplicitParam(paramType = "query", name = "prod", value = "产品", dataType = "String", required = true)}
                        )
    @RequestMapping(value = "/eam/open/ip/checkIP", method = RequestMethod.GET)
    public EnerbosMessage checkIP(@ApiParam(value = "IP", required = true)@RequestParam("ip") String ip,
                                    @ApiParam(value = "组织ID", required = true)@RequestParam("orgId") String orgId,
                                    @ApiParam(value = "站点ID", required = true)@RequestParam("siteId") String siteId,
                                    @ApiParam(value = "产品", required = true)@RequestParam("prod") String prod
                                   ) {
        try {
            boolean flag=safeIPClient.checkIp(ip,orgId,siteId,prod);
            if (flag){
                return EnerbosMessage.createSuccessMsg(flag, "IP存在", "");
            }else{
                return EnerbosMessage.createSuccessMsg(flag, "IP不存在", "");
            }
        } catch (Exception e) {
            logger.error("-------findIPDetail--------",e);
            return EnerbosMessage.createErrorMsg("", e.getMessage(), e.getStackTrace().toString());
        }
    }
}
