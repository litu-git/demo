package com.example.demo.utils;

import java.security.SecureRandom;
import java.util.regex.Pattern;

/**
 * @Author li_tu@suixingpay.com
 * @Date 2019-07-05 15:28
 * @Version 1.0
 */
public class NumberUtils {

    /**
     * 运营商号段如下：
     * 中国联通号码：130、131、132、145（无线上网卡）、155、156、166、185（iPhone5上市后开放）、186、176
     * （4G号段）、 175（2015年9月10日正式启用，暂只对北京、上海和广东投放办理）
     * 中国移动号码：134、135、136、137、138、139
     * 、147（无线上网卡）、150、151、152、157、158、159、182、183、187、188、178、198、199
     * 中国电信号码：133、153、180、181、189、177、173、149 虚拟运营商：170、1718、1719 手机号前3位的数字包括： 1
     * :1 2 :3,4,5,7,8 3 :0,1,2,3,4,5,6,7,8,9 总结： 目前java手机号码正则表达式有： a
     * :"^1[3|4|5|6|7|8|9][0-9]\\d{4,8}$" 一般验证情况下这个就可以了 b :
     * "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$"
     * 20180205增加联通166和移动198、199支持xu_jy
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(166)|(17[013678])|(18[0,5-9])|(19[8|9]))\\d{8}$";

    /**
     * 验证身份证号码
     *
     * @param idCard 居民身份证号码15位或18位，最后一位可能是数字或字母
     * @return 验证成功返回true，验证失败返回false
     * @Author: li_tu@suixingpay.com
     * @Date: 2019-07-10 18:00
     * @Version: 1.0
     */
    public static boolean checkIdCard(String idCard) {
        String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
        return Pattern.matches(regex, idCard);
    }

    /**
     * 校验手机号
     *
     * @param mobile 手机号
     * @return 验证成功返回true，验证失败返回false
     * @Author: li_tu@suixingpay.com
     * @Date: 2019-07-10 18:03
     * @Version: 1.0
     */
    public static boolean checkMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 数字替换,防止敏感打码
     *
     * @Author li_tu@suixingpay.com
     * @Date 2019-07-05 15:28
     * @Version 1.0
     */
    public static String enReplace(String val) {
        if (val == null || "".equals(val.trim())) {
            return val;
        }
        val = val.replace("1", "~Q");
        val = val.replace("2", "~W");
        val = val.replace("3", "~E");
        val = val.replace("4", "~R");
        val = val.replace("5", "~T");
        val = val.replace("6", "~Y");
        val = val.replace("7", "~U");
        val = val.replace("8", "~I");
        val = val.replace("9", "~O");
        val = val.replace("0", "~P");
        return val;
    }

    /**
     * 生成验证码
     *
     * @Author: li_tu@suixingpay.com
     * @Date: 2019-07-10 18:15
     * @Version: 1.0
     */
    public static String smsCode() {
        SecureRandom random = new SecureRandom();
        int x = 100000 + random.nextInt(899999);
        return String.valueOf(x);
    }
}
