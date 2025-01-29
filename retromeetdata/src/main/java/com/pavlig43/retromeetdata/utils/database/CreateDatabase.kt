package com.pavlig43.retromeetdata.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.pavlig43.retromeetdata.loginRepository.database.LoginDao
import com.pavlig43.retromeetdata.loginRepository.model.LoginRequest


class RetromeetDataBase internal constructor(private val newsRoomDataBase: RetromeetRoomDatabase) {
    val loginDao: LoginDao
        get() = newsRoomDataBase.loginDao()

}

@Database(
    entities = [
        LoginRequest::class,
    ],
    version = 1
)
@TypeConverters(StringListTypeConverter::class)
internal abstract class RetromeetRoomDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginDao

}

fun RetromeetDataBase(context: Context): RetromeetDataBase {
    val roomDataBase = Room.databaseBuilder(
        checkNotNull(context.applicationContext),
        RetromeetRoomDatabase::class.java,
        "retromeet"
    ).build()
    return RetromeetDataBase(roomDataBase)
}
