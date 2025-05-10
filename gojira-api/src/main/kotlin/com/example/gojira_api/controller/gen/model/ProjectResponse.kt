package com.example.gojira_api.controller.gen.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param id 
 * @param name 
 * @param description 
 */
data class ProjectResponse(

    @get:JsonProperty("id", required = true) val id: java.util.UUID,

    @get:JsonProperty("name", required = true) val name: kotlin.String,

    @get:JsonProperty("description", required = true) val description: kotlin.String
    ) {

}

