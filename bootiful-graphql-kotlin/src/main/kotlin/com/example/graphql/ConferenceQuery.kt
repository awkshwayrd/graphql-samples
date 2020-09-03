package com.example.graphql

import com.expediagroup.graphql.annotations.GraphQLDescription
import com.expediagroup.graphql.spring.operations.Query
import kotlinx.coroutines.delay
import org.springframework.stereotype.Component


@Component
class ConferenceQuery : Query {

    fun conference(): Conference = Conference("GOTO Chicago", "virtual")

    fun people(nameStartsWith: String?): List<People> = listOf(
            Speaker("Dariusz", listOf("Bootiful GraphQL")),
            Attendee("Jane", TicketType.CONFERENCE)
    ).filter { it.name.startsWith(nameStartsWith ?: "") }

    fun schedule() = ScheduleDetails(
            greeting = "Welcome to GOTO Chicago!"
    )
}

class ScheduleDetails(val greeting: String) {
    suspend fun talks(): List<String> {
        delay(2000)
        return listOf("Bootiful GraphQL", "GraphQL is awesome", "Intro to GraphQL")
    }
}

interface People {
    val name: String
}

data class Speaker(override val name: String, val talks: List<String>) : People

data class Attendee(override val name: String, val ticketType: TicketType) : People

enum class TicketType {
    CONFERENCE,
    WORKSHOP,
    FULL
}

data class Conference(
        @GraphQLDescription("my super **awesome** `conference` name")
        val name: String,
        @Deprecated("not needed anymore")
        val location: String?)