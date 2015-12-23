package com.perfect.dto.huiyan;

/**
 * Created by subdong on 15-12-21.
 */
public class InsightWebsiteDTO {

    /**
     * 数据ID
     */
    private String id;
    /**
     * 用户id
     */
    private String uid;

    /**
     * es type id
     */
    private String type_id;

    /**
     * js track id
     */
    private String track_id;

    /**
     * 网站url
     */
    private String site_url;

    /**
     * 网站名称
     */
    private String site_name;

    /**
     * 配置暂停/启用设置  true暂停
     */
    private Boolean site_pause;

    /**
     * track code  status
     */
    private int track_status;

    /**
     * 默认显示图标
     */
    private int icon;

    /**
     * 是否置顶
     */
    private Boolean is_top;

    /**
     * 是否禁用 0停用  1启用
     */
    private int is_use;

    /**
     * 百度账号
     */
    private String bname;

    /**
     * 百度密码
     */
    private String bpasswd;

    /**
     * 备注
     */
    private String rname;

    /**
     * 绑定时间
     */
    private Long ctime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getTrack_id() {
        return track_id;
    }

    public void setTrack_id(String track_id) {
        this.track_id = track_id;
    }

    public String getSite_url() {
        return site_url;
    }

    public void setSite_url(String site_url) {
        this.site_url = site_url;
    }

    public String getSite_name() {
        return site_name;
    }

    public void setSite_name(String site_name) {
        this.site_name = site_name;
    }

    public Boolean isSite_pause() {
        return site_pause;
    }

    public void setSite_pause(Boolean site_pause) {
        this.site_pause = site_pause;
    }

    public int getTrack_status() {
        return track_status;
    }

    public void setTrack_status(int track_status) {
        this.track_status = track_status;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Boolean is_top() {
        return is_top;
    }

    public void setIs_top(Boolean is_top) {
        this.is_top = is_top;
    }

    public int getIs_use() {
        return is_use;
    }

    public void setIs_use(int is_use) {
        this.is_use = is_use;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getBpasswd() {
        return bpasswd;
    }

    public void setBpasswd(String bpasswd) {
        this.bpasswd = bpasswd;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }
}
