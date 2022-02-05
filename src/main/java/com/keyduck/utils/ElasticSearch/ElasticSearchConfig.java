package com.keyduck.utils.ElasticSearch;

import com.keyduck.keyboard.repository.SearchRepository;
import lombok.Setter;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.elasticsearch.client.RestClient;
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
    @Value("${es.host}")
    private String host;
    @Value("${es.username}")
    private String username;
    @Value("${es.password}")
    private String password;
    @Value("${es.port}")
    private Integer port;


    @Override
    public RestHighLevelClient elasticsearchClient(){
        RestHighLevelClient restClient;

        if(!username.equals("")){
         CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
            credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

            restClient = new RestHighLevelClient(
                    RestClient.builder(new HttpHost(host,port,"https"))
                       .setHttpClientConfigCallback(httpAsyncClientBuilder ->
                httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
                                   .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())));
        } else{
            ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                            .connectedTo(host+":"+port)
                            .build();
           return RestClients.create(clientConfiguration).rest();
        }

        return restClient;

    }
}
