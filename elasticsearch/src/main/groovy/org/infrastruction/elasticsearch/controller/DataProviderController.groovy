package org.infrastruction.elasticsearch.controller

import org.infrastruction.elasticsearch.model.Weather
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class DataProviderController {

    private final RestTemplate restTemplate

    @Autowired
    DataProviderController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate
    }

    @RequestMapping('/findAllWeather')
    List<Weather> findAllWeather() {
        restTemplate.getForObject(
                'http://data/findAllWeather', List.class)
    }
}
