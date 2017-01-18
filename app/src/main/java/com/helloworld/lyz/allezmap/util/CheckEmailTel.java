package com.helloworld.lyz.allezmap.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created at 2017/1/11 20:24
 * 检查邮箱电话合法性
 * @Version 1.0
 * @Author paul (yangnaihua.2008at163.com)
 * @desc: CheckEmailTel
 */

public class CheckEmailTel {
    /**
     * 验证邮箱地址是否正确
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            // LOG.error("验证邮箱地址错误", e);
            flag = false;
        }

        return flag;
    }

    /**
     * 验证手机号码
     *
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        boolean flag = false;
        try {
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
//	   Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(mobiles);
            flag = m.matches();//是的话
        } catch (Exception e) {
            //  LOG.error("验证手机号码错误", e);
            flag = false;
        }
        return flag;
    }

    /**
     * 验证字符串是否是数字
     *
     * @param mobiles
     * @return
     */

    public static boolean isNum(String str) {
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");

    }
}
