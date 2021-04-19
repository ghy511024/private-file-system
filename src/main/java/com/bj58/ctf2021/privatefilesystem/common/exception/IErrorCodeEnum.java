package com.bj58.ctf2021.privatefilesystem.common.exception;

/**
 * 错误码接口类
 *
 * @author me
 * @date 2021/4/1
 */
public interface IErrorCodeEnum {
    /**
     * 获取错误码
     *
     * @return 应用错误码
     */
    int getCode();

    /**
     * 获取错误描述
     *
     * @return 错误描述
     */
    String getMsg();

    /**
     * 获取错误级别
     *
     * @return {@link ErrorLevel}
     */
    ErrorLevel getErrorLevel();
}
