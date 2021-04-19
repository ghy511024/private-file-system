package com.bj58.ctf2021.privatefilesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@ImportResource({"classpath*:spring/private-file-system-config.xml"})
@SpringBootApplication
public class PrivateFileSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrivateFileSystemApplication.class, args);
    }

}
