package com.pavlig43.retromeetdata.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.withTransaction
import com.pavlig43.retromeetdata.friendRepository.database.FriendDao
import com.pavlig43.retromeetdata.loginRepository.database.LoginDao
import com.pavlig43.retromeetdata.loginRepository.model.LoginRequest
import com.pavlig43.retromeetdata.searchuserRepository.database.SearchDao
import com.pavlig43.retromeetdata.searchuserRepository.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserFilterRequest


class RetromeetDataBase internal constructor(private val newsRoomDataBase: RetromeetRoomDatabase) {

    suspend fun <R> withTransaction(block: suspend () -> R): R {
        return newsRoomDataBase.withTransaction {
            block()
        }
    }

    val loginDao: LoginDao
        get() = newsRoomDataBase.loginDao()
    val searchDao: SearchDao
        get() = newsRoomDataBase.searchDao()
    val friendDao: FriendDao
        get() = newsRoomDataBase.friendDao()


}

@Database(
    entities = [
        LoginRequest::class,
        SearchUserFilterRequest::class,
        FriendPreviewResponse::class,

    ],
    version = 1
)
@TypeConverters(
    EnumListConverters.GenderConverter::class,
    EnumListConverters.OrientationConverter::class,
    EnumListConverters.ReligionConverter::class,
    EnumListConverters.EyeColorConverter::class,
    EnumListConverters.HairColorConverter::class,
    EnumListConverters.EducationConverter::class,
    EnumListConverters.PetConverter::class,
    EnumListConverters.MusicGenreConverter::class,
)
internal abstract class RetromeetRoomDatabase : RoomDatabase() {
    abstract fun loginDao(): LoginDao
    abstract fun searchDao(): SearchDao
    abstract fun friendDao(): FriendDao

}

fun RetromeetDataBase(context: Context): RetromeetDataBase {
    val roomDataBase = Room.databaseBuilder(
        checkNotNull(context.applicationContext),
        RetromeetRoomDatabase::class.java,
        "retromeet"
    ).build()
    return RetromeetDataBase(roomDataBase)
}
