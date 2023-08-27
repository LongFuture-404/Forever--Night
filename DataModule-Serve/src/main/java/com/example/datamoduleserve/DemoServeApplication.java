package com.example.datamoduleserve;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient //能够被注册中心发现，能扫描到这个服务
public class DemoServeApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoServeApplication.class, args);
	}

}
