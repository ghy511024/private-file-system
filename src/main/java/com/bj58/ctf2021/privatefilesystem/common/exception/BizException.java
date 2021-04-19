package com.bj58.ctf2021.privatefilesystem.common.exception;

/**
 * 业务异常类
 *
 * @author me
 * @date 2021/4/1
 */
public class BizException extends RuntimeException {
    private static final long serialVersionUID = 4205294181791791368L;
    /**
     * 错误码
     */
    private final IErrorCodeEnum errorCode;

    /**
     * 自定义的错误描述
     */
    private final String msg;

    public BizException(IErrorCodeEnum errorCode) {
        super(errorCode.getMsg());
        this.errorCode = errorCode;
        this.msg = errorCode.getMsg();
    }

    public BizException(IErrorCodeEnum errorCode, String msg) {
        super(msg);
        this.errorCode = errorCode;
        this.msg = msg;
    }

    public IErrorCodeEnum getErrorCode() {
        return errorCode;
    }


    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "BizException{" +
                "errCode=" + errorCode +
                ", msg='" + msg + '\'' +
                '}';
    }
}
