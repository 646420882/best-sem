package com.perfect.dto.baidu;

/**
 * Created by yousheng on 14/12/9.
 */
public class BaiduAccountInfoNoPasswordDTO extends BaiduAccountInfoDTO {

    @Override
    public void setBaiduPassword(String baiduPassword) {
    }

    @Override
    public String getBaiduPassword() {
        return "";
    }
}
