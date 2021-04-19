package com.bj58.ctf2021.privatefilesystem.common.context;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统上下文
 *
 * @author me
 * @date 2021/4/1
 */
public class SystemContext {

    public static final String UUID = "uuid";

    /**
     * 外部请求参数key
     */
    public static final String OUT_REQUEST_ARGS = "outRequestArgs";

    /**
     * 是否接入日志中心标示(不受日志级别过滤影响)
     */
    public static final String TRACK_LOG_FLAG = "trackLogFlag";

    private SystemContext() {
        throw new IllegalStateException("Utility class");
    }

    private static final ThreadLocal<Map<Object, Object>> SYSTEM_CONTEXT_DATA = new ThreadLocal<>();

    /**
     * 根据key获取值
     *
     * @param key key
     * @return getCode
     */
    public static Object getValue(Object key) {
        if (SYSTEM_CONTEXT_DATA.get() == null) {
            return null;
        }
        return SYSTEM_CONTEXT_DATA.get().get(key);
    }

    /**
     * 存储
     *
     * @param key   key
     * @param value getCode
     * @return getCode
     */
    public static Object setValue(Object key, Object value) {
        Map<Object, Object> cacheMap = SYSTEM_CONTEXT_DATA.get();
        if (cacheMap == null) {
            cacheMap = new HashMap();
            SYSTEM_CONTEXT_DATA.set(cacheMap);
        }
        return cacheMap.put(key, value);
    }

    /**
     * 根据key移除值
     *
     * @param key key
     */
    public static void removeValue(Object key) {
        Map<Object, Object> cacheMap = SYSTEM_CONTEXT_DATA.get();
        if (cacheMap != null) {
            cacheMap.remove(key);
        }
    }

    /**
     * 重置
     */
    public static void clear() {
        if (SYSTEM_CONTEXT_DATA.get() != null) {
            SYSTEM_CONTEXT_DATA.get().clear();
        }
    }

    /**
     * 返回 uuid
     * uuid设置参见SystemContextFilter
     *
     * @return uuid
     */
    public static String getUuid() {
        Object uuid = getValue(UUID);
        if (uuid == null) {
            return "";
        }
        return String.valueOf(uuid);
    }

    /**
     * 返回 是否打印到日志中心
     * TRACK_LOG_FLAG 设置参见LoggerFilter
     *
     * @return true:打印到日志中心; false:不打印到日志中心
     */
    public static Boolean isTrackLog() {
        Object trackLogFlag = getValue(TRACK_LOG_FLAG);
        if (trackLogFlag == null) {
            return false;
        }

        try {
            return (Boolean) trackLogFlag;
        } catch (Exception e) {
            return false;
        }
    }
}
