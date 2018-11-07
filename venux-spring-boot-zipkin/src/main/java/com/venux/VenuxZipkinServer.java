package com.venux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import zipkin.server.EnableZipkinServer;


@SpringBootApplication
@EnableAutoConfiguration
@EnableZipkinServer //启动ZipkinServer段
@RestController
public class VenuxZipkinServer {
    public static void main(String[] args) {
        SpringApplication.run(VenuxZipkinServer.class, args);
    }
}
