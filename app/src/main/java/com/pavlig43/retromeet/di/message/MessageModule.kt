package com.pavlig43.retromeet.di.message

import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetdata.dialog.api.DialogApi
import com.pavlig43.retromeetdata.message.api.MessageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
object MessageModule {

    @Provides
    @ViewModelScoped
    fun provideMessageApi(okHttpClient: OkHttpClient?): MessageApi {
        return MessageApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }

    @Provides
    @ViewModelScoped
    fun provideDialogApi(okHttpClient: OkHttpClient?): DialogApi {
        return DialogApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }
}
