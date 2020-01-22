package com.jillesvangurp.graphql.backend.jilleshackery.search

enum class CoderLevel { junior, senior, grand_master}

data class CodeMonkey(val name: String, val level: CoderLevel)