package com.perfect.param;

import java.util.List;

/**
 * Created by yousheng on 15/12/16.
 */
public class Menu {
    private String id;

    private String pid;

    private List<String> subMenus;

    public List<String> getSubMenus() {
        return subMenus;
    }

    public Menu setSubMenus(List<String> subMenus) {
        this.subMenus = subMenus;
        return this;
    }

    public String getPid() {
        return pid;
    }

    public Menu setPid(String pid) {
        this.pid = pid;
        return this;
    }

    public String getId() {
        return id;
    }

    public Menu setId(String id) {
        this.id = id;
        return this;
    }
}
