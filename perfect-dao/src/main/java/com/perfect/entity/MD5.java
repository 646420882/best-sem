package com.perfect.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by baizz on 2014-7-6.
 */
public class MD5 {

    private String md5;

    public MD5(String password, Object salt) {
        this.md5 = generateMD5(password + "{" + salt.toString() + "}");
    }

    private String generateMD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuilder buf = new StringBuilder("");
            for (int offset = 0, l = b.length; offset < l; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            md5 = buf.toString();
            return md5;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public String getMD5() {
        return md5;
    }
}
