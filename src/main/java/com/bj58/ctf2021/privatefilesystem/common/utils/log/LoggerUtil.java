package com.bj58.ctf2021.privatefilesystem.common.utils.log;

import com.bj58.ctf2021.privatefilesystem.common.context.SystemContext;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizException;
import com.bj58.ctf2021.privatefilesystem.common.exception.IErrorCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 *
 * @author me
 * @date 2021/4/1
 */
public class LoggerUtil {
    public static final Logger TRACK_LOG = LoggerFactory.getLogger("TRACK_LOG");


    public static void debug(Logger logger, String desc, Object... obj) {
        if (logger == null) {
            return;
        }
        if (logger.isDebugEnabled()) {
            logger.debug(getLogString(desc, obj));
        }
    }

    public static void info(Logger logger, String desc, Object... obj) {
        if (logger == null) {
            return;
        }

        if (logger.isInfoEnabled()) {
            logger.info(getLogString(desc, obj));
            return;
        }

        if (SystemContext.isTrackLog()) {
            TRACK_LOG.info(getLogString(desc, obj));
        }
    }

    public static void warn(Logger logger, String desc, Throwable e, Object... obj) {
        if (logger != null) {
            logger.warn(getLogString(desc, e, obj), e);
        }

    }

    public static void warn(Logger logger, String desc, Object... obj) {
        if (logger != null) {
            logger.warn(getLogString(desc, obj));
        }
    }

    public static void error(Logger logger, String desc, Throwable e, Object... obj) {
        if (logger != null) {
            logger.error(getLogString(desc, e, obj), e);
        }
    }

    public static void error(Logger logger, String desc, Object... obj) {
        if (logger != null) {
            logger.error(getLogString(desc, obj));
        }
    }

    private static String getLogString(String desc, Throwable t, Object... obj) {
        StringBuilder stringBuilder = new StringBuilder();

        String msg = getLogString(desc, obj);

        stringBuilder.append(msg);

        if (t instanceof BizException) {
            IErrorCodeEnum errorCode = ((BizException) t).getErrorCode();
            stringBuilder.append("[");
            stringBuilder.append(errorCode);
            stringBuilder.append("]");
        }

        return stringBuilder.toString();
    }

    private static String getLogString(String desc, Object... obj) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[").append(SystemContext.getUuid()).append("]");

        stringBuilder.append("[").append(desc).append("]");

        if ((obj != null) && (obj.length > 0)) {
            stringBuilder.append("[");
            for (int i = 0; i < obj.length; i++) {
                String objStr = String.valueOf(obj[i]);
                stringBuilder.append(objStr);
                if (obj.length - 1 != i) {
                    stringBuilder.append(", ");
                }
            }
            stringBuilder.append("]");
        }

        return stringBuilder.toString();
    }

}
