package com.jillesvangurp.graphql.backend.jilleshackery.search

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class EsQueries(val esService: EsService): Query {

    @GraphQLDescription("Returns the health status of the Elasticsearch cluster.")
    suspend fun esHealth() =  esService.esHealth().status

    @GraphQLDescription("returns all the coders")
    suspend fun coders() = esService.findAll()

    @GraphQLDescription("Fuzzy search on coder names. Returns a list of coders.")
    suspend fun findByName(query: String) = esService.findByName(query)

    @GraphQLDescription("Returns a simple aggregation of coders on their level")
    suspend fun levels() = esService.aggOnLevel()
}

