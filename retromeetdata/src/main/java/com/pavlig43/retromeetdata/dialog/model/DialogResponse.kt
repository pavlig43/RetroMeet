package com.pavlig43.retromeetdata.dialog.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity("dialogs")
data class DialogResponse(

    @SerialName("dialog_info")
    @Embedded(prefix = "dialog_")
    val info: DialogInfo,

    @SerialName("user_info")
    @Embedded(prefix = "user_")
    val userInfo: DialogUserPreview,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("local_id")
    val localId: Long = 0
)

@Serializable
data class DialogInfo(
    @SerialName("server_id")
    @ColumnInfo("server_id")
    val serverId: Long? = null,

    @SerialName("user_id")
    @ColumnInfo("user_id")
    val userId: Int,

    @SerialName("friend_id")
    @ColumnInfo("friend_id")
    val friendId: Int,

    @SerialName("updated_at")
    @ColumnInfo("updated_at")
    val updatedAt: Long,
)

@Serializable
data class DialogsResponse(
    @SerialName("dialogs")
    @ColumnInfo("dialogs")
    val dialogs: List<DialogResponse>
)

@Serializable
data class DialogUserPreview(
    @SerialName("name")
    @ColumnInfo("name")
    val name: String?,

    @SerialName("main_photo")
    @ColumnInfo("main_photo")
    val mainPhoto: String?,

    @SerialName("is_online")
    @ColumnInfo("is_online")
    val isOnline: Boolean
)
