package com.pavlig43.retromeetdata.mainScreenPreviewRepository.model

import com.pavlig43.retromeetdata.common.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainScreenPreviewResponse(

    @SerialName("loginId")
    val loginId: Int,

    @SerialName("gender")
    val gender: Gender?,

    @SerialName("mainPhotoPath")
    val mainPhotoPath: String?,

    @SerialName("name")
    val name: String?,

    @SerialName("dateOfBirth")
    val dateOfBirth: Long?,

    )
