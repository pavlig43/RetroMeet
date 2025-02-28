package com.pavlig43.retromeetdata.mainScreenPreviewRepository.model

import com.pavlig43.retromeetdata.common.Gender
import com.pavlig43.retromeetdata.common.USER_ID
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MainScreenPreviewResponse(

    @SerialName(USER_ID)
    val userId:Int,

    @SerialName("gender")
    val gender: Gender?,

    @SerialName("main_photo_path")
    val mainPhotoPath:String?,

    @SerialName("name")
    val name: String?,

    @SerialName("date_of_birth")
    val dateOfBirth:Long?,

    )
