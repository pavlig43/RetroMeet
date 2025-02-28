package com.pavlig43.features.userInfo.model

import com.pavlig43.features.common.enumui.model.FriendStatusUi
import com.pavlig43.features.userInfo.nested.resume.model.ResumeUi

data class UserInfoUi(
    val resumeUi: ResumeUi,
    val friendStatusUi: FriendStatusUi,
    val lastVisit:String?
)
