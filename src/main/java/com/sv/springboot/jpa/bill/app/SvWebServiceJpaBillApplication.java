package com.sv.springboot.jpa.bill.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sv.springboot.jpa.bill.app.models.service.IUploadFileService;

@SpringBootApplication
public class SvWebServiceJpaBillApplication implements CommandLineRunner {
	
	@Autowired
	IUploadFileService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(SvWebServiceJpaBillApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		uploadService.deleteAll();
		uploadService.init();
	}
}
