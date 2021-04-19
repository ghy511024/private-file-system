package com.bj58.ctf2021.privatefilesystem.controller.web;

import com.bj58.ctf2021.privatefilesystem.biz.service.PrivateFileBizService;
import com.bj58.ctf2021.privatefilesystem.common.template.ServiceProcessTemplate;
import com.bj58.ctf2021.privatefilesystem.controller.entity.request.file.DownloadUrlGetRequest;
import com.bj58.ctf2021.privatefilesystem.controller.entity.request.file.FileDownloadRequest;
import com.bj58.ctf2021.privatefilesystem.controller.validator.DownloadUrlGetRequestValidator;
import com.bj58.ctf2021.privatefilesystem.controller.validator.FileDownloadRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 文件请求控制器
 *
 * @author me
 * @date 2021/3/24
 */
@RestController
@RequestMapping(value = "/privateFile", produces = {"application/json"})
public class FileController {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileController.class);

    @Resource
    private PrivateFileBizService privateFileBizService;


    @GetMapping("/downloadFile")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(FileDownloadRequest request, HttpServletRequest httpRequest) {
        return ServiceProcessTemplate.doResourceProcess("下载文件", LOGGER, () -> {
            // 1.参数校验
            FileDownloadRequestValidator.validate(request);
            // 2.下载文件
            return privateFileBizService.downloadFile(request, httpRequest.getCookies());
        });
    }

    @GetMapping("/getDownloadUrl")
    public String getDownloadUrl(DownloadUrlGetRequest request, HttpServletRequest httpRequest) {
        return ServiceProcessTemplate.doJsonProcess("获取文件下载url", LOGGER, () -> {
            // 1.参数校验
            DownloadUrlGetRequestValidator.validate(request);
            // 2.获取下载地址
            return privateFileBizService.getDownloadUrl(request.getFileId(), httpRequest.getCookies());
        });
    }


}
