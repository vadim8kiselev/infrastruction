package org.infrastruction.elasticsearch

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.netflix.feign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
class Application {

    static void main(String[] args) {
        SpringApplication.run(Application.class, args)
    }
}
