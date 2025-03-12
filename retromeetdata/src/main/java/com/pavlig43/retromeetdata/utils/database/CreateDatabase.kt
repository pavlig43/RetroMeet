package com.pavlig43.retromeetdata.utils.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.withTransaction
import com.pavlig43.retromeetdata.dialog.database.DialogsDao
import com.pavlig43.retromeetdata.dialog.database.DialogsKeysDao
import com.pavlig43.retromeetdata.dialog.database.DialogsRemoteKey
import com.pavlig43.retromeetdata.dialog.model.DialogResponse
import com.pavlig43.retromeetdata.friend.database.FriendDao
import com.pavlig43.retromeetdata.login.database.LoginDao
import com.pavlig43.retromeetdata.login.model.LoginRequest
import com.pavlig43.retromeetdata.message.database.MessageDao
import com.pavlig43.retromeetdata.message.database.MessagesRemoteKey
import com.pavlig43.retromeetdata.message.database.MessagesRemoteKeysDao
import com.pavlig43.retromeetdata.message.model.MessageResponse
import com.pavlig43.retromeetdata.searchUser.database.SearchDao
import com.pavlig43.retromeetdata.searchUser.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.searchUser.model.SearchUserFilterRequest

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
    val usersRemoteKeysDao: UsersRemoteKeysDao
        get() = newsRoomDataBase.usersRemoteKeysDao()
    val messageDao: MessageDao
        get() = newsRoomDataBase.messageDao()
    val messagesRemoteKeysDao: MessagesRemoteKeysDao
        get() = newsRoomDataBase.messagesRemoteKeysDao()
    val dialogsDao: DialogsDao
        get() = newsRoomDataBase.dialogsDao()
    val dialogsKeysDao: DialogsKeysDao
        get() = newsRoomDataBase.dialogsKeyDao()
}

@Database(
    entities = [
        LoginRequest::class,
        SearchUserFilterRequest::class,
        FriendPreviewResponse::class,
        UsersRemoteKey::class,
        MessageResponse::class,
        MessagesRemoteKey::class,
        DialogResponse::class,
        DialogsRemoteKey::class

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
    abstract fun usersRemoteKeysDao(): UsersRemoteKeysDao
    abstract fun messageDao(): MessageDao
    abstract fun messagesRemoteKeysDao(): MessagesRemoteKeysDao
    abstract fun dialogsDao(): DialogsDao
    abstract fun dialogsKeyDao(): DialogsKeysDao
}

fun RetromeetDataBase(context: Context): RetromeetDataBase {
    val roomDataBase = Room.databaseBuilder(
        checkNotNull(context.applicationContext),
        RetromeetRoomDatabase::class.java,
        "retromeet"
    ).build()
    return RetromeetDataBase(roomDataBase)
}
