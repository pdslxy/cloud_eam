package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.DefectOrder;
import com.enerbos.cloud.eam.microservice.domain.DefectOrderRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.DefectOrderRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.DefectOrderRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.DefectOrderDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.DefectOrderRfCollectorDao;
import com.enerbos.cloud.eam.microservice.service.DefectOrderService;
import com.enerbos.cloud.eam.vo.*;
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
 * @date 2017年9月5日
 * @Description 消缺工单service
 */
@Service
public class DefectOrderServiceImpl implements DefectOrderService {

    private Logger logger = LoggerFactory.getLogger(DefectOrderServiceImpl.class);

    @Autowired
    private DefectOrderDao defectOrderDao;
    
    @Autowired
    private DefectOrderRepository defectOrderRepository;

	@Autowired
	private DefectOrderRfCollectorRepository defectOrderRfCollectorRepository;

	@Autowired
	private DefectOrderRfCollectorDao defectOrderRfCollectorDao;

    @Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DefectOrderVo findDefectOrderByDefectOrderNum(String defectOrderNum) {
    	return defectOrderDao.findDefectOrderByDefectOrderNum(defectOrderNum);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DefectOrder save(DefectOrder defectOrder) {
        return defectOrderRepository.save(defectOrder);
    }

    @Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DefectOrder findDefectOrderByID(String id) {
    	return defectOrderRepository.findOne(id);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDefectOrderByIds(List<String> ids) {
    	for (String id : ids) {
    		defectOrderRepository.delete(id);
		}
    }
    
    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDefectOrderById(String id) {
    	defectOrderRepository.delete(id);
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DefectOrderForListVo> findPageDefectOrderList(DefectOrderForFilterVo defectOrderForFilterVo){
		PageHelper.startPage(defectOrderForFilterVo.getPageNum(), defectOrderForFilterVo.getPageSize());
		if (StringUtils.isNotBlank(defectOrderForFilterVo.getSorts())) {
			String sorts = defectOrderForFilterVo.getSorts();
			defectOrderForFilterVo.setSorts(ParamUtils.convertSortParam(new DefectOrder(), sorts).get(ParamConstans.SUCCESS).toString());
		}
		return defectOrderDao.findPageDefectOrderList(defectOrderForFilterVo);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void collectDefectOrder(List<DefectOrderRfCollectorVo> defectOrderRfCollectorVoList) {
		if (defectOrderRfCollectorVoList == null || defectOrderRfCollectorVoList.isEmpty()) {
			return;
		}
		//过滤重复数据
		Map<String, List<DefectOrderRfCollectorVo>> map = defectOrderRfCollectorVoList.stream()
				.filter(vo -> Objects.nonNull(vo.getDefectOrderId()))
				.filter(vo -> Objects.nonNull(vo.getPersonId()))
				.collect(Collectors.groupingBy(o -> String.format("%s_%s", o.getDefectOrderId(), o.getPersonId()), Collectors.toList()));

		List<DefectOrderRfCollector> list = map.entrySet().stream().map(entry -> entry.getValue().get(0)).map(vo -> new DefectOrderRfCollector(vo.getDefectOrderId(), vo.getPersonId(), vo.getProduct())).collect(Collectors.toList());
		list.stream().filter(o -> 0 == defectOrderRfCollectorDao.checkIsCollected(o.getDefectOrderId(), o.getPersonId(), o.getProduct())).forEach(defectOrderRfCollectorRepository::save);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void cancelCollectDefectOrder(List<DefectOrderRfCollectorVo> defectOrderRfCollectorVoList) {
		if (defectOrderRfCollectorVoList == null || defectOrderRfCollectorVoList.isEmpty()) {
			return;
		}
		Map<String, List<DefectOrderRfCollectorVo>> map = defectOrderRfCollectorVoList.stream().collect(Collectors.groupingBy(DefectOrderRfCollectorVo::getPersonId, Collectors.toList()));
		for (Map.Entry<String, List<DefectOrderRfCollectorVo>> entry : map.entrySet()) {
			List<DefectOrderRfCollector> list = defectOrderRfCollectorDao.findDefectOrderRfCollectorByPersonId(entry.getKey());

			if (!ObjectUtils.isEmpty(list)) {
				Map<String, String> collectedIdMap = entry.getValue().stream().collect(Collectors.toMap(DefectOrderRfCollectorVo::getDefectOrderId, DefectOrderRfCollectorVo::getPersonId));

				list.stream().filter(o -> collectedIdMap.containsKey(o.getDefectOrderId())).forEach(o -> defectOrderRfCollectorRepository.delete(o.getId()));
			}
		}
	}
}