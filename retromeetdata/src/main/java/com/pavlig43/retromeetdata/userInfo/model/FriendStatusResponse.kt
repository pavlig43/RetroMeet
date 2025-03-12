package com.pavlig43.retromeetdata.userInfo.model

import androidx.room.ColumnInfo
import com.pavlig43.retromeetdata.common.FriendStatus
import kotlinx.serialization.Serializable

@Serializable
data class FriendStatusResponse(
    @ColumnInfo("status")
    val status: FriendStatus,
    @ColumnInfo("updated_at")
    val updatedAt: Long?
)
