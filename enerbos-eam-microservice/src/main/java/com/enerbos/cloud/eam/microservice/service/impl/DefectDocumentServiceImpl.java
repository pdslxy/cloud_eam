package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.DefectDocument;
import com.enerbos.cloud.eam.microservice.domain.DefectDocumentRfCollector;
import com.enerbos.cloud.eam.microservice.repository.jpa.DefectDocumentRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.DefectDocumentRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.DefectDocumentDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.DefectDocumentRfCollectorDao;
import com.enerbos.cloud.eam.microservice.service.DefectDocumentService;
import com.enerbos.cloud.eam.vo.DefectDocumentForFilterVo;
import com.enerbos.cloud.eam.vo.DefectDocumentForListVo;
import com.enerbos.cloud.eam.vo.DefectDocumentRfCollectorVo;
import com.enerbos.cloud.eam.vo.DefectDocumentVo;
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
 * @Description 缺陷单service
 */
@Service
public class DefectDocumentServiceImpl implements DefectDocumentService {

    private Logger logger = LoggerFactory.getLogger(DefectDocumentServiceImpl.class);

    @Autowired
    private DefectDocumentDao defectDocumentDao;
    
    @Autowired
    private DefectDocumentRepository defectDocumentRepository;

	@Autowired
	private DefectDocumentRfCollectorRepository defectDocumentRfCollectorRepository;

	@Autowired
	private DefectDocumentRfCollectorDao defectDocumentRfCollectorDao;

    @Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DefectDocumentVo findDefectDocumentByDefectDocumentNum(String defectDocumentNum) {
    	return defectDocumentDao.findDefectDocumentByDefectDocumentNum(defectDocumentNum);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DefectDocument save(DefectDocument defectDocument) {
        return defectDocumentRepository.save(defectDocument);
    }

    @Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DefectDocument findDefectDocumentByID(String id) {
    	return defectDocumentRepository.findOne(id);
    }

    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDefectDocumentByIds(List<String> ids) {
    	for (String id : ids) {
    		defectDocumentRepository.delete(id);
		}
    }
    
    @Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteDefectDocumentById(String id) {
    	defectDocumentRepository.delete(id);
    }

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<DefectDocumentForListVo> findPageDefectDocumentList(DefectDocumentForFilterVo defectDocumentForFilterVo){
		PageHelper.startPage(defectDocumentForFilterVo.getPageNum(), defectDocumentForFilterVo.getPageSize());
		if (StringUtils.isNotBlank(defectDocumentForFilterVo.getSorts())) {
			String sorts = defectDocumentForFilterVo.getSorts();
			defectDocumentForFilterVo.setSorts(ParamUtils.convertSortParam(new DefectDocument(), sorts).get(ParamConstans.SUCCESS).toString());
		}
		return defectDocumentDao.findPageDefectDocumentList(defectDocumentForFilterVo);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void collectDefectDocument(List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVoList) {
		if (defectDocumentRfCollectorVoList == null || defectDocumentRfCollectorVoList.isEmpty()) {
			return;
		}
		//过滤重复数据
		Map<String, List<DefectDocumentRfCollectorVo>> map = defectDocumentRfCollectorVoList.stream()
				.filter(vo -> Objects.nonNull(vo.getDefectDocumentId()))
				.filter(vo -> Objects.nonNull(vo.getPersonId()))
				.collect(Collectors.groupingBy(o -> String.format("%s_%s", o.getDefectDocumentId(), o.getPersonId()), Collectors.toList()));

		List<DefectDocumentRfCollector> list = map.entrySet().stream().map(entry -> entry.getValue().get(0)).map(vo -> new DefectDocumentRfCollector(vo.getDefectDocumentId(), vo.getPersonId(), vo.getProduct())).collect(Collectors.toList());
		list.stream().filter(o -> 0 == defectDocumentRfCollectorDao.checkIsCollected(o.getDefectDocumentId(), o.getPersonId(), o.getProduct())).forEach(defectDocumentRfCollectorRepository::save);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void cancelCollectDefectDocument(List<DefectDocumentRfCollectorVo> defectDocumentRfCollectorVoList) {
		if (defectDocumentRfCollectorVoList == null || defectDocumentRfCollectorVoList.isEmpty()) {
			return;
		}
		Map<String, List<DefectDocumentRfCollectorVo>> map = defectDocumentRfCollectorVoList.stream().collect(Collectors.groupingBy(DefectDocumentRfCollectorVo::getPersonId, Collectors.toList()));
		for (Map.Entry<String, List<DefectDocumentRfCollectorVo>> entry : map.entrySet()) {
			List<DefectDocumentRfCollector> list = defectDocumentRfCollectorDao.findDefectDocumentRfCollectorByPersonId(entry.getKey());

			if (!ObjectUtils.isEmpty(list)) {
				Map<String, String> collectedIdMap = entry.getValue().stream().collect(Collectors.toMap(DefectDocumentRfCollectorVo::getDefectDocumentId, DefectDocumentRfCollectorVo::getPersonId));

				list.stream().filter(o -> collectedIdMap.containsKey(o.getDefectDocumentId())).forEach(o -> defectDocumentRfCollectorRepository.delete(o.getId()));
			}
		}
	}
}