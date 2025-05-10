package com.example.gojira_api.controller.gen.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param name 
 * @param description 
 */
data class ProjectRequest(

    @get:JsonProperty("name", required = true) val name: kotlin.String,

    @get:JsonProperty("description", required = true) val description: kotlin.String
    ) {

}

