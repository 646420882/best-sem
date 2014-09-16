package com.perfect.api.baidu;

import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.core.AppContext;
import com.perfect.dao.AccountManageDAO;
import com.perfect.entity.BaiduAccountInfoEntity;
import com.perfect.mongodb.dao.impl.AccountManageDAOImpl;
import com.perfect.utils.BaiduServiceSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by SubDong on 2014/9/10.
 */
public class PromotionMonitoring {

    //得到百度aip
    private CommonService service = null;

    private FolderService folderService = null;

    public PromotionMonitoring() {
    }

    public PromotionMonitoring(BaiduAccountInfoEntity entity) {
        service = BaiduServiceSupport.getCommonService(entity);
        init();
    }

    public void init() {
        try {
            folderService = service.getService(FolderService.class);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前用户下的所有监控文件夹
     */
    public List<Folder> getFolder() {
        Long[] folderIds = new Long[]{};
        //创建请求
        GetFolderRequest request = new GetFolderRequest();
        request.setFolderIds(Arrays.asList(folderIds));
        //获取当前用户下的所有监控文件夹信息
        GetFolderResponse folder = folderService.getFolder(request);
        List<Folder> folderList = folder.getFolders();

        return folderList;
    }

    /**
     * 获取当前用户下的所有监控对象信息
     */
    public List<FolderMonitor> getMonitorWordByFolderIdAll() {
        //获取用户所有监控文件夹信息
        List<Folder> folderList = getFolder();
        List<Long> folderLong = new ArrayList<>();
        for (Folder folder : folderList) {
            folderLong.add(folder.getFolderId());
        }
        //创建监控对象请求
        GetMonitorWordByFolderIdRequest request = new GetMonitorWordByFolderIdRequest();
        request.setFolderIds(folderLong);
        //接收请求API后的数据
        GetMonitorWordByFolderIdResponse monitorword = folderService.getMonitorWordByFolderId(request);

        List<FolderMonitor> folderMonitors = monitorword.getFolderMonitors();
        return folderMonitors;
    }

    /**
     * 获取当前用户下相应监控文件夹下的监控对象信息
     *
     * @return
     */
    public List<FolderMonitor> getMonitorWordByFolderId(List<Long> folderLong) {
        //创建监控对象请求
        GetMonitorWordByFolderIdRequest request = new GetMonitorWordByFolderIdRequest();
        request.setFolderIds(folderLong);
        //接收请求API后的数据
        GetMonitorWordByFolderIdResponse monitorword = folderService.getMonitorWordByFolderId(request);

        List<FolderMonitor> folderMonitors = monitorword.getFolderMonitors();
        return folderMonitors;
    }

    /**
     * 新建当前用户的监控文件夹
     *
     * @param folders
     * @return
     */
    public List<Folder> addFolder(List<Folder> folders) {
        //创建监控对象请求
        AddFolderRequest request = new AddFolderRequest();
        request.setFolders(folders);
        //接收请求API后的数据
        AddFolderResponse addFolder = folderService.addFolder(request);
        List<Folder> list = addFolder.getFolders();
        return list;
    }

    /**
     * 更新当前用户的监控文件夹信息
     *
     * @param folders
     * @return
     */
    public List<Folder> updateFolder(List<Folder> folders) {
        //创建监控对象请求
        UpdateFolderRequest request = new UpdateFolderRequest();
        request.setFolders(folders);
        //接收请求API后的数据
        UpdateFolderResponse response = folderService.updateFolder(request);
        List<Folder> folderList = response.getFolders();
        return folderList;
    }

    /**
     * 新建监控对象。一个账户下最多2000个监控对象。
     *
     * @param monitors
     * @return
     */
    public List<Monitor> addMonitorWord(List<Monitor> monitors) {
        //创建请求
        AddMonitorWordRequest addMonitorWordRequest = new AddMonitorWordRequest();
        addMonitorWordRequest.setMonitors(monitors);
        //获取返回数据
        AddMonitorWordResponse addMonitorWordResponse = folderService.addMonitorWord(addMonitorWordRequest);

        List<Monitor> monitorList = addMonitorWordResponse.getMonitors();
        return monitorList;
    }


    public int deleteFolder(List<Long> folders) {
        DeleteFolderRequest request = new DeleteFolderRequest();
        request.setFolderIds(folders);
        return -1;
    }
}
