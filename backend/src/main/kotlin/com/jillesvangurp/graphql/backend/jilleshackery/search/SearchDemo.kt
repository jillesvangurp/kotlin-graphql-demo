package com.jillesvangurp.graphql.backend.jilleshackery.search

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

@Component
class SearchDemo(val esService: EsService): Query {

    fun esHealth() =  esService.esHealth().status

    fun coders() = esService.findAll()

    fun findByName(query: String) = esService.findByName(query)

    fun levels() = esService.aggOnLevel()
}

