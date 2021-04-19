package com.bj58.ctf2021.privatefilesystem.biz.checker;

import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizExceptionBuilder;
import com.bj58.ctf2021.privatefilesystem.controller.converter.HttpHeaderConverter;
import com.bj58.ctf2021.privatefilesystem.core.model.file.FileInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

/**
 * 下载链接业务校验器
 *
 * @author me
 * @date 2021/4/1
 */
@Service
public class DownloadUrlBizChecker {

    @Resource
    private AuthBizChecker authBizChecker;

    public void checkCouldGetDownloadUrl(FileInfoDO fileInfoDO, Cookie[] cookies) {
        // 1.检查文件是否存在
        if (fileInfoDO == null) {
            throw BizExceptionBuilder.build(AppErrorCode.FILE_NOT_EXISTS);
        }
        // 2.检查用户是否是该文件的拥有者
        String userName = HttpHeaderConverter.getUserName(cookies);
        if (StringUtils.isNotBlank(fileInfoDO.getUploader()) && !StringUtils.equals(fileInfoDO.getUploader(), userName)) {
            throw BizExceptionBuilder.build(AppErrorCode.FILE_NOT_UPLOADER_AUTH);
        }
        // 3.检查用户是否登录
        authBizChecker.isUserLogin(cookies, userName);
    }
}
