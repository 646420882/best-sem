package com.perfect.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created on 2015-12-30.
 * <p>验证码工具类
 *
 * @author dolphineor
 */
public class CaptchaUtils {

    public static String getRandom(int codeCount) {
        StringBuffer randomCodeRes = new StringBuffer();

        char[] codeSequenceNumber = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] codeSequenceChar = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'
        };

        List<String> randomCode = new ArrayList<>();

        // 创建一个随机数生成器类
        Random random = new Random();

        // 随机产生, 验证码由几个数字、几个字母组成
        int shuziNum = random.nextInt(5) + 1;
        int charNum = codeCount - shuziNum;

        // 随机产生codeCount数字的验证码
        for (int i = 0; i < shuziNum; i++) {
            // 得到随机产生的验证码数字
            String numRand = String.valueOf(codeSequenceNumber[random.nextInt(codeSequenceNumber.length)]);
            // 将产生的六个随机数组合在一起
            randomCode.add(numRand);
        }
        for (int i = 0; i < charNum; i++) {
            // 得到随机产生的验证码字母
            String strRand = String.valueOf(codeSequenceChar[random.nextInt(codeSequenceChar.length)]);
            // 将产生的六个随机数组合在一起
            randomCode.add(strRand);
        }

        Collections.shuffle(randomCode);

        for (int i = 0, s = randomCode.size(); i < s; i++) {
            randomCodeRes.append(randomCode.get(i));
        }

        return randomCodeRes.toString();
    }
}
