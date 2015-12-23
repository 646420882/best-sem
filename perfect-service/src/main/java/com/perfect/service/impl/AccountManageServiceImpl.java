package com.perfect.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.perfect.autosdk.core.CommonService;
import com.perfect.autosdk.core.ServiceFactory;
import com.perfect.autosdk.exception.ApiException;
import com.perfect.autosdk.sms.v3.*;
import com.perfect.commons.constants.SystemNameConstant;
import com.perfect.core.AppContext;
import com.perfect.dao.account.AccountManageDAO;
import com.perfect.dao.account.SystemAccountDAO;
import com.perfect.dao.sys.SystemUserDAO;
import com.perfect.dto.account.AccountReportDTO;
import com.perfect.dto.baidu.AccountAllStateDTO;
import com.perfect.dto.baidu.BaiduAccountInfoDTO;
import com.perfect.dto.sys.ModuleAccountInfoDTO;
import com.perfect.dto.sys.SystemUserDTO;
import com.perfect.service.AccountManageService;
import com.perfect.utils.DateUtils;
import com.perfect.utils.MD5;
import com.perfect.utils.ObjectUtils;
import com.perfect.utils.json.JSONUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.perfect.commons.constants.PasswordSalts.USER_SALT;

/**
 * Created by baizz on 2014-8-21.
 * 2014-12-2 refactor
 */
@Service("accountManageService")
public class AccountManageServiceImpl implements AccountManageService {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Resource
    private AccountManageDAO accountManageDAO;

    @Resource
    private SystemUserDAO systemUserDAO;

    @Resource
    private SystemAccountDAO systemAccountDAO;

    private final MD5.Builder md5Builder = new MD5.Builder();


    @Override
    public int updatePwd(String userName, String newPassword) {
        SystemUserDTO systemUserDTO = new SystemUserDTO();
        systemUserDTO.setUserName(userName);
        systemUserDTO.setPassword(md5Builder.source(newPassword).salt(USER_SALT).build().getMD5());

        boolean writeResult = systemAccountDAO.updateSystemUserInfo(systemUserDTO);
        if (writeResult) {
            return 1;
        } else {
            return -1;
        }

    }

