package com.pavlig43.retromeetdata.register.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("login")
    val login: String,

    @SerialName("password")
    val password: String,

)
