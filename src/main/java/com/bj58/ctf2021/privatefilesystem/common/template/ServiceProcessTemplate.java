package com.bj58.ctf2021.privatefilesystem.common.template;

import com.bj58.ctf2021.privatefilesystem.common.context.SystemContext;
import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizException;
import com.bj58.ctf2021.privatefilesystem.common.exception.ErrorLevel;
import com.bj58.ctf2021.privatefilesystem.common.utils.json.JacksonUtils;
import com.bj58.ctf2021.privatefilesystem.common.utils.log.LoggerUtil;
import com.bj58.ctf2021.privatefilesystem.controller.entity.response.PlainResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;

/**
 * 业务入口处理模板
 *
 * @author me
 * @date 2021/4/1
 */
public class ServiceProcessTemplate {

    /**
     * 处理业务,返回json输出结果
     *
     * @param desc     业务描述
     * @param log      {@link Logger}
     * @param callback {@link ServiceCallback}
     * @param <T>      业务回调返回对象
     * @return {@link PlainResult}
     */
    public static <T> String doJsonProcess(String desc, Logger log, ServiceCallback<T> callback) {
        Object[] args = (Object[]) SystemContext.getValue(SystemContext.OUT_REQUEST_ARGS);

        LoggerUtil.info(log, desc, args);

        PlainResult<T> plainResult = new PlainResult<>();
        plainResult.setSuccess(true);
        try {
            T data = callback.doProcess();
            plainResult.setData(data);
            plainResult.setRequestId(SystemContext.getUuid());
            return JacksonUtils.toJson(plainResult);
        } catch (BizException e) {
            if (e.getErrorCode().getErrorLevel() == ErrorLevel.SYS) {
                LoggerUtil.error(log, desc + "[发生系统异常]", e, args);
            } else {
                LoggerUtil.info(log, desc + "[发生业务异常]", e, args);
            }
            plainResult.setSuccess(false);
            plainResult.setErrorMessage(e.getErrorCode().getCode(), e.getMsg());
            plainResult.setRequestId(SystemContext.getUuid());
            return JacksonUtils.toJson(plainResult);
        } catch (Throwable t) {
            LoggerUtil.error(log, desc + "[发生未知异常]", t, args);
            plainResult.setSuccess(false);
            plainResult.setErrorMessage(AppErrorCode.SYSTEM_ERROR.getCode(), AppErrorCode.SYSTEM_ERROR.getMsg());
            plainResult.setRequestId(SystemContext.getUuid());
            return JacksonUtils.toJson(plainResult);
        }
    }

    /**
     * 处理业务,返回resource输出结果
     *
     * @param desc     业务描述
     * @param log      {@link Logger}
     * @param callback {@link ServiceCallback}
     * @param <T>      业务回调返回对象
     * @return {@link PlainResult}
     */
    public static <T> ResponseEntity<Resource> doResourceProcess(String desc, Logger log, ResourceServiceCallback<T> callback) {
        Object[] args = (Object[]) SystemContext.getValue(SystemContext.OUT_REQUEST_ARGS);

        LoggerUtil.info(log, desc, args);

        PlainResult<T> plainResult = new PlainResult<>();
        plainResult.setSuccess(true);
        try {
            return callback.doProcess();
        } catch (BizException e) {
            if (e.getErrorCode().getErrorLevel() == ErrorLevel.SYS) {
                LoggerUtil.error(log, desc + "[发生系统异常]", e, args);
            } else {
                LoggerUtil.info(log, desc + "[发生业务异常]", e.getErrorCode(), args);
            }
            plainResult.setSuccess(false);
            plainResult.setErrorMessage(e.getErrorCode().getCode(), e.getMsg());
            plainResult.setRequestId(SystemContext.getUuid());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON)
                    .body(buildResource(JacksonUtils.toJson(plainResult)));
        } catch (Throwable t) {
            LoggerUtil.error(log, desc + "[发生未知异常]", t, args);
            plainResult.setSuccess(false);
            plainResult.setErrorMessage(AppErrorCode.SYSTEM_ERROR.getCode(), AppErrorCode.SYSTEM_ERROR.getMsg());
            plainResult.setRequestId(SystemContext.getUuid());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON)
                    .body(buildResource(JacksonUtils.toJson(plainResult)));
        }
    }


    private static Resource buildResource(String data) {
        return new InputStreamResource(new ByteArrayInputStream(StringUtils.isNoneBlank(data) ? data.getBytes() : "".getBytes()));
    }

}
