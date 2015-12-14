package com.perfect.service;

import com.perfect.account.BaseBaiduAccountInfoVO;
import com.perfect.account.SystemUserInfoVO;
import com.perfect.commons.constants.AuthConstants;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.List;

/**
 * Created on 2015-12-07.
 *
 * @author dolphineor
 */
public interface SystemUserInfoService extends AuthConstants {

    // 获取全部系统用户的请求地址
    String RETRIEVE_ALL_SYSTEM_USER_URL = String.format(BASE_VERIFICATION_URL, "/users/findAllSystemUserAccount");

    // 根据用户名获取指定系统用户的请求地址
    String RETRIEVE_SPECIFIC_SYSTEM_USER_BY_USERNAME_URL = String.format(BASE_VERIFICATION_URL, "/users/findSystemUserInfoByUserName");

    // 根据凤巢帐号ID获取系统用户的请求地址
    String RETRIEVE_SPECIFIC_SYSTEM_USER_BY_BAIDU_ACCOUNT_ID_URL = String.format(BASE_VERIFICATION_URL, "/users/findSystemUserInfoByBaiduAccountId");

    String PARAM_SERVICE_TOKEN = "ServicerToken";

    String PASSWORD_CRYPT_KEY = "perfect-";

    /**
     * <p>获取全部系统用户
     *
     * @return
     */
    List<SystemUserInfoVO> findAllSystemUserAccount();

    /**
     * <p>根据系统用户名查询该用户的全部信息
     *
     * @param username 系统用户名
     * @return
     */
    SystemUserInfoVO findSystemUserInfoByUserName(String username);

    /**
     * <p>根据百度ID查询系统用户
     *
     * @param baiduUserId
     * @return
     */
    SystemUserInfoVO findSystemUserInfoByBaiduAccountId(Long baiduUserId);

    /**
     * <p>在当前登录用户中, 查询指定百度帐号的基础信息{@link com.perfect.account.BaseBaiduAccountInfoVO}.
     *
     * @param baiduUserId 百度账号ID
     * @return
     */
    BaseBaiduAccountInfoVO findByBaiduUserId(Long baiduUserId);

    /**
     * <p>根据系统用户名查询该用户下的所有百度帐号信息
     *
     * @param username 系统用户名
     * @return
     */
    List<BaseBaiduAccountInfoVO> findBaiduAccountsByUserName(String username);

    /**
     * <p>查询系统中所有的百度帐号
     *
     * @return
     */
    List<BaseBaiduAccountInfoVO> findAllBaiduAccounts();


    /**
     * <p>JSON数据解密
     *
     * @param message
     * @param key
     * @return
     * @throws Exception
     */
    default String decrypt(String message, String key) throws Exception {
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] retByte = cipher.doFinal(new BASE64Decoder().decodeBuffer(message));

        return new String(retByte);
    }
}
