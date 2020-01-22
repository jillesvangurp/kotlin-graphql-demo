/*
 * Copyright 2019 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jillesvangurp.graphql.backend.examples.query

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import com.jillesvangurp.graphql.backend.examples.model.Animal
import com.jillesvangurp.graphql.backend.examples.model.AnimalType
import com.jillesvangurp.graphql.backend.examples.model.BodyPart
import com.jillesvangurp.graphql.backend.examples.model.Cat
import com.jillesvangurp.graphql.backend.examples.model.Dog
import com.jillesvangurp.graphql.backend.examples.model.LeftHand
import com.jillesvangurp.graphql.backend.examples.model.RightHand
import org.springframework.stereotype.Component

/**
 * Example query that displays the usage of interfaces and polymorphism.
 */
@Component
class PolymorphicQuery : Query {

    @GraphQLDescription("this query returns specific animal type")
    fun animal(type: AnimalType): Animal? = when (type) {
        AnimalType.CAT -> Cat()
        AnimalType.DOG -> Dog()
    }

    fun dog(): Dog = Dog()

    @GraphQLDescription("this query can return either a RightHand or a LeftHand as part of the union of both type")
    fun whichHand(whichHand: String): BodyPart = when (whichHand) {
        "right" -> RightHand(12)
        else -> LeftHand("hello world")
    }
}
