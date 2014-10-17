package com.perfect.dao;

import com.perfect.entity.FolderEntity;
import com.perfect.entity.FolderMonitorEntity;

import java.util.List;

/**
 * Created by SubDong on 2014/9/12.
 */
public interface MonitorSynchronizedDAO {

    public int insterData(List<FolderEntity> forlderEntities);

    public int insterMoniterData(List<FolderMonitorEntity> folderMonitorEntities);
}
