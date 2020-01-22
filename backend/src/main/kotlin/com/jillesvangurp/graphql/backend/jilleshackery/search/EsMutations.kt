package com.jillesvangurp.graphql.backend.jilleshackery.search

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Mutation
import org.springframework.stereotype.Component

@Component
class EsMutations(val esService: EsService): Mutation {

    @GraphQLDescription("add a code monkey to the index. The index is created on the fly and is schemaless.")
    fun index(coder: CodeMonkey) = esService.index(coder)

    @GraphQLDescription("delete the entire index with the coders.")
    fun clearCoders() = esService.clearAll()

}