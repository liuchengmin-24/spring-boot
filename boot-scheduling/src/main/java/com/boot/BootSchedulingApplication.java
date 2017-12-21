package com.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author Administrator
 */
@SpringBootApplication
@EnableScheduling
public class BootSchedulingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootSchedulingApplication.class, args);
	}
}
