package com.bj58.ctf2021.privatefilesystem.common.filter;

import com.bj58.ctf2021.privatefilesystem.common.context.SystemContext;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.UUID;

/**
 * 系统上下文过滤器
 *
 * @author me
 * @date 2021/4/2
 */
public class SystemContextFilter {
    /**
     * 接口切点
     */
    public void inServiceLayer() {
    }

    /**
     * 初始化请求uuid
     *
     * @param jp 切点
     * @return 结果
     * @throws Throwable 异常
     */
    public Object initUuid(ProceedingJoinPoint jp) throws Throwable {
        Object result;
        try {
            SystemContext.setValue(SystemContext.UUID, UUID.randomUUID().toString());
            Object[] args = jp.getArgs();
            SystemContext.setValue(SystemContext.OUT_REQUEST_ARGS, args);

            result = jp.proceed();
        } finally {
            SystemContext.clear();
        }
        return result;
    }
}
