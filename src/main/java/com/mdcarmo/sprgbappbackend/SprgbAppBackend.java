package com.mdcarmo.sprgbappbackend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.mdcarmo.sprgbappbackend.services.S3Service;

@SpringBootApplication
public class SprgbAppBackend implements CommandLineRunner {
	
	@Autowired
	private S3Service s3service;
	
	public static void main(String[] args) {
		SpringApplication.run(SprgbAppBackend.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		s3service.uploadFile("D:\\Marcelo\\Imagens\\mustang97.jpg");
	}
}
