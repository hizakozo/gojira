package com.example.gojira_api.controller.gen.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param projectId 
 * @param title 
 * @param content 
 */
data class TicketRequest(

    @get:JsonProperty("projectId", required = true) val projectId: java.util.UUID,

    @get:JsonProperty("title", required = true) val title: kotlin.String,

    @get:JsonProperty("content", required = true) val content: kotlin.String
    ) {

}

