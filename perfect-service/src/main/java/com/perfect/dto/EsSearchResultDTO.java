package com.perfect.dto;

import com.perfect.entity.CreativeSourceEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vbzer_000 on 2014/9/19.
 */
public class EsSearchResultDTO {
    private long total;
    private List<CreativeSourceEntity> list;
    private List<KeyValuePair> keywords = new ArrayList<>();

    private List<KeyValuePair> hosts = new ArrayList<>();

    private List<KeyValuePair> regions = new ArrayList<>();

    public void setTotal(long total) {
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setList(List<CreativeSourceEntity> list) {
        this.list = list;
    }

    public List<CreativeSourceEntity> getList() {
        return list;
    }

    public void addKeyword(String key, Object value) {
        keywords.add(new KeyValuePair(key, value));
    }

    public List<KeyValuePair> getKeywords() {
        return this.keywords;
    }

    public List<KeyValuePair> getHosts() {
        return hosts;
    }

    public List<KeyValuePair> getRegions() {
        return regions;
    }

    public void addHost(String key, Object value) {
        hosts.add(new KeyValuePair(key, value));
    }


    public void addRegions(String key, Object value) {
        regions.add(new KeyValuePair(key, value));
    }

    public static class KeyValuePair {
        private String key;

        private Object value;

        public KeyValuePair(String key, Object value) {
            this.key = key;
            this.value = value;
        }


        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
