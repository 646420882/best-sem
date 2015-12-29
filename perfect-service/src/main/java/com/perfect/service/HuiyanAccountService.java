package com.perfect.service;

import com.perfect.dto.huiyan.InsightWebsiteDTO;

import java.util.List;

/**
 * Created on 2015-12-20.
 * <p>慧眼帐号接口
 *
 * @author dolphineor
 */
public interface HuiyanAccountService {

    /**
     * 添加用户慧眼数据
     *
     * @param websiteDTO
     */
    String insertInsight(InsightWebsiteDTO websiteDTO);

    /**
     * 查询用户数据
     *
     * @param uid
     * @return
     */
    List<InsightWebsiteDTO> queryInfo(String uid);

    /**
     * 查询用户数据
     *
     * @param id
     * @return
     */
    List<InsightWebsiteDTO> queryInfoByid(String id);

    /**
     * 删除慧眼数据
     *
     * @param id
     */
    String del(String id);

    /**
     * 慧眼数据修改
     *
     * @param uid
     * @param rname
     * @param url
     * @param webName
     * @return
     */
    String huiyanUpdate(String uid, String rname, String url, String webName, String bname, String bpwd);

    /**
     * url 启用暂停
     *
     * @param id
     * @return
     */
    String huiyanEnableOrPause(String id, boolean enable);

    /**
     * 对应的慧眼url 添加Token
     *
     * @param id
     * @param token
     * @return
     */
    String updateHuiyanToken(String id, String token);

}
