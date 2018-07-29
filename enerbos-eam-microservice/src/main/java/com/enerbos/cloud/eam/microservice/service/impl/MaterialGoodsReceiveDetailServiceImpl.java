package com.enerbos.cloud.eam.microservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enerbos.cloud.eam.microservice.domain.MaterialGoodsReceiveDetail;
import com.enerbos.cloud.eam.microservice.repository.jpa.MaterialGoodsReceiveDetailRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.MaterialGoodsReceiveDetailDao;
import com.enerbos.cloud.eam.microservice.service.MaterialGoodsReceiveDetailService;
import com.enerbos.cloud.eam.vo.MaterialGoodsReceiveDetailVoForList;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MaterialGoodsReceiveDetailServiceImpl implements
        MaterialGoodsReceiveDetailService {

    @Autowired
    private MaterialGoodsReceiveDetailRepository matrectransRepository;

    @Autowired
    private MaterialGoodsReceiveDetailDao materialGoodsReceiveDetailDao;

	/*
     * (non-Javadoc)
	 * 
	 * @see
	 * com.enerbos.cloud.eam.microservice.inv.service.impl.MatrectransService
	 * #findMatrectrans(java.util.Map, java.lang.Integer, java.lang.Integer)
	 */

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<MaterialGoodsReceiveDetailVoForList> findGoodsReceiveDetailByGoodsReceiveId(
            String id, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);

        return new PageInfo<MaterialGoodsReceiveDetailVoForList>(
                materialGoodsReceiveDetailDao.findGoodsReceiveDetailByGoodsReceiveId(id));
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.MatrectransService
     * #saveMatrectrans
     * (com.enerbos.cloud.eam.microservice.inv.domain.Matrectrans)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialGoodsReceiveDetail saveMatrectrans(
            MaterialGoodsReceiveDetail matrectrans) {

        return matrectransRepository.save(matrectrans);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.MatrectransService
     * #updateInventory
     * (com.enerbos.cloud.eam.microservice.inv.domain.Matrectrans)
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public MaterialGoodsReceiveDetail updateGoodsReceiveDetail(
            MaterialGoodsReceiveDetail matrectrans) {

        return saveMatrectrans(matrectrans);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.enerbos.cloud.eam.microservice.inv.service.impl.MatrectransService
     * #deleteMatrectrans(java.lang.String[])
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void deleteGoodsReceiveDetail(String ids[]) {
        for (String id : ids) {
            matrectransRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public MaterialGoodsReceiveDetail findGoodsReceiveDetailById(String id) {
        return matrectransRepository.findOne(id);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public List<MaterialGoodsReceiveDetail> saveMaterialGoodsReceiveDetailList(List<MaterialGoodsReceiveDetail> materialGoodsReceiveDetailList) {
        return matrectransRepository.save(materialGoodsReceiveDetailList);
    }

}
