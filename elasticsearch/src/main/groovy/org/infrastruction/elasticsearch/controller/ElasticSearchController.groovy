package org.infrastruction.elasticsearch.controller

import org.infrastruction.elasticsearch.service.ElasticSearchService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ElasticSearchController {

    private final ElasticSearchService elasticSearchService

    @Autowired
    ElasticSearchController(ElasticSearchService elasticSearchService) {
        this.elasticSearchService = elasticSearchService
    }

    @RequestMapping("/")
    String message() {
        elasticSearchService.info()
    }
}
