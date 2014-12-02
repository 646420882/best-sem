package com.perfect.dto.keyword;

import com.perfect.dto.BaseDTO;

/**
 * Created by baizz on 2014-11-28.
 */
public class RankDTO extends BaseDTO {
    private String keyword;

    private String host;

    private String desc;

    private String title;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
