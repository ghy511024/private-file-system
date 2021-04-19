package com.bj58.ctf2021.privatefilesystem.controller.entity.response;

import com.bj58.ctf2021.privatefilesystem.common.enums.code.CommonResultCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.IErrorCodeEnum;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author me
 * @date 2021/4/1
 */
public class BaseResult implements Serializable {

    private static final long serialVersionUID = -1308837958361535086L;

    /**
     * 标识本次调用是否成功
     */
    private Boolean success;

    /**
     * 本次调用返回code，一般为错误代码
     */
    private Integer code;

    /**
     * 本次调用返回的消息，一般为错误消息
     */
    private String message;

    /**
     * 请求Id
     */
    private String requestId;

    public BaseResult() {
        this.code = CommonResultCode.SUCCESS.code;
        this.success = true;
        this.message = CommonResultCode.SUCCESS.msg;
    }

    /**
     * 设置错误信息
     *
     * @param code    调用返回code
     * @param message 调用返回的消息
     */
    public <R extends BaseResult> R setErrorMessage(Integer code, String message) {
        this.code = code;
        this.success = false;
        this.message = message;
        return (R) this;
    }

    public <R extends BaseResult> R setErrorMessage(IErrorCodeEnum code, Object... args) {
        this.code = code.getCode();
        this.success = false;
        this.message = String.format(code.getMsg(), args);
        return (R) this;
    }

    /**
     * 设置错误信息
     *
     * @param rc   公共返回码
     * @param args 参数
     * @return 返回结果
     * @see CommonResultCode
     */
    public <R extends BaseResult> R setError(CommonResultCode rc, Object... args) {
        this.code = rc.code;
        this.success = false;
        if (args == null || args.length == 0) {
            this.message = rc.msg;
        } else {
            this.message = String.format(rc.msg, args);
        }
        return (R) this;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

}