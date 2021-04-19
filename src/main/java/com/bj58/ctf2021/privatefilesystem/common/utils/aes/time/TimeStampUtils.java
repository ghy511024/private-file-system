package com.bj58.ctf2021.privatefilesystem.common.utils.aes.time;

/**
 * 时间戳工具类
 *
 * @author me
 * @date 2021/4/1
 */
public class TimeStampUtils {
    private final static long NANO_TIME_MULTIPLE = 1000_000;

    private final static long MICRO_TIME_MULTIPLE = 1000;

    private final static long JVM_TIME_DIFF;

    static {
        JVM_TIME_DIFF = System.currentTimeMillis() - System.nanoTime() / NANO_TIME_MULTIPLE;
    }

    public static long getMilliTimeStamp() {
        return System.currentTimeMillis();
    }

    public static long getMicroTimeStamp() {
        return getNanoTimeStamp() / MICRO_TIME_MULTIPLE;
    }

    public static long getNanoTimeStamp() {
        return System.nanoTime() + JVM_TIME_DIFF * NANO_TIME_MULTIPLE;
    }

}
