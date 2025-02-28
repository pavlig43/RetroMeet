package com.pavlig43.features.searchusersresult.model

import com.pavlig43.features.common.enumui.model.ShortGenderUi

data class SearchUserPreviewUi(
    val userId: Int,
    val gender: ShortGenderUi? = null,
    val mainPhotoPath: String? = null,
    val name: String? = null,
    val age: Int?,
    val city:String? = null,
    val isOnline: Boolean,
)

