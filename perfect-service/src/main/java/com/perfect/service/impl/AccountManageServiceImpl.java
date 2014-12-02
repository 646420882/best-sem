package com.perfect.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dto.SystemUserDTO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.baidu.BaiduAccountAllStateDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.service.AccountManageService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.MD5Utils;
import com.perfect.utils.json.JSONUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by baizz on 2014-8-21.
 * 2014-12-2 refactor
 */
@Service("accountManageService")
public class AccountManageServiceImpl implements AccountManageService {

    @Resource
    private AccountManageDAO accountManageDAO;

    @Override
    public int updatePwd(String password, String newPwd) {
        SystemUserDTO currUserInfo = getCurrUserInfo();

        MD5Utils.Builder builder = new MD5Utils.Builder();
        MD5Utils md5 = builder.password(password).salt(currUserInfo.getUserName()).build();

        MD5Utils md5NewPwd = builder.password(newPwd).salt(currUserInfo.getUserName()).build();

        int i;
        if (md5.getMD5().equals(currUserInfo.getPassword())) {
            boolean writeResult = accountManageDAO.updatePwd(currUserInfo.getUserName(), md5NewPwd.getMD5());
            if (writeResult) {
                i = 1;
            } else {
                i = 0;
            }
        } else {
            i = -1;
        }
        return i;
    }

    @Override
    public int JudgePwd(String password) {
        SystemUserDTO currUserInfo = getCurrUserInfo();

        MD5Utils.Builder builder = new MD5Utils.Builder();
        MD5Utils md5 = builder.password(password).salt(currUserInfo.getUserName()).build();
        int i;
        if (md5.getMD5().equals(currUserInfo.getPassword())) {
            i = 1;
        } else {
            i = -1;
        }

        return i;
    }

    @Override
    public List<SystemUserDTO> getAccount() {
        List<SystemUserDTO> entities = accountManageDAO.getAccount();
        return entities;
    }

    @Override
    public int auditAccount(String userNmae) {
        int flagStruts = accountManageDAO.updateAccountStruts(userNmae);
        return flagStruts;
    }

    @Override
    public List<BaiduAccountAllStateDTO> getAccountAll() {
        List<SystemUserDTO> systemUserDTOList = accountManageDAO.getAccountAll();
        List<BaiduAccountAllStateDTO> allStates = new ArrayList<>();

        for (SystemUserDTO systemUserDTO : systemUserDTOList) {
            if (systemUserDTO.getUserName().equals("administrator")) {
                continue;
            }
            if (systemUserDTO.getBaiduAccountInfoDTOs().size() > 0) {
                for (BaiduAccountInfoDTO dto : systemUserDTO.getBaiduAccountInfoDTOs()) {
                    BaiduAccountAllStateDTO accountAllState = new BaiduAccountAllStateDTO();
                    accountAllState.setIdObj(dto.getId());
                    accountAllState.setUserName(systemUserDTO.getUserName());
                    accountAllState.setUserState(systemUserDTO.getState());
                    accountAllState.setBaiduUserName(dto.getBaiduUserName());
                    accountAllState.setBaiduState(dto.getState());
                    allStates.add(accountAllState);
                }
            } else {
                BaiduAccountAllStateDTO accountAllState = new BaiduAccountAllStateDTO();
                accountAllState.setIdObj(0l);
                accountAllState.setUserName(systemUserDTO.getUserName());
                accountAllState.setUserState(systemUserDTO.getState());
                accountAllState.setBaiduUserName(" ");
                accountAllState.setBaiduState(0l);
                allStates.add(accountAllState);
            }

        }
        return allStates;
    }

    @Override
    public int updateAccountAllState(String userName, Long baiduId, Long state) {
        int i = 0;
        boolean writeResult = accountManageDAO.updateBaiDuAccount(userName, baiduId, state);
        if (writeResult) {
            i = 1;
        }
        return i;
    }

    public Map<String, Object> getAccountTree() {
        ArrayNode treeNodes = accountManageDAO.getAccountTree();
        Map<String, Object> trees = new HashMap<>();
        trees.put("trees", treeNodes);
        return trees;
    }

    @Override
    public SystemUserDTO getCurrUserInfo() {
        return accountManageDAO.getCurrUserInfo();
    }

    @Override
    public void uploadImg(byte[] bytes) {
        accountManageDAO.uploadImg(bytes);
    }

    @Override
    public Map<String, Object> getAllBaiduAccount(String currSystemUserName) {
        List<BaiduAccountInfoDTO> list = accountManageDAO.getBaiduAccountItems(currSystemUserName);
        return JSONUtils.getJsonMapData(list);
    }

    @Override
    public List<BaiduAccountInfoDTO> getAllBaiduAccount() {
        List<BaiduAccountInfoDTO> baiduAccountList = new ArrayList<>();
        accountManageDAO.getAllSysUserAccount().stream().forEach(e -> {
            if (e.getBaiduAccountInfoDTOs().size() > 0)
                baiduAccountList.addAll(e.getBaiduAccountInfoDTOs());
        });
        return baiduAccountList;
    }

    public Map<String, Object> getBaiduAccountInfoByUserId(Long baiduUserId) {
        BaiduAccountInfoDTO dto = accountManageDAO.findByBaiduUserId(baiduUserId);
        Map<String, Object> results = JSONUtils.getJsonMapData(dto);
        results.put("cost", getYesterdayCost(baiduUserId));
        results.put("costRate", accountManageDAO.getCostRate());
        //从凤巢获取budgetOfflineTime
        try {
            CommonService service = ServiceFactory.getInstance(dto.getBaiduUserName(), dto.getBaiduPassword(), dto.getToken(), null);
            AccountService accountService = service.getService(AccountService.class);
            GetAccountInfoRequest request = new GetAccountInfoRequest();
            GetAccountInfoResponse response = accountService.getAccountInfo(request);
            AccountInfoType accountInfo = response.getAccountInfoType();
            List<OfflineTimeType> list = accountInfo.getBudgetOfflineTime();
            if (list.size() == 0) {
                results.put("budgetOfflineTime", "");
            } else {
                results.put("budgetOfflineTime", getJson(list));
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return results;
    }

    public BaiduAccountInfoDTO getBaiduAccountInfoById(Long baiduUserId) {
        return accountManageDAO.findByBaiduUserId(baiduUserId);
    }

    public void updateBaiduAccount(BaiduAccountInfoDTO baiduAccountInfoDTO) {
        accountManageDAO.updateBaiduAccountInfo(baiduAccountInfoDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAccountReports(int number) {
        List<Date> dates = (List<Date>) DateUtils.getsLatestAnyDays("MM-dd", number).get(DateUtils.KEY_DATE);
        List<AccountReportDTO> list = accountManageDAO.getAccountReports(dates);
        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        values.put("dates", JSONUtils.getJsonObjectArray(DateUtils.getsLatestAnyDays("MM-dd", 7).get(DateUtils.KEY_STRING)));
        return values;
    }

    public Double getYesterdayCost(Long accountId) {
        return accountManageDAO.getYesterdayCost(accountId);
    }

    protected JsonNode getJson(Object o) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd/HH"));
        Map<String, Object> values = new LinkedHashMap<>();
        try {
            JsonNode jsonNode = mapper.readTree(mapper.writeValueAsBytes(o));
            return jsonNode;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}