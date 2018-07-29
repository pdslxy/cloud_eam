package com.enerbos.cloud.eam.microservice.service.impl;

import com.enerbos.cloud.eam.microservice.domain.HeadArchivesLog;
import com.enerbos.cloud.eam.microservice.repository.jpa.HeadArchivesLogRepository;
import com.enerbos.cloud.eam.microservice.repository.mybatis.HeadArchivesLogDao;
import com.enerbos.cloud.eam.microservice.service.HeadArchivesLogService;
import com.enerbos.cloud.eam.microservice.util.EamCommonUtil;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVo;
import com.enerbos.cloud.eam.vo.HeadArchivesLogVoForFilter;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Enerbos on 2016/10/19.
 */

@Service
public class HeadArchivesLogServiceImpl implements HeadArchivesLogService {
    @Autowired
    private HeadArchivesLogRepository headArchivesLogRepository;

    @Autowired
    private HeadArchivesLogDao headArchivesLogDao;
    
    @Override
    public PageInfo<HeadArchivesLogVo> getArchivesLogList(HeadArchivesLogVoForFilter headArchivesLogVoForFilter) {

        PageHelper.startPage(headArchivesLogVoForFilter.getPageNum(),
                headArchivesLogVoForFilter.getPageSize());

        String word = headArchivesLogVoForFilter.getWord();
        Map<String, Object> filters=new HashMap<>();
        try{
            filters = EamCommonUtil.reModelToMap(headArchivesLogVoForFilter);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (StringUtil.isNotEmpty(word)) {
            String[] words = word.split(" ");
            filters.put("words", words);
        }
        return new PageInfo<HeadArchivesLogVo>(headArchivesLogDao.findHeadArchivesLogList(filters));

    }

    @Override
    public HeadArchivesLog saveArchivesLog(HeadArchivesLog headArchivesLog) {
        return headArchivesLogRepository.save(headArchivesLog);
    }

    @Override
    public HeadArchivesLog updateArchivesLog(HeadArchivesLog headArchivesLog) {
        return headArchivesLogRepository.save(headArchivesLog);
    }

    @Override
    public void deleteArchivesLog(String[] ids) {
        for (String id : ids) {
            headArchivesLogRepository.delete(id);
        }
    }

    @Override
    public HeadArchivesLog findArchivesLogDetail(String id) {
        return headArchivesLogRepository.findOne(id);
    }
}
