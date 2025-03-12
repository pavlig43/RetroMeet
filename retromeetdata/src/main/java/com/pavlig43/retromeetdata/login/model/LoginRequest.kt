package com.pavlig43.retromeetdata.login.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity("login")
data class LoginRequest(

    @ColumnInfo("login")
    @SerialName("login")
    val login: String,

    @ColumnInfo("password")
    @SerialName("password")
    val password: String,

    @PrimaryKey
    @ColumnInfo("id")
    @SerialName("id")
    val id: Int = 0,

)
