package com.jillesvangurp.graphql.backend.jilleshackery.search

import org.elasticsearch.client.create
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EsConfig {
    @Bean
    fun esClient() = create(host = "localhost", port= 9200)
}