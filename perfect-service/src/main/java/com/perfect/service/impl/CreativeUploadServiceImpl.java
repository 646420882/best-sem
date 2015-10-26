package com.perfect.service.impl;

import com.perfect.dao.creative.CreativeDAO;
import com.perfect.service.CreativeUploadService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created on 2015-10-23.
 *
 * @author dolphineor
 */
@Service
public class CreativeUploadServiceImpl implements CreativeUploadService {

    @Resource
    private CreativeDAO creativeDAO;


    @Override
    public Map<String, Map<Integer, List<String>>> deduplicate(Long baiduUserId) {
        return null;
    }

    @Override
    public Map<Integer, List<String>> deduplicate(Long baiduUserId, Long adgroupId) {
        return null;
    }
}
