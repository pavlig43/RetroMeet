package com.pavlig43.retromeetdata.registerRepository.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    @SerialName("loginId")
    val loginId: Int
)
