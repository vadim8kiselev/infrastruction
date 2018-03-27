package org.infrastruction.elasticsearch.service

import com.google.common.collect.Lists
import org.elasticsearch.action.bulk.BulkRequest
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.common.xcontent.XContentFactory
import org.infrastruction.elasticsearch.model.Weather
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value

class ElasticSearchService implements ElasticSearch {

    @Value('${elasticsearch.bulk.max-size:1000}')
    private Integer bulkMaxSize

    private final RestHighLevelClient client

    @Autowired
    ElasticSearchService(RestHighLevelClient client) {
        this.client = client
    }

    @Override
    String info() {
        def info = client.info()

        '{<br/>' +
                '&nbsp&nbsp&nbsp&nbsp"name" : "' + info.getNodeName() + '",<br/>' +
                '&nbsp&nbsp&nbsp&nbsp"cluster_name" : "' + info.getClusterName().value() + '",<br/>' +
                '&nbsp&nbsp&nbsp&nbsp"cluster_uuid" : "' + info.getClusterUuid() + '",<br/>' +
                '&nbsp&nbsp&nbsp&nbsp"version" : {<br/>' +
                '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"number" : "' + info.getVersion() + '",<br/>' +
                '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"build_hash" : "' + info.getBuild().shortHash() + '",<br/>' +
                '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"build_date" : "' + info.getBuild().date() + '",<br/>' +
                '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"build_snapshot" : ' + info.getBuild().snapshot + ',<br/>' +
                '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"lucene_version" : "' + info.getVersion().luceneVersion + '",<br/>' +
                '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"minimum_wire_compatibility_version" : "' + info.getVersion().minimumCompatibilityVersion() + '",<br/>' +
                '&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp"minimum_index_compatibility_version" : "' + info.getVersion().minimumIndexCompatibilityVersion() + '"<br/>' +
                '&nbsp&nbsp&nbsp&nbsp},<br/>' +
                '&nbsp&nbsp&nbsp&nbsp"tagline" : "You Know, for Search"<br/>' +
                '}<br/>'
    }

    @Override
    void postWeather(List<Weather> weathers) {
        try {
            List<List<Weather>> lists = Lists.partition(weathers, bulkMaxSize)

            for (List<Weather> list : lists) {
                BulkRequest bulkRequest = new BulkRequest()

                for (Weather weather : list) {
                    IndexRequest indexRequest = new IndexRequest("weather", "doc", weather.getId())
                            .source(XContentFactory.jsonBuilder()
                            .startObject()
                            .field("weather_id", weather.getId())
                            .endObject())

                    bulkRequest.add(indexRequest)
                }

                if (!bulkRequest.requests().isEmpty()) {
                    client.bulk(bulkRequest)
                }
            }
        } catch (IOException e) {
            e.printStackTrace()
        }
    }
}
