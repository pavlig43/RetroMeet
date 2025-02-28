package com.pavlig43.retromeetdata.searchuserRepository.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pavlig43.retromeetdata.common.FRIEND_ID
import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.common.Gender
import com.pavlig43.retromeetdata.common.USER_ID
import com.pavlig43.retromeetdata.userinfoRepository.model.FriendStatusResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "all_users", primaryKeys = [USER_ID, FRIEND_ID])
@Serializable
data class FriendPreviewResponse(

    @ColumnInfo(USER_ID)
    val userId:Int = 0,

    @ColumnInfo(FRIEND_ID)
    @SerialName(FRIEND_ID)
    val friendId: Int,

    @ColumnInfo("gender")
    @SerialName("gender")
    val gender: Gender?,

    @ColumnInfo("main_photo_path")
    @SerialName("main_photo_path")
    val mainPhotoPath: String?,

    @ColumnInfo("name")
    @SerialName("name")
    val name: String?,

    @ColumnInfo("city")
    @SerialName("city")
    val city: String?,

    @ColumnInfo("date_of_birth")
    @SerialName("date_of_birth")
    val dateOfBirth: Long?,

    @ColumnInfo("is_online")
    @SerialName("is_online")
    val isOnline: Boolean,

    @Embedded("friend_")
    @SerialName("friend_status")
    val friendStatus: FriendStatusResponse = FriendStatusResponse(FriendStatus.NO_DATA,null)


)

@Serializable
data class SearchUsersResponse(
    @Serializable
    val users: List<FriendPreviewResponse>
)

