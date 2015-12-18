package com.perfect.admin.utils;

import com.perfect.commons.web.JsonResultMaps;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Map;

/**
 * Created by yousheng on 15/12/15.
 */
public class JsonViews {


    public static ModelAndView generate(Map<String, Object> map) {
        MappingJackson2JsonView av = new MappingJackson2JsonView();
        av.setPrettyPrint(true);
        av.setAttributesMap(map);
        return new ModelAndView(av);
    }


    public static ModelAndView generateSuccessNoData() {
        return generate(JsonResultMaps.successMap());
    }

    public static ModelAndView generateFailedNoData() {
        return generate(JsonResultMaps.failedMap());
    }


    public static ModelAndView generate(int code) {
        return generate(JsonResultMaps.map(code));
    }

    public static ModelAndView generate(int code, String msg) {
        return generate(JsonResultMaps.map(code, msg));
    }
}
