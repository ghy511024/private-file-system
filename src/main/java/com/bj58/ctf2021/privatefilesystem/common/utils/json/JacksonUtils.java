package com.bj58.ctf2021.privatefilesystem.common.utils.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * Json工具类
 *
 * @author me
 * @date 2021/4/1
 */
public class JacksonUtils {

    @Getter
    static ObjectMapper mapper = new ObjectMapper();

    static {
        //解析时忽略未知的字段继续完成解析
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }


    /**
     * 对象转json字符串
     *
     * @param o 对象
     * @return json字符串
     */
    @SneakyThrows
    public static String toJson(Object o) {
        return mapper.writeValueAsString(o);
    }

    /**
     * json字符串转对象
     *
     * @param json json字符串
     * @param <T>  目标类型
     */
    @SneakyThrows
    public static <T> T toObj(String json, Class<T> t) {
        if (t == String.class) {
            return (T) json;
        }
        return mapper.readValue(json, t);
    }

    /**
     * json字符串转对象
     *
     * @param json json字符串
     * @param type 目标类型
     */
    @SneakyThrows
    public static <T> T toObj(String json, Type type) {
        if (type == String.class) {
            return (T) json;
        }
        return mapper.readValue(json, mapper.constructType(type));
    }

    /**
     * json字符串转对象
     *
     * @param json json字符串
     * @param ref  目标类型
     */
    @SneakyThrows
    public static <T> T toObj(String json, TypeReference<T> ref) {
        if (String.class.getTypeName().equals(ref.getType().getTypeName())) {
            return (T) json;
        }
        return mapper.readValue(json, ref);
    }

    /**
     * json输入流转对象
     *
     * @param inputStream json字符输入流
     * @param <T>         目标类型
     */
    @SneakyThrows
    public static <T> T toObj(InputStream inputStream, Class<T> t) {
        if (t == String.class) {
            return (T) streamToString(inputStream);
        }
        return mapper.readValue(inputStream, t);
    }

    /**
     * json输入流转对象
     *
     * @param inputStream json字符输入流
     * @param type        目标类型
     */
    @SneakyThrows
    public static <T> T toObj(InputStream inputStream, Type type) {
        if (type == String.class) {
            return (T) streamToString(inputStream);
        }
        return mapper.readValue(inputStream, mapper.constructType(type));
    }

    /**
     * json输入流转对象
     *
     * @param inputStream json字符输入流
     * @param ref         目标类型
     */
    @SneakyThrows
    public static <T> T toObj(InputStream inputStream, TypeReference<T> ref) {
        if (String.class.getTypeName().equals(ref.getType().getTypeName())) {
            return (T) streamToString(inputStream);
        }
        return mapper.readValue(inputStream, ref);
    }

    private static String streamToString(InputStream inputStream) {
        BufferedInputStream bis = new BufferedInputStream(inputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        int result;
        try {
            result = bis.read();
            while (result != -1) {
                buf.write((byte) result);
                result = bis.read();
            }
            return buf.toString("UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

}
