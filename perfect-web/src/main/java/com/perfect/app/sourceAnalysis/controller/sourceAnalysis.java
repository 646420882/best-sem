package com.perfect.app.sourceAnalysis.controller;

import com.perfect.service.CensusService;
import com.perfect.utils.json.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.AbstractView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by SubDong on 2014/11/19.
 */
@RestController
@Scope("prototype")
@RequestMapping("/pftstis")
public class sourceAnalysis{

    @Resource
    private CensusService censusService;

    /**
     * 全部来源
     *
     * @return
     */
    @RequestMapping(value = "/getAllSourcePage", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getAllSourcePage() {

        return new ModelAndView("sourceAnalysis/allSource");
    }

    /**
     * 全部来源
     *
     * @return
     */
    @RequestMapping(value = "/getAllSource", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView getAllSource(HttpServletResponse response) {
        Map<String, Map<String, String>> mainMap = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("llbl", "99.99%");
        map.put("fwcs", "854");
        map.put("xfks", "442");
        map.put("xfkbl", "78%");
        map.put("ddzhl", "50.99%");
        mainMap.put("head", map);


        return new ModelAndView(getJsonView(JSONUtils.getJsonMapData(mainMap)));
    }



    private View getJsonView(Map<String, Object> attributes) {
        AbstractView jsonView = new MappingJackson2JsonView();
        if (attributes == null) {
            jsonView.setAttributesMap(new HashMap<String, Object>(1) {{
                put("stat", true);
            }});
            return jsonView;
        }

        jsonView.setAttributesMap(attributes);
        return jsonView;
    }
}
