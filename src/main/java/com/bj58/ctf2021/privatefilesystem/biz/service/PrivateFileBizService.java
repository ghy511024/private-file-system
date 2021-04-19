package com.bj58.ctf2021.privatefilesystem.biz.service;

import com.bj58.ctf2021.privatefilesystem.controller.entity.request.file.FileDownloadRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.Cookie;

/**
 * 私有文件业务服务
 *
 * @author me
 * @date 2021/4/1
 */
public interface PrivateFileBizService {

    /**
     * 获取文件的下载地址
     *
     * @param fileId  文件id
     * @param cookies cookies
     * @return 文件的下载地址
     */
    String getDownloadUrl(Long fileId, Cookie[] cookies);

    /**
     * 下载文件
     *
     * @param request 文件下载请求
     * @param cookies cookies
     * @return 文件资源响应实体
     */
    ResponseEntity<Resource> downloadFile(FileDownloadRequest request, Cookie[] cookies);
}
