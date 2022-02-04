package com.keyduck;

import com.keyduck.keyboard.repository.SearchRepository;
import com.keyduck.member.img.FileUploadProperties;
import com.keyduck.utils.ElasticSearch.ElasticSearchConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories(excludeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = SearchRepository.class))
@SpringBootApplication
@EnableSwagger2
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
