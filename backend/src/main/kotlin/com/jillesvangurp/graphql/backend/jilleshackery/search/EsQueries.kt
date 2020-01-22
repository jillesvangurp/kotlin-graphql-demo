package com.jillesvangurp.graphql.backend.jilleshackery.search

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class EsQueries(val esService: EsService): Query {

    @GraphQLDescription("Returns the health status of the Elasticsearch cluster.")
    fun esHealth() =  esService.esHealth().status

    @GraphQLDescription("returns all the coders")
    fun coders() = esService.findAll()

    @GraphQLDescription("Fuzzy search on coder names. Returns a list of coders.")
    fun findByName(query: String) = esService.findByName(query)

    @GraphQLDescription("Returns a simple aggregation of coders on their level")
    fun levels() = esService.aggOnLevel()
}

