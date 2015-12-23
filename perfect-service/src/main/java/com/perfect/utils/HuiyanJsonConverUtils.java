package com.perfect.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.perfect.dto.huiyan.InsightWebsiteDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by subdong on 15-12-22.
 */
public class HuiyanJsonConverUtils {

    public static List<InsightWebsiteDTO> toInsight(String queryString) {
        JSONArray jsonobject = JSONObject.parseArray(queryString);
        List<InsightWebsiteDTO> list = new ArrayList<>();
        jsonobject.stream().filter(item -> item instanceof JSONObject ).forEach(stringObjectEntry -> {
            JSONObject e = (JSONObject) stringObjectEntry;

            InsightWebsiteDTO dto = new InsightWebsiteDTO();
            dto.setId(e.getString("_id"));
            dto.setUid(e.getString("uid"));
            dto.setType_id(e.getString("type_id"));
            dto.setTrack_id(e.getString("track_id"));
            dto.setSite_url(e.getString("site_url"));
            dto.setSite_name(e.getString("site_name"));
            dto.setSite_pause(e.getBoolean("site_pause"));
            dto.setTrack_status(e.getInteger("track_status"));
            dto.setIcon(e.getInteger("icon"));
            dto.setIs_top((e.getBoolean("is_top")));
            dto.setIs_use(e.getInteger("is_use"));
            dto.setBname(e.getString("bname"));
            dto.setBpasswd(e.getString("bpasswd"));
            dto.setRname(e.getString("rname"));
            list.add(dto);

        });
        return list;
    }
}
