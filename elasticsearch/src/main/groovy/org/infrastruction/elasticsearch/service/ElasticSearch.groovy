package org.infrastruction.elasticsearch.service

import org.infrastruction.elasticsearch.model.Weather

interface ElasticSearch {

    String info()

    void postWeather(List<Weather> weather)
}