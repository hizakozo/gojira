package com.example.gojira_api.controller.gen.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param ticketId 
 * @param projectId 
 * @param title 
 * @param content 
 */
data class TicketResponse(

    @get:JsonProperty("ticketId", required = true) val ticketId: java.util.UUID,

    @get:JsonProperty("projectId", required = true) val projectId: java.util.UUID,

    @get:JsonProperty("title", required = true) val title: kotlin.String,

    @get:JsonProperty("content", required = true) val content: kotlin.String
    ) {

}

