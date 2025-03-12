package com.pavlig43.retromeetdata.login.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pavlig43.retromeetdata.login.model.LoginRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginDao {
    @Query("SELECT * FROM login  ")
    fun getAllLogin(): Flow<List<LoginRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogin(login: LoginRequest)
}
