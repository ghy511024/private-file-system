package com.bj58.ctf2021.privatefilesystem.common.config;

import com.bj58.ctf2021.privatefilesystem.core.model.file.FileInfoDO;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 文件系统自动配置
 *
 * @author me
 * @date 2021/4/1
 */
@Component
@ConfigurationProperties(prefix = "file-system")
@Data
public class FileSystemAutoConfiguration {
    /**
     * 系统域名
     */
    private String systemDomain;
    /**
     * 系统加密密钥(测试、沙盒、生产环境使用不同的配置文件进行隔离)
     */
    private String aesEncryptKey;
    /**
     * 签名分割字符串(测试、沙盒、生产环境使用不同的配置文件进行隔离)
     */
    private String splitString;
    /**
     * 默认文件列表
     */
    private List<FileInfoDO> defaultFileList;


}
