package com.pavlig43.retromeetdata.loginRepository.model

import com.pavlig43.retromeetdata.common.USER_ID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable



@Serializable
data class LoginResponse(

    @SerialName(USER_ID)
    val userId: Int,


    )
