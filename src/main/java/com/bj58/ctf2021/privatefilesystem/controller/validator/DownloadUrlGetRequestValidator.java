package com.bj58.ctf2021.privatefilesystem.controller.validator;

import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.utils.asserts.AssertUtils;
import com.bj58.ctf2021.privatefilesystem.controller.entity.request.file.DownloadUrlGetRequest;


/**
 * 文件下载url获取请求参数校验器
 *
 * @author me
 * @date 2021/4/1
 */
public class DownloadUrlGetRequestValidator {

    private DownloadUrlGetRequestValidator() {
        throw new IllegalStateException("Utility class");
    }

    public static void validate(DownloadUrlGetRequest request) {
        AssertUtils.assertNotNull(request, AppErrorCode.INVALID_ARGUMENT, "request不可为null");

        AssertUtils.assertTrue(request.getFileId() != null && request.getFileId() > 0,
                AppErrorCode.INVALID_ARGUMENT, "文件id不合法");

    }

}
