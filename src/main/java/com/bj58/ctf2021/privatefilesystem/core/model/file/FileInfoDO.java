package com.bj58.ctf2021.privatefilesystem.core.model.file;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * 文件信息模型
 *
 * @author me
 * @date 2021/4/1
 */
@Data
public class FileInfoDO {
    /**
     * 文件id
     */
    private Long id;
    /**
     * 文件名
     */
    private String name;
    /**
     * 文件上传者
     */
    private String uploader;
    /**
     * 文件内容
     */
    private String fileContent;

    /**
     * 构造授权签名明文
     *
     * @param downloaderUserName 下载者用户名
     * @param splitString        分割字符串
     * @return 授权签名明文
     */
    public String buildSignPlainData(String downloaderUserName, String splitString) {
        // 按用户名、分隔符、文件id构造签名明文
        return String.format("%s%s%s", StringUtils.right(downloaderUserName, 8), splitString, id);
    }
}
