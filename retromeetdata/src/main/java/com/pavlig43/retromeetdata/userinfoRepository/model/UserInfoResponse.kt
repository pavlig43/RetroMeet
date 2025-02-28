package com.pavlig43.retromeetdata.userinfoRepository.model

import com.pavlig43.retromeetdata.common.FriendStatus
import com.pavlig43.retromeetdata.resumeRepository.model.ResumeResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoResponse(
    @SerialName("resume")
    val resume: ResumeResponse,
    @SerialName("friend_status")
    val friendStatus: FriendStatusResponse,
    @SerialName("last_visit")
    val lastVisit: LastVisitResponse

)