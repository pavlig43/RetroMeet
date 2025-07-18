package com.pavlig43.retromeet.di

import android.content.Context
import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetcommon.AndroidLogger
import com.pavlig43.retromeetcommon.AppDispatcher
import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.dialog.ObserveUnreadDialogRepository
import com.pavlig43.retromeetdata.online.OnlineRepository
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DateStoreSettings {
        return DateStoreSettings(context)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): RetromeetDataBase {
        return RetromeetDataBase(context)
    }

    @Suppress("FunctionOnlyReturningConstant")
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient? {
        return null
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return AndroidLogger()
    }

    @Provides
    @Singleton
    fun provideDispatcher(): AppDispatcher {
        return AppDispatcher()
    }

    @Provides
    @Singleton
    fun provideWsUrl(): String {
        return BuildConfig.RETROMEET_WS
    }

    @Provides
    @Singleton
    fun provideOnlineRepository(wsUrl: String, dateStore: DateStoreSettings): OnlineRepository {
        return OnlineRepository(wsUrl, dateStore)
    }

    @Provides
    @Singleton
    fun provideObserveUnreadMessageRepository(
        wsUrl: String,
        dateStore: DateStoreSettings
    ): ObserveUnreadDialogRepository {
        return ObserveUnreadDialogRepository(wsUrl, dateStore)
    }
}
