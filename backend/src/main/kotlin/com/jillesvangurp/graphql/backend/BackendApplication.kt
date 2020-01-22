package com.jillesvangurp.graphql.backend

import com.expediagroup.graphql.directives.KotlinDirectiveWiringFactory
import com.fasterxml.jackson.databind.ObjectMapper
import com.jillesvangurp.graphql.backend.examples.datafetchers.CustomDataFetcherFactoryProvider
import com.jillesvangurp.graphql.backend.examples.datafetchers.SpringDataFetcherFactory
import com.jillesvangurp.graphql.backend.examples.directives.CustomDirectiveWiringFactory
import com.jillesvangurp.graphql.backend.examples.exceptions.CustomDataFetcherExceptionHandler
import com.jillesvangurp.graphql.backend.examples.extension.CustomSchemaGeneratorHooks
import graphql.execution.DataFetcherExceptionHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class BackendApplication {
	@Bean
	fun wiringFactory() = CustomDirectiveWiringFactory()

	@Bean
	fun hooks(wiringFactory: KotlinDirectiveWiringFactory) = CustomSchemaGeneratorHooks(wiringFactory)

	@Bean
	fun dataFetcherFactoryProvider(springDataFetcherFactory: SpringDataFetcherFactory, objectMapper: ObjectMapper) =
		CustomDataFetcherFactoryProvider(springDataFetcherFactory, objectMapper)
	@Bean
	fun dataFetcherExceptionHandler(): DataFetcherExceptionHandler = CustomDataFetcherExceptionHandler()
}

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}
