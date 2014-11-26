package com.perfect.service.impl;

import com.perfect.dao.AdgroupBackUpDAO;
import com.perfect.dao.AdgroupDAO;
import com.perfect.db.mongodb.base.AbstractUserBaseDAOImpl;
import com.perfect.dto.backup.AdgroupBackupDTO;
import com.perfect.entity.backup.AdgroupBackUpEntity;
import com.perfect.service.AdgroupBackUpService;
import com.perfect.utils.Pager;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by XiaoWei on 2014/9/4.
 */
@Service("adgroupBackUpService")
public class AdgroupBackUpServiceImpl extends AbstractUserBaseDAOImpl<AdgroupBackupDTO,Long> implements AdgroupBackUpService {
    @Resource
    private AdgroupBackUpDAO adgroupBackUpDAO;
    @Resource
    private AdgroupDAO adgroupDAO;
    @Override
    public Class<AdgroupBackupDTO> getEntityClass() {
        return null;
    }

    @Override
    public Pager findByPager(int start, int pageSize, Map<String, Object> q, int orderBy) {
        return null;
    }

    @Override
    public AdgroupBackupDTO agReBack(Long id) {
        AdgroupBackupDTO adgroupBackupDTOFind=adgroupBackUpDAO.findByLongId(id);
        if (adgroupBackupDTOFind!=null){
            adgroupBackupDTOFind.setLocalStatus(null);
            adgroupDAO.insertReBack(adgroupBackupDTOFind);
            adgroupBackUpDAO.deleteByLongId(id);
        }
        return adgroupBackupDTOFind;
    }
}
