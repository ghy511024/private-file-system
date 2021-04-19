package com.bj58.ctf2021.privatefilesystem.common.utils.asserts;

import com.bj58.ctf2021.privatefilesystem.common.exception.BizException;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizExceptionBuilder;
import com.bj58.ctf2021.privatefilesystem.common.exception.IErrorCodeEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 断言工具类
 *
 * @author me
 * @date 2021/4/1
 */
public class AssertUtils {


    /**
     * 当前条件是否为true, 不为true,则抛出异常e
     *
     * @param condition 判断的条件
     * @param e         condition为false,需要抛出的异常
     */
    public static void assertTrue(boolean condition, BizException e) {
        if (!condition) {
            throw e;
        }
    }

    /**
     * 当前条件是否为true, 不为true,则抛出异常
     *
     * @param condition 判断的条件
     * @param errorCode condition为false,需要抛出的异常的错误码
     */
    public static void assertTrue(boolean condition, IErrorCodeEnum errorCode) {
        if (!condition) {
            throw BizExceptionBuilder.build(errorCode);
        }
    }

    /**
     * 当前条件是否为true, 不为true,则抛出异常
     *
     * @param condition 判断的条件
     * @param errorCode condition为false,需要抛出的异常的错误码
     * @param msg       错误信息
     */
    public static void assertTrue(boolean condition, IErrorCodeEnum errorCode, String msg) {
        if (!condition) {
            throw BizExceptionBuilder.build(errorCode, msg);
        }
    }

    /**
     * 当前对象不可为空,否则抛出异常e
     *
     * @param target 需要判断的对象
     * @param e      为空时,抛出的异常
     */
    public static void assertNotNull(Object target, BizException e) {
        if (target == null) {
            throw e;
        }
    }

    /**
     * 当前对象不可为空,否则抛出异常
     *
     * @param target    需要判断的对象
     * @param errorCode 为空时,抛出的异常的错误码
     */
    public static void assertNotNull(Object target, IErrorCodeEnum errorCode) {
        if (target == null) {
            throw BizExceptionBuilder.build(errorCode);
        }
    }

    /**
     * 前对象不可为空,否则抛出异常
     *
     * @param target    需要判断的对象
     * @param errorCode 为空时,抛出的异常的错误码
     * @param msg       错误信息
     */
    public static void assertNotNull(Object target, IErrorCodeEnum errorCode, String msg) {
        if (target == null) {
            throw BizExceptionBuilder.build(errorCode, msg);
        }
    }

    /**
     * 字符串不能为空(包括不能为null和空格),否则抛出异常
     *
     * @param str 需要判断的字符串
     * @param e   为空时,抛出的异常
     */
    public static void assertNotBlank(String str, BizException e) {
        if (org.apache.commons.lang3.StringUtils.isBlank(str)) {
            throw e;
        }
    }

    /**
     * 字符串不能为空(包括不能为null和空格),否则抛出异常
     *
     * @param str       需要判断的字符串
     * @param errorCode 为空时,抛出的异常的错误码
     */
    public static void assertNotBlank(String str, IErrorCodeEnum errorCode) {
        if (StringUtils.isBlank(str)) {
            throw BizExceptionBuilder.build(errorCode);
        }
    }

    /**
     * 字符串不能为空(包括不能为null和空格),否则抛出异常
     *
     * @param str       需要判断的字符串
     * @param errorCode 为空时,抛出的异常的错误码
     * @param msg       错误信息
     */
    public static void assertNotBlank(String str, IErrorCodeEnum errorCode, String msg) {
        if (StringUtils.isBlank(str)) {
            throw BizExceptionBuilder.build(errorCode, msg);
        }
    }
}
