package com.zhanghao.searchboot;


/**
 * Spring Boot 内置了 Tomcat 所以可以允许通过main方法 的方式来启动项目。 
 */

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(value = "com.zhanghao.searchboot.dao")
public class MainApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
}