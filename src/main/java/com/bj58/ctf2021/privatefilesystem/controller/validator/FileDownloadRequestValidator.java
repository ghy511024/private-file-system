package com.bj58.ctf2021.privatefilesystem.controller.validator;

import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.utils.asserts.AssertUtils;
import com.bj58.ctf2021.privatefilesystem.controller.entity.request.file.FileDownloadRequest;
import org.apache.commons.lang3.StringUtils;


/**
 * 文件下载请求参数校验器
 *
 * @author me
 * @date 2021/4/1
 */
public class FileDownloadRequestValidator {

    private FileDownloadRequestValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validate(FileDownloadRequest request) {
        AssertUtils.assertNotNull(request, AppErrorCode.INVALID_ARGUMENT, "request不可为null");
        AssertUtils.assertTrue(request.getId() != null && request.getId() > 0,
                AppErrorCode.INVALID_ARGUMENT, "参数id不合法");
        AssertUtils.assertTrue(StringUtils.isNotBlank(request.getR())
                        && StringUtils.startsWith(request.getR(), "version-"),
                AppErrorCode.INVALID_ARGUMENT, "参数r不合法");
        AssertUtils.assertTrue(StringUtils.isNotBlank(request.getSign()),
                AppErrorCode.INVALID_ARGUMENT, "参数sign不合法");
    }

}
