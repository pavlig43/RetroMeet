package com.pavlig43.retromeetdata.userInfo.model

import kotlinx.serialization.Serializable

@Serializable
data class LastVisitResponse(
    val time: Long?
)
