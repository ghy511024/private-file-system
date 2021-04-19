package com.bj58.ctf2021.privatefilesystem.common.exception;

/**
 * 业务异常构造器
 *
 * @author me
 * @date 2021/4/1
 */
public class BizExceptionBuilder {
    /**
     * 构造业务异常
     *
     * @param errorCode 错误码枚举
     * @return 默认错误描述的业务异常
     */
    public static BizException build(IErrorCodeEnum errorCode) {
        return new BizException(errorCode);
    }

    /**
     * 构造业务异常
     *
     * @param errorCode 错误码枚举
     * @param msg       自定义错误描述
     * @return 自定义错误描述的业务异常
     */
    public static BizException build(IErrorCodeEnum errorCode, String msg) {
        return new BizException(errorCode, msg);
    }

}
