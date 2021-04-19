package com.bj58.ctf2021.privatefilesystem.common.utils.client.ctf;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * CTF接口客户端
 *
 * @author me
 * @date 2021/4/1
 */
public interface CtfApiClient {

    /**
     * 获取用户信息
     *
     * @param cookieList cookie列表
     * @return 用户信息
     */
    String getUserInfo(List<String> cookieList);

    /**
     * 获取用户标识
     *
     * @param cookieList 用户标识
     * @return 用户标识
     */
    String getUserFlag(List<String> cookieList);
}
