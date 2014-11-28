package com.perfect;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by baizz on 2014-7-6.
 */
public class MD5Utils {

    private String md5;

    private MD5Utils(String password, Object salt) {
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
            return null;
        }
    }

    public String getMD5() {
        return md5;
    }

    public String toString() {
        return "MD5{" +
                "md5='" + md5 + '\'' +
                '}';
    }

    public static class Builder {

        private String password;
        private String salt;

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder salt(String salt) {
            this.salt = salt;
            return this;
        }

        public MD5Utils build() {
            return new MD5Utils(password, salt);
        }
    }
}
