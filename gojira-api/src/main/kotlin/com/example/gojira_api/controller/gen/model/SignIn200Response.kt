package com.example.gojira_api.controller.gen.model

import java.util.Objects
import com.fasterxml.jackson.annotation.JsonProperty

/**
 * 
 * @param token 
 */
data class SignIn200Response(

    @get:JsonProperty("token", required = true) val token: kotlin.String
    ) {

}

