package com.example.demoui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.example.demoserve.feign"})
@MapperScan(basePackages ="com.example.*.mapper")
public class DemoUIApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoUIApplication.class, args);
	}

}
