package com.keyduck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.keyduck.member.img.FileUploadProperties;

@SpringBootApplication
@EnableConfigurationProperties({
	FileUploadProperties.class
})
public class KeyduckBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(KeyduckBackApplication.class, args);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	

}
