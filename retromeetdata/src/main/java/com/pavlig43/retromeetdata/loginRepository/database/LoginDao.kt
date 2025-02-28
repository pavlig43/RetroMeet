package com.pavlig43.retromeetdata.loginRepository.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.pavlig43.retromeetdata.loginRepository.model.LoginRequest
import kotlinx.coroutines.flow.Flow

@Dao
interface LoginDao {
    @Query("SELECT * FROM login  ")
    fun getAllLogin(): Flow<List<LoginRequest>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLogin(login: LoginRequest)



}
