package com.bookservice.borrowing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.bookservice.borrowing", "com.bookservice.commonservice"})
public class BorrowingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BorrowingApplication.class, args);
	}

}
