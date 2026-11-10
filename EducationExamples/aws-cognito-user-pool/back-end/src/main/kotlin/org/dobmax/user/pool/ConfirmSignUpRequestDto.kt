package org.dobmax.user.pool

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class ConfirmSignUpRequestDto
    @JsonCreator
    constructor(
        @JsonProperty("email") val email: String,
        @JsonProperty("code") val code: String,
    )
