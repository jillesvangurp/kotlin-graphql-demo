package com.jillesvangurp.graphql.backend.jilleshackery.search

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest
import org.elasticsearch.action.search.source
import org.elasticsearch.client.RequestOptions
import org.elasticsearch.client.RestHighLevelClient
import org.elasticsearch.client.crudDao
import org.elasticsearch.client.healthAsync
import org.elasticsearch.index.query.Operator
import org.elasticsearch.index.query.QueryBuilders.matchAllQuery
import org.elasticsearch.index.query.QueryBuilders.matchQuery
import org.elasticsearch.search.aggregations.bucket.terms.Terms
import org.elasticsearch.search.builder.SearchSourceBuilder.searchSource
import org.springframework.stereotype.Component

@Component
class EsService(val esClient: RestHighLevelClient) {
    val codersCrud = esClient.crudDao<CodeMonkey>("coderz")

    suspend fun esHealth() = esClient.cluster().healthAsync(ClusterHealthRequest(), RequestOptions.DEFAULT)

    fun index(coder: CodeMonkey): Boolean {
        // use the name as the id
        return try {
            codersCrud.index(coder.name, coder)
            true
        } catch (e: Exception) {
            false
        }
    }

    suspend fun findAll(): List<CodeMonkey> =
        codersCrud.searchAsync {
            source(searchSource()
                .size(100)
                .query(matchAllQuery()))
        }.mappedHits.toList()

    fun clearAll(): Boolean =
        try {
            codersCrud.deleteIndex()
            true
        } catch (e: Exception) {
            false
        }

    suspend fun findByName(query: String) = codersCrud.searchAsync {
        source(searchSource().size(100).query(matchQuery("name",query).fuzziness("AUTO").operator(Operator.OR)))
    }.mappedHits.toList()

    suspend fun aggOnLevel() = codersCrud.searchAsync {
        source("""
            {
                "size": 0,
                "aggs": {
                    "by_level": {
                        "terms": {
                            "field":"level.keyword"
                        }
                    }
                }
            }
        """.trimIndent())
    }.searchResponse.aggregations.get<Terms>("by_level").buckets.map {b -> "${b.key}: ${b.docCount}"}.joinToString(", ")


}