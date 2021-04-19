package com.bj58.ctf2021.privatefilesystem.biz.service.impl;

import com.bj58.ctf2021.privatefilesystem.biz.checker.DownloadFileBizChecker;
import com.bj58.ctf2021.privatefilesystem.biz.checker.DownloadUrlBizChecker;
import com.bj58.ctf2021.privatefilesystem.biz.service.PrivateFileBizService;
import com.bj58.ctf2021.privatefilesystem.common.config.FileSystemAutoConfiguration;
import com.bj58.ctf2021.privatefilesystem.common.enums.code.AppErrorCode;
import com.bj58.ctf2021.privatefilesystem.common.exception.BizExceptionBuilder;
import com.bj58.ctf2021.privatefilesystem.common.utils.aes.AesUtils;
import com.bj58.ctf2021.privatefilesystem.common.utils.aes.time.TimeStampUtils;
import com.bj58.ctf2021.privatefilesystem.common.utils.client.ctf.CtfApiClient;
import com.bj58.ctf2021.privatefilesystem.controller.converter.HttpHeaderConverter;
import com.bj58.ctf2021.privatefilesystem.controller.entity.request.file.FileDownloadRequest;
import com.bj58.ctf2021.privatefilesystem.core.model.file.FileInfoDO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.io.ByteArrayInputStream;
import java.net.URLEncoder;

/**
 * 私有文件业务服务实现
 *
 * @author me
 * @date 2021/4/1
 */
@Service
public class PrivateFileBizServiceImpl implements PrivateFileBizService {

    @Resource
    private FileSystemAutoConfiguration fileSystemAutoConfiguration;

    @Resource
    private DownloadUrlBizChecker downloadUrlBizChecker;

    @Resource
    private DownloadFileBizChecker downloadFileBizChecker;

    @Resource
    private CtfApiClient ctfApiClient;

    @Value("${api.client.ctf.cdKeyPlaceholder}")
    private String cdKeyPlaceholder;

    @Override
    public String getDownloadUrl(Long fileId, Cookie[] cookies) {
        // 1.获取文件信息
        FileInfoDO fileInfoDO = getFileInfoDO(fileId);
        // 2.校验文件权限
        downloadUrlBizChecker.checkCouldGetDownloadUrl(fileInfoDO, cookies);
        // 3.构造下载地址
        String singPlainData = fileInfoDO.buildSignPlainData(HttpHeaderConverter.getUserName(cookies), fileSystemAutoConfiguration.getSplitString());
        String timeVersion = buildTimeVersion();
        String sign = AesUtils.encrypt(fileSystemAutoConfiguration.getAesEncryptKey(), timeVersion, singPlainData);
        return String.format("%s/privateFile/downloadFile?id=%s&r=%s&sign=%s",
                fileSystemAutoConfiguration.getSystemDomain(), fileInfoDO.getId(), timeVersion, sign);
    }

    @Override
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(FileDownloadRequest request, Cookie[] cookies) {
        // 1.获取文件信息
        FileInfoDO fileInfoDO = getFileInfoDO(request.getId());
        // 2.校验授权参数
        downloadFileBizChecker.checkCouldDownloadFile(fileInfoDO, cookies, request.getSign(), request.getR());
        // 3.构造下载文件资源
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            headers.add("charset", "utf-8");
            headers.add("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileInfoDO.getName(), "UTF-8") + "\"");
            return ResponseEntity.ok().headers(headers).contentType(MediaType.parseMediaType("text/plain"))
                    .body(buildFileResource(cookies, fileInfoDO.getFileContent()));
        } catch (Exception e) {
            throw BizExceptionBuilder.build(AppErrorCode.DOWNLOAD_FAILED);
        }
    }

    /**
     * 获取文件信息
     *
     * @param fileId 文件id
     * @return 文件信息
     */
    private FileInfoDO getFileInfoDO(Long fileId) {
        return fileSystemAutoConfiguration.getDefaultFileList().stream()
                .filter(e -> e.getId().equals(fileId))
                .findFirst().orElse(null);
    }

    /**
     * 构造时间戳版本号
     *
     * @return 基于微秒级时间戳的version
     */
    private String buildTimeVersion() {
        return String.format("version-%s", TimeStampUtils.getMicroTimeStamp());
    }

    /**
     * 构造文件资源
     */
    private org.springframework.core.io.Resource buildFileResource(Cookie[] cookies, String data) {
        if (StringUtils.isNotBlank(cdKeyPlaceholder) && StringUtils.contains(data, cdKeyPlaceholder)) {
            String userFlag = ctfApiClient.getUserFlag(HttpHeaderConverter.getCookieList(cookies));
            data = StringUtils.replace(data, cdKeyPlaceholder, userFlag);
        }
        return new InputStreamResource(new ByteArrayInputStream(StringUtils.isNoneBlank(data) ? data.getBytes() : "".getBytes()));
    }
}
