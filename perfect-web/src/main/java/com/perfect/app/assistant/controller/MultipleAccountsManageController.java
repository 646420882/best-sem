package com.perfect.app.assistant.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.perfect.core.AppContext;
import com.perfect.service.AccountDataService;
import com.perfect.service.MultipleAccountManageService;
import com.perfect.web.support.WebContextSupport;
import com.perfect.web.support.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@Scope("prototype")
@RequestMapping(value = "/multipleaccount")
public class MultipleAccountsManageController extends WebContextSupport {

    @Resource
    private MultipleAccountManageService multipleAccountManageService;

    @Resource
    private AccountDataService accountDataService;


    /**
     * 获取账户下载树
     *
     * @return
     */
    @RequestMapping(value = "/get_MultipAccountDownloadtree", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ModelAndView getAccountDownloadstree(HttpServletRequest request) {
        String currSystemUserName = WebUtils.getUserName(request);
        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        Map<String, Object> trees = multipleAccountManageService.getAccountDownloadstree(currSystemUserName);
        jsonView.setAttributesMap(trees);
        return new ModelAndView(jsonView);
    }

    /**
     * 根据选择的树节点下载更新当前多个百度账户下的所有数据
     *
     * @param downloadId
     * @return
     */
    @RequestMapping(value = "/updateMultipAccountData", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public String updateAccountDatas(
            @RequestParam(value = "downloadIds", required = false) String downloadId) {
        List<String> account = new ArrayList<String>(downloadId.split(",").length);
        String downloadIds[] = downloadId.replace("[", "").replace("]", "").split(",");
        for (int i = 0; i < downloadIds.length; i++) {
            if (downloadIds[i].substring(1).split("_")[0].equals(downloadIds[i].substring(0, downloadIds[i].length() - 1).split("_")[1])) {
                account.add(downloadIds[i]);
                // 根节点的json数据
            }
        }
        for (int i = 0; i < account.size(); i++) {
            List<Long> camIds = new ArrayList<>(downloadId.length());
            for (int k = 0; k < downloadIds.length; k++) {
                if (account.get(i).substring(1).split("_")[0].equals(downloadIds[k].substring(1).split("_")[0])) {
                    if (downloadIds == null || "".equals(downloadIds)) {
                        return null;
                    } else {

                        camIds.add(Long.valueOf(downloadIds[k].substring(0, downloadIds[k].length() - 1).split("_")[1]));
                    }
                }

            }
            accountDataService.updateAccountData(AppContext.getUser(), Long.valueOf(account.get(i).replace("\"", "").split("_")[0]), camIds);
        }


        ObjectNode json_string = new ObjectMapper().createObjectNode();
        json_string.put("status", true);
        return json_string.toString();

    }


}