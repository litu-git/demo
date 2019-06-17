package com.example.demo.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 自定义String工具类
 *
 * @Author: litu[li_tu@suixingpay.com]
 * @Date: 17:49 2019/5/20
 **/
@Slf4j
public final class CommonStringUtils {

    private CommonStringUtils() {
    }

    /**
     * 根据Unicode编码完美的判断中文汉字和符号
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        return (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION);
    }

    /**
     * 截取固定长度的字符串，汉字为两个字符，字母为一个字符
     *
     * @param orginString 元字符串
     * @param length      截取长度
     * @return
     */
    public static String getSubStringByLength(String orginString, int length) {
        log.info("orginString:" + orginString + "-length:" + length);
        StringBuilder newString = new StringBuilder();
        if (StringUtils.isEmpty(orginString)) {
            return "";
        }
        // 如果字符串的长度小于等于所需长度的一半，直接返回该字符串
        if (orginString.length() <= length / 2) {
            return orginString;
        }
        int newLength = 0;
        char[] orginStringChar = orginString.toCharArray();
        for (char c : orginStringChar) {
            if (CommonStringUtils.isChinese(c)) {
                // 汉字长度+2
                newLength = newLength + 2;
            } else {
                // 不是汉字长度+1
                newLength = newLength + 1;
            }
            if (newLength > length) {
                break;
            }
            newString.append(c);
        }
        return newString.toString();
    }

    /**
     * 获取字符串的长度，汉字为两个字符，字母为一个字符
     *
     * @param orginString
     * @return
     */
    public static int getStringLength(String orginString) {
        int newLength = 0;
        if (StringUtils.isEmpty(orginString)) {
            return 0;
        }
        char[] orginStringChar = orginString.toCharArray();
        for (char c : orginStringChar) {
            if (CommonStringUtils.isChinese(c)) {
                // 汉字长度+2
                newLength = newLength + 2;
            } else {
                // 不是汉字长度+1
                newLength = newLength + 1;
            }
        }
        return newLength;
    }

    /**
     * 密码格式校验，请输入密码长度为6-18位，且数字加字母组合
     *
     * @param pwd
     * @return
     */
    public static boolean validatePwd(String pwd) {
        if (StringUtils.isEmpty(pwd)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,18}$");
        Matcher m = pattern.matcher(pwd);
        return m.matches();
    }


    /**
     * 生成指定长度的随机字符串
     *
     * @param length 指定字符串长度
     * @return
     */
    public static String generateLenString(int length) {
        char[] cResult = new char[length];
        int[] flag = { 0, 0, 0 }; // A-Z, a-z, 0-9
        int i = 0;
        while (flag[0] == 0 || flag[1] == 0 || flag[2] == 0 || i < length) {
            i = i % length;
            int f = (int) (new SecureRandom().nextDouble() * 3 % 3);
            if (f == 0) {
                cResult[i] = (char) ('A' + new SecureRandom().nextDouble() * 26);
            } else if (f == 1) {
                cResult[i] = (char) ('a' + new SecureRandom().nextDouble() * 26);
            } else {
                cResult[i] = (char) ('0' + new SecureRandom().nextDouble() * 10);
            }
            flag[f] = 1;
            i++;
        }
        return new String(cResult);
    }

}
