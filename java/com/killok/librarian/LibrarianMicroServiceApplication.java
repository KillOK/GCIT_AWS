package com.killok.librarian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan("com.killok.librarian")
public class LibrarianMicroServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibrarianMicroServiceApplication.class, args);
	}
}
