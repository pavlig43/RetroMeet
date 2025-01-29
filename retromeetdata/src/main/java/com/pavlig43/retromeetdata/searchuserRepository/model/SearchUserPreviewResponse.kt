package com.pavlig43.retromeetdata.searchuserRepository.model

import com.pavlig43.retromeetdata.common.Gender
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class SearchUserPreviewResponse(

    @SerialName("loginId")
    val loginId:Int,

    @SerialName("gender")
    val gender: Gender?,

    @SerialName("mainPhotoPath")
    val mainPhotoPath:String?,

    @SerialName("name")
    val name: String?,

    @SerialName("dateOfBirth")
    val dateOfBirth:Long?
)

@Serializable
data class SearchUsersResponse(
    val users:List<SearchUserPreviewResponse>
)

