package com.jillesvangurp.graphql.backend.jilleshackery

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component

@Component
class OhHai: Query {

    @GraphQLDescription("Says hi after a few seconds")
    suspend fun ohai() = coroutineScope {
        delay(2000)
        "ohai!"
    }
}