package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.common.EnerbosException;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeter;
import com.enerbos.cloud.eam.microservice.domain.DaliyCopyMeterDetail;
import com.enerbos.cloud.eam.microservice.repository.jpa.DaliyCopyMeterDetailRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.DaliyCopyMeterRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.DaliyCopyMeterRfCollectorRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.DaliyCopyMeterDao;
import com.enerbos.cloud.eam.microservice.service.DaliyCopyMeterService;
import com.enerbos.cloud.eam.vo.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DaliyCopyMeterServiceImpl implements DaliyCopyMeterService {

    @Autowired
    private DaliyCopyMeterDao daliyCopyMeterDao;

    @Autowired
    private DaliyCopyMeterRepository daliyCopyMeterRepository;
    @Autowired
    private DaliyCopyMeterDetailRepository daliyCopyMeterDetailRepository;

    @Autowired
    private DaliyCopyMeterRfCollectorRepository daliyCopyMeterRfCollectorRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<DailyCopyMeterVoForList> findCopyMeters(DailyCopyMeterFilterVo dailyCopyMeterFilterVo) throws Exception {
        PageHelper.startPage(dailyCopyMeterFilterVo.getPageNum(), dailyCopyMeterFilterVo.getPageSize());
        Map<String, Object> filters = null;
        if (StringUtils.isNotEmpty(dailyCopyMeterFilterVo.getKeyword())) {
            dailyCopyMeterFilterVo.setKeywords(dailyCopyMeterFilterVo.getKeyword().split(" "));
        }
        filters = com.enerbos.cloud.util.ReflectionUtils.reflectionModelToMap(dailyCopyMeterFilterVo);
        return new PageInfo<DailyCopyMeterVoForList>(daliyCopyMeterDao.findCopyMeters(filters));
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public DaliyCopyMeter saveCopyMeter(DaliyCopyMeter daliyCopyMeter) {
        return daliyCopyMeterRepository.save(daliyCopyMeter);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<DaliyCopyMeterDetail> saveCopyMeterDetail(
            List<DaliyCopyMeterDetail> daliyCopyMeterDetails) {
        return daliyCopyMeterDetailRepository.save(daliyCopyMeterDetails);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteCopyMeter(String[] ids) {
        for (String id : ids) {
            daliyCopyMeterDetailRepository.deleteByCopyMeterId(id);
            daliyCopyMeterRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<DailyCopyMeterDetailForList> findCopyMeterDetails(String id, String siteId, String orgId) {
        return new PageInfo<DailyCopyMeterDetailForList>(daliyCopyMeterDao.findCopyMeterDetails(id, siteId, orgId));
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DaliyCopyMeter findCopyMeterById(String id) {
        return daliyCopyMeterRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteCopyMeterDetail(String[] ids) {
        for (String id : ids) {
            daliyCopyMeterDetailRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DaliyCopyMeterDetail findCopyMeterDetailById(String id) {
        return daliyCopyMeterDao.findCopyMeterDetailById(id) ;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void updateCopyMeterStatus(String[] ids, String status) {
        for (String id : ids) {
            DaliyCopyMeter daliyCopyMeter = findCopyMeterById(id);
            daliyCopyMeter.setStatus(status);
            this.saveCopyMeter(daliyCopyMeter);
        }
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public PageInfo<DailyCopyMeterDetailForList> findCopyMeterDetailByMeterId(String id, String siteId, String orgId) {
        List<DailyCopyMeterDetailForList> list = daliyCopyMeterDao.findCopyMeterDetailByMeterId(id, siteId, orgId);
        return new PageInfo<DailyCopyMeterDetailForList>(list);

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<DailyCopyMeterDetailForList> findcopymeterDetailByCopyMeterId(String copyMeterId) throws EnerbosException {
        List<DailyCopyMeterDetailForList> list = new ArrayList<>();
        List<DaliyCopyMeterDetail> byCopyMeterId = daliyCopyMeterDetailRepository.findByCopyMeterId(copyMeterId);
        for (DaliyCopyMeterDetail daliyCopyMeterDetail : byCopyMeterId) {
            DailyCopyMeterDetailForList cmd = new DailyCopyMeterDetailForList();
            BeanUtils.copyProperties(daliyCopyMeterDetail, cmd);
            list.add(cmd);
        }
        return list;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<DailyCopyMeterVo> findCopyMeterByIds(List<String> ids) {
        return daliyCopyMeterDao.findByIds(ids);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public DailyCopyMeterDetailVo findCopyMeterDetailByCopyMeterIdAndMeterId(String copyMeterId, String meterId) {
        return daliyCopyMeterDao.findCopyMeterDetailByCopyMeterIdAndMeterId(copyMeterId,meterId);
    }

}
