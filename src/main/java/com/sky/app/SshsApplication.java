package com.sky.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Simon
 * @date 2018-01-20
 */
@ComponentScan(basePackages = { "com.sky" })
@ServletComponentScan
@SpringBootApplication
public class SshsApplication {
	public static void main(String[] args) {
		SpringApplication.run(SshsApplication.class, args);
	}
}
