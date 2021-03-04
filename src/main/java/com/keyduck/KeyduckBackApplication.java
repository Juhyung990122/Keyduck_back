package com.keyduck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.keyduck.member.img.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileUploadProperties.class
})
public class KeyduckBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyduckBackApplication.class, args);
	}

}
