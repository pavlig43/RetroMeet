package com.pavlig43.retromeet.di.friend

import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetdata.friend.api.FriendApi
import com.pavlig43.retromeetdata.friend.api.FriendRequestApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
object FriendModule {
    @Provides
    @ViewModelScoped
    fun provideFriendApi(okHttpClient: OkHttpClient?): FriendApi {
        return FriendApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }

    @Provides
    @ViewModelScoped
    fun provideFriendRequestApi(okHttpClient: OkHttpClient?): FriendRequestApi {
        return FriendRequestApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }
}
