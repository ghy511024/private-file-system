package com.bj58.ctf2021.privatefilesystem.biz.checker;

import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizExceptionBuilder;
import com.bj58.ctf2021.privatefilesystem.common.utils.client.ctf.CtfApiClient;
import com.bj58.ctf2021.privatefilesystem.controller.converter.HttpHeaderConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.List;

/**
 * 鉴权业务校验器
 *
 * @author me
 * @date 2021/4/1
 */
@Service
public class AuthBizChecker {

    @Resource
    private CtfApiClient ctfApiClient;

    public void isUserLogin(Cookie[] cookies, String userName) {
        List<String> cookieList = HttpHeaderConverter.getCookieList(cookies);
        String userInfo = ctfApiClient.getUserInfo(cookieList);
        if (!StringUtils.equals(userInfo, userName)) {
            throw BizExceptionBuilder.build(AppErrorCode.NEED_LOGIN);
        }
    }
}
