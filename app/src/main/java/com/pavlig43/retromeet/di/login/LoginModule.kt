package com.pavlig43.retromeet.di.login

import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetdata.login.api.LoginApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {
    @Provides
    @ViewModelScoped
    fun provideLoginApi(okHttpClient: OkHttpClient?): LoginApi {
        return LoginApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }
}
