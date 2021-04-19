package com.bj58.ctf2021.privatefilesystem.common.template;

import java.io.FileNotFoundException;

/**
 * 业务回调
 *
 * @author me
 * @date 2021/4/1
 */
public interface ServiceCallback<T> {
    T doProcess() throws FileNotFoundException;
}
