package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.HeadArchives;
import com.enerbos.cloud.eam.microservice.domain.HeadArchivesType;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadArchivesRepository;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadArchivesTypeRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadArchivesDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadArchivesTypeDao;
import com.enerbos.cloud.eam.microservice.service.HeadArchivesService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.HeadArchivesVo;
import com.enerbos.cloud.eam.vo.HeadArchivesVoForFilter;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/19.
 */

@Service
public class HeadArchivesServiceImpl implements HeadArchivesService {
    @Autowired
    private HeadArchivesRepository headArchivesRepository;

    @Autowired
    private HeadArchivesDao headArchivesDao;

    @Autowired
    private HeadArchivesTypeRepository headArchivesTypeRepository;

    @Autowired
    private HeadArchivesTypeDao headArchivesTypeDao;

    List<String> ids = new ArrayList<>();

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public PageInfo<HeadArchivesVo> getArchivesList(HeadArchivesVoForFilter headArchivesVoForFilter) {


        PageHelper.startPage(headArchivesVoForFilter.getPageNum(),
                headArchivesVoForFilter.getPageSize());

        String word = headArchivesVoForFilter.getWord();
        Map<String, Object> filters = new HashMap<>();

        try {
            filters = EamCommonUtil.reModelToMap(headArchivesVoForFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        if (StringUtils.isNotEmpty(headArchivesVoForFilter.getMaterialType())) {
            filters.put("typeIds", getIds(headArchivesVoForFilter.getMaterialType()));
        }
        System.out.println(filters.toString());
        return new PageInfo<HeadArchivesVo>(headArchivesDao.findHeadArchivesList(filters));

    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    private List<String> getIds(String materialType) {

        Map map = new HashMap();
        map.put("parentId", materialType);
        List<HeadArchivesType> headArchivesTypes = headArchivesTypeDao.findHeadArchivesTypeListByParentId(map);
        ids.add(materialType);
        for (HeadArchivesType headArchivesType : headArchivesTypes) {
            if (!headArchivesType.getHasChild()) {
                ids.add(headArchivesType.getId());
            } else {
                getIds(headArchivesType.getId());
            }
        }
        return ids;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public HeadArchives saveArchives(HeadArchives headArchives) {
        return headArchivesRepository.save(headArchives);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public HeadArchives updateArchives(HeadArchives headArchives) {
        return headArchivesRepository.save(headArchives);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void deleteArchives(String[] ids) {
        for (String id : ids) {
            headArchivesRepository.delete(id);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public HeadArchives findArchivesDetail(String id) {
        return headArchivesRepository.findOne(id);
    }

    /**
     * 批量导入档案
     *
     * @param headArchivesVoList
     */
    @Override
    @Transactional
    public void saveBatchArchives(List<HeadArchivesVo> headArchivesVoList) throws Exception {
        List<HeadArchives> headArchives = new ArrayList<HeadArchives>();
         ReflectionUtils.copyProperties(headArchivesVoList , headArchives ,new HeadArchives());
        this.headArchivesRepository.save(headArchives);
    }

    @Override
    public HeadArchivesVo findByArchivesNum(String archivesNum, String orgId, String siteId) {
        return headArchivesDao.findByArchivesNum(archivesNum,orgId,siteId);
    }

}
