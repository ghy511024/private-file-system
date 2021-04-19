package com.bj58.ctf2021.privatefilesystem.controller.entity.request.file;

import lombok.Data;

/**
 * 文件下载请求
 *
 * @author me
 * @date 2021/4/1
 */
@Data
public class FileDownloadRequest {
    /**
     * 文件id
     */
    private Long id;
    /**
     * 随机数
     */
    private String r;
    /**
     * 授权签名
     */
    private String sign;
}
