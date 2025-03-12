package com.pavlig43.features.dialog.model

import kotlinx.serialization.Serializable

data class DialogResponseUi(
    val localId:Long,
    val info: DialogInfoUi,
    val userInfo: DialogUserPreviewUi
)


@Serializable
data class DialogInfoUi(
    val serverId: Long? = null,
    val userId: Int,
    val friendId: Int,
    val updatedAt: Long,
)


data class DialogUserPreviewUi(
    val name: String,
    val mainPhoto: String?,
    val isOnline: Boolean
)



