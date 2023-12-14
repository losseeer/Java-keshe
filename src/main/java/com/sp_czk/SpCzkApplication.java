package com.sp_czk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@ServletComponentScan 注解不加就无法注入 filter
@SpringBootApplication
@ServletComponentScan
public class SpCzkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpCzkApplication.class, args);
	}

}
