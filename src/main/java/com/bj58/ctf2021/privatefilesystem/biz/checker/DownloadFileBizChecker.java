package com.bj58.ctf2021.privatefilesystem.biz.checker;

import com.bj58.ctf2021.privatefilesystem.common.config.FileSystemAutoConfiguration;
import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizExceptionBuilder;
import com.bj58.ctf2021.privatefilesystem.common.utils.aes.AesUtils;
import com.bj58.ctf2021.privatefilesystem.controller.converter.HttpHeaderConverter;
import com.bj58.ctf2021.privatefilesystem.core.model.file.FileInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

/**
 * 下载文件业务校验器
 *
 * @author me
 * @date 2021/4/1
 */
@Service
public class DownloadFileBizChecker {

    @Resource
    private AuthBizChecker authBizChecker;

    @Resource
    private FileSystemAutoConfiguration fileSystemAutoConfiguration;


    public void checkCouldDownloadFile(FileInfoDO fileInfoDO, Cookie[] cookies, String sign, String random) {
        // 1.检查文件是否存在
        if (fileInfoDO == null) {
            throw BizExceptionBuilder.build(AppErrorCode.FILE_NOT_EXISTS);
        }
        // 2.检查文件下载签名是否正确
        String decryptData = AesUtils.decrypt(fileSystemAutoConfiguration.getAesEncryptKey(), random, sign);
        if (decryptData == null) {
            throw BizExceptionBuilder.build(AppErrorCode.INVALID_SIGN);
        }
        String userName = HttpHeaderConverter.getUserName(cookies);
        if (!StringUtils.equals(StringUtils.trim(decryptData), fileInfoDO.buildSignPlainData(userName,
                fileSystemAutoConfiguration.getSplitString()))) {
            throw BizExceptionBuilder.build(AppErrorCode.SIGN_AUTH_FAILED);
        }
        // 3.检查用户是否登录
        authBizChecker.isUserLogin(cookies, userName);
    }

}
