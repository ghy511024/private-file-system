package com.bj58.ctf2021.privatefilesystem.controller.converter;

import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizExceptionBuilder;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * http请求头转换器
 *
 * @author me
 * @date 2021/4/1
 */
public class HttpHeaderConverter {

    private HttpHeaderConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static List<String> getCookieList(HttpServletRequest httpRequest) {
        List<String> cookieList = Lists.newArrayList();
        return getCookieList(getCookies(httpRequest));
    }

    public static List<String> getCookieList(Cookie[] cookies) {
        List<String> cookieList = Lists.newArrayList();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                cookieList.add(cookie.getName() + "=" + cookie.getValue());
            }
        }
        return cookieList;
    }

    public static String getUserName(HttpServletRequest httpRequest) {
        return getUserName(getCookies(httpRequest));
    }

    public static String getUserName(Cookie[] cookies) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (StringUtils.equals(cookie.getName(), "userName")) {
                    return cookie.getValue();
                }
            }
        }
        throw BizExceptionBuilder.build(AppErrorCode.NEED_LOGIN);
    }

    public static Cookie[] getCookies(HttpServletRequest httpRequest) {
        if (httpRequest == null) {
            throw BizExceptionBuilder.build(AppErrorCode.NEED_LOGIN);
        }
        Cookie[] cookies = httpRequest.getCookies();
        if (cookies == null || cookies.length == 0) {
            throw BizExceptionBuilder.build(AppErrorCode.NEED_LOGIN);
        }
        return cookies;
    }

}
