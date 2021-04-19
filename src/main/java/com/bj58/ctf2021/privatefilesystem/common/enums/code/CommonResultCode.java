package com.bj58.ctf2021.privatefilesystem.common.enums.code;

/**
 * 公共返回码
 *
 * @author me
 * @date 2021/4/1
 */
public enum CommonResultCode {
    /**
     * 请求成功默认返回码
     */
    SUCCESS(200, "success"),
    ;

    public final int code;
    public final String msg;

    CommonResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
