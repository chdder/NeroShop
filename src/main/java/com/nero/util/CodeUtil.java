package com.nero.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 2019/6/1 0001
 * Time: 16:01
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        String codeExpected = (String) request.getSession()
                .getAttribute(Constants.KAPTCHA_SESSION_KEY);
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        System.out.println(shopStr);
        System.out.println(codeExpected);
        System.out.println(verifyCodeActual);
        if (verifyCodeActual == null || !verifyCodeActual.equals(codeExpected)) {
            return false;
        }
        return true;
    }
}
