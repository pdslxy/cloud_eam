package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.Construction;
import com.enerbos.cloud.eam.microservice.domain.ConstructionRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.ConstructionRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.ConstructionRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.ConstructionDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.ConstructionRfCollectorDao;
import com.enerbos.cloud.eam.microservice.service.ConstructionService;
import com.enerbos.cloud.eam.vo.ConstructionForFilterVo;
import com.enerbos.cloud.eam.vo.ConstructionForListVo;
import com.enerbos.cloud.eam.vo.ConstructionRfCollectorVo;
import com.enerbos.cloud.eam.vo.ConstructionVo;
import com.enerbos.cloud.util.ParamConstans;
import com.enerbos.cloud.util.ParamUtils;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * All rights Reserved, Designed By 翼虎能源
 * Copyright:    Copyright(C) 2015-2017
 * Company   北京翼虎能源科技有限公司
 *
 * @author 庞慧东
 * @version 1.0.0
 * @date 2017年9月9日
 * @Description 施工单service
 */
@Service
public class ConstructionServiceImpl implements ConstructionService {

    private Logger logger = LoggerFactory.getLogger(ConstructionServiceImpl.class);

    @Autowired
    private ConstructionDao constructionDao;

    @Autowired
    private ConstructionRepository constructionRepository;

    @Autowired
    private ConstructionRfCollectorRepository constructionRfCollectorRepository;

    @Autowired
    private ConstructionRfCollectorDao constructionRfCollectorDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public ConstructionVo findConstructionByConstructionNum(String constructionNum) {
        return constructionDao.findConstructionByConstructionNum(constructionNum);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Construction save(Construction construction) {
        return constructionRepository.save(construction);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Construction findConstructionByID(String id) {
        return constructionRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteConstructionByIds(List<String> ids) throws EnerbosException {
        for (String id : ids) {
            Construction construction = constructionRepository.findOne(id);
            if (StringUtils.isNotBlank(construction.getProcessInstanceId()))
                throw new RuntimeException("所选施工单已经进入流程，不能删除");
            constructionRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ConstructionForListVo> findPageConstructionList(ConstructionForFilterVo constructionForFilterVo) {
        PageHelper.startPage(constructionForFilterVo.getPageNum(), constructionForFilterVo.getPageSize());
        if (StringUtils.isNotBlank(constructionForFilterVo.getSorts())) {
            String sorts = constructionForFilterVo.getSorts();
            constructionForFilterVo.setSorts(ParamUtils.convertSortParam(new Construction(), sorts).get(ParamConstans.SUCCESS).toString());
        }
        return constructionDao.findPageConstructionList(constructionForFilterVo);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void collectConstruction(List<ConstructionRfCollectorVo> constructionRfCollectorVoList) {
        if (constructionRfCollectorVoList == null || constructionRfCollectorVoList.isEmpty()) {
            return;
        }
        //过滤重复数据
        Map<String, List<ConstructionRfCollectorVo>> map = constructionRfCollectorVoList.stream()
                .filter(vo -> Objects.nonNull(vo.getConstructionId()))
                .filter(vo -> Objects.nonNull(vo.getPersonId()))
                .collect(Collectors.groupingBy(o -> String.format("%s_%s", o.getConstructionId(), o.getPersonId()), Collectors.toList()));

        List<ConstructionRfCollector> list = map.entrySet().stream().map(entry -> entry.getValue().get(0)).map(vo -> new ConstructionRfCollector(vo.getConstructionId(), vo.getPersonId(), vo.getProduct())).collect(Collectors.toList());
        list.stream().filter(o -> 0 == constructionRfCollectorDao.checkIsCollected(o.getConstructionId(), o.getPersonId(), o.getProduct())).forEach(constructionRfCollectorRepository::save);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void cancelCollectConstruction(List<ConstructionRfCollectorVo> constructionRfCollectorVoList) {
        if (constructionRfCollectorVoList == null || constructionRfCollectorVoList.isEmpty()) {
            return;
        }
        Map<String, List<ConstructionRfCollectorVo>> map = constructionRfCollectorVoList.stream().collect(Collectors.groupingBy(ConstructionRfCollectorVo::getPersonId, Collectors.toList()));
        for (Map.Entry<String, List<ConstructionRfCollectorVo>> entry : map.entrySet()) {
            List<ConstructionRfCollector> list = constructionRfCollectorDao.findConstructionRfCollectorByPersonId(entry.getKey());

            if (!ObjectUtils.isEmpty(list)) {
                Map<String, String> collectedIdMap = entry.getValue().stream().collect(Collectors.toMap(ConstructionRfCollectorVo::getConstructionId, ConstructionRfCollectorVo::getPersonId));

                list.stream().filter(o -> collectedIdMap.containsKey(o.getConstructionId())).forEach(o -> constructionRfCollectorRepository.delete(o.getId()));
            }
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public boolean checkIsCollect(String id, String productId, String personId) {
        Integer i = constructionRfCollectorDao.checkIsCollected(id, personId, productId);
        if (i >= 1) return true;
        return false;
    }
}