package com.keyduck.utils.ElasticSearch;

import com.keyduck.keyboard.repository.SearchRepository;
import lombok.Setter;
import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.net.ssl.SSLContext;

@EnableElasticsearchRepositories(basePackageClasses = SearchRepository.class)
@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    @Value("${spring.elasticsearch.rest.uris}")
    private String portInfo;

    @Override
    public RestHighLevelClient elasticsearchClient(){
        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("192.168.0.17:9200")
                .build();
        return RestClients.create(clientConfiguration).rest();

    }
}
