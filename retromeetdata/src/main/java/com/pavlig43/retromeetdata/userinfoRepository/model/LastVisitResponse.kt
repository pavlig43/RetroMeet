package com.pavlig43.retromeetdata.userinfoRepository.model

import kotlinx.serialization.Serializable

@Serializable
data class LastVisitResponse(
    val time:Long?
)
