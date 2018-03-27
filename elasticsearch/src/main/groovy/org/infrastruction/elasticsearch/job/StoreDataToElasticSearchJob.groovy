package org.infrastruction.elasticsearch.job

import org.infrastruction.elasticsearch.controller.DataProviderController
import org.infrastruction.elasticsearch.service.ElasticSearchService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired

import javax.annotation.PostConstruct

class StoreDataToElasticSearchJob {

    public static final Logger LOGGER = LoggerFactory.getLogger(StoreDataToElasticSearchJob.class)

    private final ElasticSearchService elasticSearchService

    private final DataProviderController dataProviderController

    @Autowired
    StoreDataToElasticSearchJob(ElasticSearchService elasticSearchService, DataProviderController dataProviderController) {
        this.elasticSearchService = elasticSearchService
        this.dataProviderController = dataProviderController
    }

    @PostConstruct
    void run() {
        new Thread({ -> storeDataToElasticSearch() }).start()
    }

    void storeDataToElasticSearch() {
        while (true) {
            elasticSearchService.postWeather(dataProviderController.findAllWeather())
            LOGGER.info("Data has pushed to Elastic Search")
            sleep()
        }
    }

    static void sleep() {
        try {
            Thread.sleep(2 * 60 * 1000) // 2 minutes
        } catch (InterruptedException e) {
            e.printStackTrace()
        }
    }
}
