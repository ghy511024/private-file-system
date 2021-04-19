package com.bj58.ctf2021.privatefilesystem.controller.entity.request.file;

import lombok.Data;

/**
 * 文件下载url获取请求
 *
 * @author me
 * @date 2021/4/1
 */
@Data
public class DownloadUrlGetRequest {
    /**
     * 文件id
     */
    private Long fileId;
}
