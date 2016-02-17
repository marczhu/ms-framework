package com.ms.mdd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * Created by mark.zhu on 2015/11/9.
 */
@SpringBootApplication
 public class SampleDataJpaApplication {
	public static void main(String[] args) throws Exception {
		File file = new File("E://schema/schema.sql");
		if (file.exists()) {
			file.delete();
		}
		System.setProperty("spring.config.name","application-account");
		System.setProperty("spring.config.location","classpath:/application-account.properties");
		SpringApplication.run(SampleDataJpaApplication.class, args);
	}

}
