package com.jillesvangurp.graphql.backend.jilleshackery

import com.expediagroup.graphql.spring.operations.Query
import org.springframework.stereotype.Component

data class Coder(val name: String, val age: Int)

@Component
class CodersocietyQuery: Query {
    fun hiCoders() = listOf(Coder("Koji", 10),Coder("Jilles", 11))
}