    @Override
    public int JudgePwd(String userName, String password) {
        SystemUserDTO systemUserDTO = systemAccountDAO.findByUserName(userName);

        MD5 md5 = md5Builder.source(password).salt(USER_SALT).build();
        if (md5.getMD5().equals(systemUserDTO.getPassword())) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public List<SystemUserDTO> getAccount() {
        List<SystemUserDTO> entities = accountManageDAO.getAccount();
        return entities;
    }

    @Override
    public int auditAccount(String userName) {
        int flagStruts = accountManageDAO.updateAccountStruts(userName);
        return flagStruts;
    }

    @Override
    public List<AccountAllStateDTO> getAccountAll() {
        List<SystemUserDTO> systemUserDTOList = accountManageDAO.getAccountAll();
        List<SystemUserDTO> systemUserDTOs = ObjectUtils.convertToList(systemUserDTOList, SystemUserDTO.class);
        List<AccountAllStateDTO> allStates = new ArrayList<>();

        for (SystemUserDTO systemUserDTO : systemUserDTOs) {
            if (systemUserDTO.getUserName() == null || systemUserDTO.getUserName().equals("administrator")) {
                continue;
            }

            AccountAllStateDTO accountAllState = new AccountAllStateDTO();
            accountAllState.setUserName(systemUserDTO.getUserName());
            accountAllState.setUserState(systemUserDTO.getState());
            accountAllState.setAccountState(systemUserDTO.getAccountState());
            allStates.add(accountAllState);
            /*if (systemUserDTO.getBaiduAccounts() != null && systemUserDTO.getBaiduAccounts().size() > 0) {
                for (BaiduAccountInfoDTO dto : systemUserDTO.getBaiduAccounts()) {
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
            }*/

        }
        return allStates;
    }

    @Override
    public int updateAccountAllState(String userName, Long baiduId, Long state) {
        int i = 0;

        try {
            String moduleAccountName = systemUserDAO.findByAid(baiduId)
                    .getSystemUserModules()
                    .stream()
                    .filter(o -> Objects.equals(SystemNameConstant.SOUKE_SYSTEM_NAME, o.getModuleName()))
                    .findFirst()
                    .get()
                    .getAccounts()
                    .stream()
                    .filter(o -> Objects.equals(baiduId, o.getBaiduAccountId()))
                    .findFirst()
                    .get()
                    .getBaiduUserName();


            boolean writeResult = accountManageDAO.updateBaiduAccountStatus(userName, moduleAccountName, state);
            if (writeResult) {
                i = 1;
            }
        } catch (NullPointerException e) {
            return -1;
        }

        return i;
    }

    @Override
    public int updateSystemAccount(String userName, Long state) {
        int i = 0;
        boolean writeResult = accountManageDAO.updateSysAccount(userName, state);
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
    public Map<String, Object> getAllBaiduAccount(String currSystemUserName) {
        List<ModuleAccountInfoDTO> list = accountManageDAO.getBaiduAccountItems(currSystemUserName);
        return JSONUtils.getJsonMapData(list);
    }

    @Override
    public List<ModuleAccountInfoDTO> getAllBaiduAccount() {
        List<ModuleAccountInfoDTO> baiduAccountList = new ArrayList<>();
        List<SystemUserDTO> userDTOList = accountManageDAO.getAllSysUserAccount();
        userDTOList.stream().filter(o -> o.getSystemUserModules() != null).forEach(e -> {
            if (!e.getSystemUserModules().isEmpty()) {
                e.getSystemUserModules().forEach((systemUserModuleDTO -> {
                    if (systemUserModuleDTO.getAccounts() != null && !systemUserModuleDTO.getAccounts().isEmpty()) {
                        baiduAccountList.addAll(systemUserModuleDTO.getAccounts());
                    }
                }));
            }
        });
        return baiduAccountList;
    }

    public Map<String, Object> getBaiduAccountInfoByUserId(Long baiduUserId) {
        ModuleAccountInfoDTO dto = accountManageDAO.findByBaiduUserId(baiduUserId);
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

    public ModuleAccountInfoDTO getBaiduAccountInfoById(Long baiduUserId) {
//        return accountManageDAO.findByBaiduUserId(baiduUserId);
        return systemAccountDAO.findByModuleAccountId(baiduUserId);
    }

    public void updateBaiduAccount(BaiduAccountInfoDTO baiduAccountInfoDTO) {
        accountManageDAO.updateBaiduAccountInfo(baiduAccountInfoDTO);
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> getAccountReports(int number) {
        List<Date> dates = (List<Date>) DateUtils.getsLatestAnyDays("yyyy-MM-dd", number).get(DateUtils.KEY_DATE);
        List<AccountReportDTO> list = new ArrayList<>(accountManageDAO.getAccountReports(dates));

        List<String> dateStrList = DateUtils.getsLatestAnyDays("yyyy-MM-dd", 7).get(DateUtils.KEY_STRING);

        Map<String, String> accountReportDateStrMap = new HashMap<>();
        Map<String, AccountReportDTO> accountReportMap = new HashMap<>();

        for (int i = 0, s = list.size(); i < s; i++) {
            String dateStr = DATE_FORMAT.format(list.get(i).getDate());
            accountReportDateStrMap.put(dateStr, dateStr);
            accountReportMap.put(dateStr, list.get(i));
        }

        list.clear();

        String accountName = "";
        for (int i = 0, s = dateStrList.size(); i < s; i++) {
            if (accountReportDateStrMap.containsKey(dateStrList.get(i))) {
                list.add(accountReportMap.get(dateStrList.get(i)));
                accountName = list.get(i).getAccountName();
            } else {
                AccountReportDTO accountReportDTO = new AccountReportDTO();
                try {
                    accountReportDTO.setDate(DATE_FORMAT.parse(dateStrList.get(i)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                accountReportDTO.setAccountName(accountName);
                accountReportDTO.setCount(AppContext.getAccountId());
                accountReportDTO.setPcClick(0);
                accountReportDTO.setPcConversion(0.0);
                accountReportDTO.setPcCost(BigDecimal.ZERO);
                accountReportDTO.setPcCpm(BigDecimal.ZERO);
                accountReportDTO.setPcCtr(0.0);
                accountReportDTO.setMobileClick(0);
                accountReportDTO.setMobileConversion(0.0);
                accountReportDTO.setMobileCost(BigDecimal.ZERO);
                accountReportDTO.setMobileCpm(BigDecimal.ZERO);
                accountReportDTO.setMobileCtr(0.0);
                list.add(accountReportDTO);
            }
        }

        Map<String, Object> values = JSONUtils.getJsonMapData(list);
        values.put("dates", JSONUtils.getJsonObjectArray(dateStrList));
        return values;
    }

    public Double getYesterdayCost(Long accountId) {
        return accountManageDAO.getYesterdayCost(accountId);
    }

    @Override
    public ModuleAccountInfoDTO findByBaiduUserId(Long baiduUserId) {
        return accountManageDAO.findByBaiduUserId(baiduUserId);
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