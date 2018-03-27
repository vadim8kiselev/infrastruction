package org.infrastruction.elasticsearch

import org.apache.http.HttpHost
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.infrastruction.elasticsearch.controller.DataProviderController
import org.infrastruction.elasticsearch.job.StoreDataToElasticSearchJob
import org.infrastruction.elasticsearch.service.ElasticSearchService
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.client.loadbalancer.LoadBalanced
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class ElasticSearchConfiguration {

    @Value('${elasticsearch.hostname:localhost}')
    private String elasticSearchHostName

    @Value('${elasticsearch.port:9200}')
    private Integer elasticSearchHostPort

    @Value('${elasticsearch.protocol:http}')
    private String elasticSearchProtocol

    @Bean
    StoreDataToElasticSearchJob storeDataToElasticSearchJob(ElasticSearchService elasticSearchService, DataProviderController dataProviderController) {
        new StoreDataToElasticSearchJob(elasticSearchService, dataProviderController)
    }

    @Bean
    ElasticSearchService elasticSearchService(RestHighLevelClient restHighLevelClient) {
        new ElasticSearchService(restHighLevelClient)
    }

    @Bean
    RestHighLevelClient restHighLevelClient() {
        new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(elasticSearchHostName, elasticSearchHostPort, elasticSearchProtocol)))
    }

    @Bean
    @LoadBalanced
    RestTemplate restTemplate() {
        new RestTemplate()
    }
}
