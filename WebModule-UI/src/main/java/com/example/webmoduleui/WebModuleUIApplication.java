package com.example.webmoduleui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.example.datamoduleserve.feign"})
@MapperScan(basePackages ="com.example.*.mapper")
public class WebModuleUIApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebModuleUIApplication.class, args);
	}

}
