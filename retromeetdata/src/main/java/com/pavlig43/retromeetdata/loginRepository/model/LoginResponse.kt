package com.pavlig43.retromeetdata.loginRepository.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponse(

    @SerialName("loginId")
    val loginId: Int,
)
