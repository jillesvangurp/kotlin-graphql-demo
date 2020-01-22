package com.jillesvangurp.graphql.backend.jilleshackery.search

import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component

@Component
class Indexer(val esService: EsService):
    Mutation {
    fun index(coder: CodeMonkey) = esService.index(coder)

    fun clearCoders() = esService.clearAll()

}