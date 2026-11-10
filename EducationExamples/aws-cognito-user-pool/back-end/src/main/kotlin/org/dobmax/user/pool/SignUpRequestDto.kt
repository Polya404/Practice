package org.dobmax.user.pool

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class SignUpRequestDto
    @JsonCreator
    constructor(
        @JsonProperty("firstName") val firstName: String,
        @JsonProperty("familyName") val familyName: String,
        @JsonProperty("email") val email: String,
        @JsonProperty("password") val password: String,
    )
