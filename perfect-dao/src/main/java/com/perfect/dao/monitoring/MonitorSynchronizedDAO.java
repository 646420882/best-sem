package com.perfect.dao.monitoring;

import com.perfect.dto.monitor.FolderDTO;
import com.perfect.dto.monitor.FolderMonitorDTO;

import java.util.List;

/**
 * Created by SubDong on 2014/9/12.
 */
public interface MonitorSynchronizedDAO {

    public int insterData(List<FolderDTO> forlderEntities);

    public int insterMoniterData(List<FolderMonitorDTO> folderMonitorEntities);
}
