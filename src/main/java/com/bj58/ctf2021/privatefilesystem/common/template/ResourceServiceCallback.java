package com.bj58.ctf2021.privatefilesystem.common.template;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.FileNotFoundException;

/**
 * 资源业务回调
 *
 * @author me
 * @date 2021/4/1
 */
public interface ResourceServiceCallback<T> {
    ResponseEntity<Resource> doProcess() throws FileNotFoundException;
}
