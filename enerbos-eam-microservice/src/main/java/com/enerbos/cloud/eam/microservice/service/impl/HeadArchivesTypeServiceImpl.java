package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.HeadArchivesType;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadArchivesTypeRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadArchivesDao;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadArchivesTypeDao;
import com.enerbos.cloud.eam.microservice.service.HeadArchivesTypeService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVo;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForFilter;
import com.enerbos.cloud.eam.vo.HeadArchivesTypeVoForList;
import com.enerbos.cloud.eam.vo.HeadArchivesVo;
import com.enerbos.cloud.util.ReflectionUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/19.
 */

@Service
public class HeadArchivesTypeServiceImpl implements HeadArchivesTypeService {
    @Autowired
    private HeadArchivesTypeRepository headArchivesTypeRepository;

    @Autowired
    private HeadArchivesTypeDao headArchivesTypeDao;


    @Autowired
    private HeadArchivesDao headArchivesDao;

    @Override
    public PageInfo<HeadArchivesTypeVo> getArchivesTypeList(HeadArchivesTypeVoForFilter headArchivesTypeVoForFilter) {

        PageHelper.startPage(headArchivesTypeVoForFilter.getPageNum(),
                headArchivesTypeVoForFilter.getPageSize());

        String word = headArchivesTypeVoForFilter.getWord();
        Map<String, Object> filters = new HashMap<>();
        try {
            filters = EamCommonUtil.reModelToMap(headArchivesTypeVoForFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        return new PageInfo<HeadArchivesTypeVo>(headArchivesTypeDao.findHeadArchivesTypeList(filters));

    }

    @Override
    public HeadArchivesType saveArchivesType(HeadArchivesType headArchivesType) {

        if (StringUtils.isNotEmpty(headArchivesType.getParentId())) {
            HeadArchivesType headArchivesType1 = headArchivesTypeRepository.findOne(headArchivesType.getParentId());
            headArchivesType1.setHasChild(true);
            headArchivesTypeRepository.save(headArchivesType1);
        }

        return headArchivesTypeRepository.save(headArchivesType);
    }

    @Override
    public HeadArchivesType updateArchivesType(HeadArchivesType headArchivesType) {
        return headArchivesTypeRepository.save(headArchivesType);
    }

    @Override
    public boolean deleteArchivesType(String[] ids) {
        for (String id : ids) {
            HeadArchivesType headArchivesType = headArchivesTypeRepository.findOne(id);

            if (headArchivesType.getHasChild()) {
                return false;
            }
            Map map = new HashMap();
            map.put("materialType", id);
            List<HeadArchivesVo> headArchivesVos = headArchivesDao.findHeadArchivesList(map);

            if (headArchivesVos.size() > 0) {
                return false;
            }

            String pararentId = headArchivesType.getParentId() ;
            if(StringUtils.isNotEmpty(pararentId)){
                HeadArchivesType headArchivesTypeparent = headArchivesTypeRepository.findOne(pararentId);
                headArchivesTypeparent.setHasChild(false);
                headArchivesTypeRepository.save(headArchivesTypeparent);
            }

            headArchivesTypeRepository.delete(id);
        }
        return true;
    }

    @Override
    public HeadArchivesType findArchivesTypeDetail(String id) {
        return headArchivesTypeRepository.findOne(id);
    }


    @Override
    public List<HeadArchivesTypeVoForList> findArchivesTypeAll(String siteId, String orgId) {
        return headArchivesTypeDao.findArchivesTypeAll(siteId, orgId);
    }

    @Override
    public List<HeadArchivesTypeVoForList> getArchivesTypeTree(List<HeadArchivesTypeVoForList> archivesTypeVoForLists, String parentId) {


        List<HeadArchivesTypeVoForList> listTree = new ArrayList<HeadArchivesTypeVoForList>();
        for (HeadArchivesTypeVoForList archivesType : archivesTypeVoForLists) {
            if (archivesType.getParentId() != null && !archivesType.getParentId().equals("") && !archivesType.getParentId().equals(parentId)) {
                continue;
            }
            if (null == parentId) {
                List<HeadArchivesTypeVoForList> treeNodes = getArchivesTypeTree(archivesTypeVoForLists, archivesType.getId());
                archivesType.setChildren(treeNodes);
                listTree.add(archivesType);
            } else if (parentId.equals(archivesType.getParentId())) {
                List<HeadArchivesTypeVoForList> treeNodes = getArchivesTypeTree(archivesTypeVoForLists, archivesType.getId());
                archivesType.setChildren(treeNodes);
                listTree.add(archivesType);
            }
        }
        return listTree;
    }

    @Override
    public HeadArchivesTypeVo findByName(String name, String orgId, String siteId) {
        return headArchivesTypeDao.findByName(name ,orgId,siteId);
    }

    @Override
    @Transactional
    public List<HeadArchivesTypeVo> saveBatchType(List<HeadArchivesTypeVo> headArchivesTypeVos) throws Exception {
        List<HeadArchivesType> headArchivesTypes  = new ArrayList<>() ;

        Map<String,String> nameAndParentMap  = new HashMap<>();

        for (HeadArchivesTypeVo headArchivesTypeVo:headArchivesTypeVos
                ) {
            nameAndParentMap.put(headArchivesTypeVo.getTypeName(),headArchivesTypeVo.getParentName()) ;
        }

        ReflectionUtils.copyProperties(headArchivesTypeVos,headArchivesTypes,new HeadArchivesType());

        Map<String ,String> nameAndIdMap = new HashMap<>() ;
        for (HeadArchivesType headArchivesType: headArchivesTypes
             ) {
            nameAndIdMap.put(headArchivesType.getTypeName(),headArchivesType.getId());
        }
        List<HeadArchivesType> parentTypes = new ArrayList<>();
        for (HeadArchivesType headArchivesType: headArchivesTypes
             ) {
            if(nameAndParentMap.containsKey(headArchivesType.getTypeName()) && StringUtils.isNotEmpty(nameAndParentMap.get(headArchivesType.getTypeName()))){
                headArchivesType.setParentId(nameAndIdMap.get(nameAndParentMap.get(headArchivesType.getTypeName())));
                parentTypes.add(headArchivesType);
            }
        }
        headArchivesTypeRepository.save(parentTypes);
        ReflectionUtils.copyProperties(headArchivesTypes,headArchivesTypeVos,new HeadArchivesTypeVo());
        return headArchivesTypeVos;
    }

}
