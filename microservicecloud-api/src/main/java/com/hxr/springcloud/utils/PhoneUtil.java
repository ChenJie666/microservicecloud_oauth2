package com.hxr.springcloud.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {

    /**
     * 通过正则表达式判断手机号是否符合要求
     */
    private static String regex = "^(13[0-9]|14[5-9]|15[0-3,5-9]|16[2,5,6,7]|17[0-8]|18[0-9]|19[0-3,5-9])\\d{8}$";
    private static Pattern P = Pattern.compile(regex);

    /**
     * 校验手机号是否有效
     *
     * @return boolean
     */
    public static boolean checkPhone(String phone){
        if (phone == null || phone.length() != 11) {
            return Boolean.FALSE;
        }

        Matcher m = P.matcher(phone);
        return m.matches();
    }

}
