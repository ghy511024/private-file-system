package com.bj58.ctf2021.privatefilesystem.controller.web;

import com.bj58.ctf2021.privatefilesystem.biz.service.PrivateFileBizService;
import com.bj58.ctf2021.privatefilesystem.common.config.FileSystemAutoConfiguration;
import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizException;
import com.bj58.ctf2021.privatefilesystem.common.utils.client.ctf.CtfApiClient;
import com.bj58.ctf2021.privatefilesystem.controller.converter.HttpHeaderConverter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * 默认控制器
 *
 * @author me
 * @date 2021/4/1
 */
@Controller
public class DefaultController {
    /**
     * 本次题目的任务文件id
     */
    private static final Long TASK_FILE_ID = 1001L;

    private static final String COOKIE_USER_NAME = "userName";

    @Resource
    private CtfApiClient ctfApiClient;

    @Resource
    private FileSystemAutoConfiguration fileSystemAutoConfiguration;

    @Resource
    private PrivateFileBizService privateFileBizService;

    /**
     * 文件展示页面
     */
    @GetMapping("/")
    public String index(HttpServletRequest httpRequest, HttpServletResponse response, Model model) {
        String userName;
        try {
            // 获取用户登录信息
            Cookie[] cookies = HttpHeaderConverter.getCookies((httpRequest));
            List<String> cookieList = HttpHeaderConverter.getCookieList(cookies);
            userName = ctfApiClient.getUserInfo(cookieList);
            setUserCookie(response, userName);
            // 设置页面模板中的动态信息
            model.addAttribute("userName", userName);
            model.addAttribute("downloadUrl", privateFileBizService.getDownloadUrl(TASK_FILE_ID,
                    updateExistsCookieWithUserName(cookies, userName)));
            model.addAttribute("sysDomain", fileSystemAutoConfiguration.getSystemDomain());
        } catch (BizException e) {
            if (e.getErrorCode() == AppErrorCode.NEED_LOGIN) {
                clearUserCookie(response);
                return "redirect:http://ctf.58corp.com/";
            }
        }
        return "index.html";
    }

    /**
     * 更新/添加当前已有cookie中的用户名
     */
    private Cookie[] updateExistsCookieWithUserName(Cookie[] cookies, String userName) {
        return Stream.concat(Stream.of(new Cookie(COOKIE_USER_NAME, userName)),
                Arrays.stream(cookies).filter(e -> !StringUtils.equals(StringUtils.trim(e.getName()), COOKIE_USER_NAME)))
                .toArray(Cookie[]::new);
    }

    private void setUserCookie(HttpServletResponse response, String userName) {
        Cookie cookie = new Cookie(COOKIE_USER_NAME, userName);
        response.addCookie(cookie);
    }

    private void clearUserCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie(COOKIE_USER_NAME, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
