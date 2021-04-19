package com.bj58.ctf2021.privatefilesystem.common.exception;

/**
 * 错误级别
 *
 * @author me
 * @date 2021/4/1
 */
public enum ErrorLevel {
    /**
     * 错误级别枚举
     */
    BIZ("business", "业务级别"),

    SYS("system", "系统级别");

    /**
     * 错误级别编码
     */
    private final String code;

    /**
     * 错误级别描述
     */
    private final String desc;

    ErrorLevel(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
