package com.bj58.ctf2021.privatefilesystem.common.utils.client.ctf.impl;

import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.enums.code.CommonResultCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizExceptionBuilder;
import com.bj58.ctf2021.privatefilesystem.common.utils.client.AbstractWebApiClientImpl;
import com.bj58.ctf2021.privatefilesystem.common.utils.client.ctf.CtfApiClient;
import com.bj58.ctf2021.privatefilesystem.common.utils.log.LoggerUtil;
import com.bj58.ctf2021.privatefilesystem.controller.entity.response.PlainResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CTF接口客户端实现
 *
 * @author me
 * @date 2021/4/1
 */
@Component
public class CtfApiClientImpl extends AbstractWebApiClientImpl implements CtfApiClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CtfApiClientImpl.class);

    @Value("${api.client.ctf.api.info}")
    private String infoApi;

    @Value("${api.client.ctf.api.flag}")
    private String flagApi;

    public CtfApiClientImpl(RestTemplateBuilder restTemplateBuilder) {
        super(restTemplateBuilder);
    }

    @Override
    public String getUserInfo(List<String> cookieList) {
        String url = String.format("%s/%s", apiDomain, infoApi);
        // 透传cookie
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put("Cookie", cookieList);
        // 请求接口
        ResponseEntity<String> res;
        try {
            res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, requestHeaders), String.class);
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "用户信息获取出错", e, cookieList);
            throw BizExceptionBuilder.build(AppErrorCode.API_INVOKE_HTTP_ERROR);
        }
        // 校验响应
        checkWebApiResponse(res);
        PlainResult<String> plainResult = getPlainResult(res, String.class);
        // 判断是否登录
        if (plainResult.getCode() != CommonResultCode.SUCCESS.code) {
            throw BizExceptionBuilder.build(AppErrorCode.NEED_LOGIN);
        }
        String userInfo = plainResult.getData();
        LoggerUtil.warn(LOGGER, "获取用户信息成功", userInfo);
        return userInfo;
    }

    @Override
    public String getUserFlag(List<String> cookieList) {
        String url = String.format("%s/%s", apiDomain, flagApi);
        // 透传cookie
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.put("Cookie", cookieList);
        // 请求接口
        ResponseEntity<String> res;
        try {
            res = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, requestHeaders), String.class);
        } catch (Exception e) {
            LoggerUtil.error(LOGGER, "用户flag获取出错", e, cookieList);
            throw BizExceptionBuilder.build(AppErrorCode.API_INVOKE_HTTP_ERROR);
        }
        // 校验响应
        checkWebApiResponse(res);
        PlainResult<String> plainResult = getPlainResult(res, String.class);
        // 判断是否成功
        if (plainResult.getCode() != CommonResultCode.SUCCESS.code) {
            throw BizExceptionBuilder.build(AppErrorCode.NEED_LOGIN);
        }
        String userInfo = getUserInfo(cookieList);
        LoggerUtil.warn(LOGGER, "获取用户标识成功", userInfo);
        return extractFlag(plainResult.getData());
    }

    private String extractFlag(String res) {
        String regex = "^flag\\{([\\w\\d]+)}$";
        Matcher matcher = Pattern.compile(regex).matcher(res);
        if (!matcher.lookingAt()) {
            throw BizExceptionBuilder.build(AppErrorCode.API_DATA_ERROR);
        }
        return matcher.group(1);
    }


}
