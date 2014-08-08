package com.perfect.app.keyword.controller;

import com.perfect.dao.KeywordGroupDAO;
import com.perfect.mongodb.dao.impl.KeywordGroupDAOImpl;
import com.perfect.utils.JSONUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by baizz on 2014-08-08.
 */
@RestController
@Scope("prototype")
public class KeywordGroupController implements Controller {

    private String tmp;

    @Override
    @RequestMapping(value = "/getKRWords", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        Object o = httpServletRequest.getParameter("seedWords");
        KeywordGroupDAOImpl keywordGroupDAO = new KeywordGroupDAOImpl();
        List<String> list = new ArrayList<String>(){{
            add("婚博会");
            add("北京婚博会");
        }};
        String krFilePath = keywordGroupDAO.getKRFilePath(list);
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> attributes = new HashMap<>(1);
        attributes.put("filePath", krFilePath);
        jsonView.setAttributesMap(attributes);
        return new ModelAndView(jsonView);
    }
}
