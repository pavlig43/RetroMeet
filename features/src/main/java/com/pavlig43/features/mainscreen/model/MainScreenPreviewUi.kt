package com.pavlig43.features.mainscreen.model

import com.pavlig43.features.common.enumui.model.GenderUi

data class MainScreenPreviewUi(
    val loginId: Int,
    val gender: GenderUi? = null,
    val mainPhotoPath: String? = null,
    val name: String? = null,
    val age: Int?
)

