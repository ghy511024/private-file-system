package com.bj58.ctf2021.privatefilesystem.common.enums.code;


import com.bj58.ctf2021.privatefilesystem.common.exception.ErrorLevel;
import com.bj58.ctf2021.privatefilesystem.common.exception.IErrorCodeEnum;

import static com.bj58.ctf2021.privatefilesystem.common.exception.ErrorLevel.BIZ;
import static com.bj58.ctf2021.privatefilesystem.common.exception.ErrorLevel.SYS;

/**
 * 应用错误代码
 *
 * @author me
 * @date 2021/4/1
 */
public enum AppErrorCode implements IErrorCodeEnum {

    /**
     * private-file-system 错误码预分配号段：121100~121999
     */
    SYSTEM_ERROR(121100, "系统异常", SYS),

    INVALID_ARGUMENT(121101, "参数错误", BIZ),

    FILE_NOT_EXISTS(121102, "文件不存在", BIZ),

    FILE_NOT_UPLOADER_AUTH(121103, "您非上传者或无权限下载此文件", BIZ),

    INVALID_SIGN(121104, "授权参数不合法", BIZ),

    SIGN_AUTH_FAILED(121105, "授权校验失败", BIZ),

    NEED_LOGIN(121106, "请先登录", BIZ),

    API_INVOKE_HTTP_ERROR(121107, "Api调用发生http异常", BIZ),

    API_INVOKE_BIZ_ERROR(121108, "Api调用发生业务异常", BIZ),

    API_DATA_ERROR(121109, "获取接口数据异常", BIZ),

    DOWNLOAD_FAILED(121110, "文件下载异常", BIZ),


    ;


    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误描述
     */
    private final String msg;

    /**
     * 错误级别
     */
    private final ErrorLevel errorLevel;


    AppErrorCode(int code, String msg, ErrorLevel errorLevel) {
        this.code = code;
        this.msg = msg;
        this.errorLevel = errorLevel;
    }

    public static AppErrorCode getEnum(int curCode) {
        for (AppErrorCode appErrorCode : AppErrorCode.values()) {
            if (curCode == appErrorCode.getCode()) {
                return appErrorCode;
            }
        }
        return null;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public ErrorLevel getErrorLevel() {
        return errorLevel;
    }

    @Override
    public String toString() {
        return "AppErrorCode{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", errorLevel=" + errorLevel +
                '}';
    }
}
