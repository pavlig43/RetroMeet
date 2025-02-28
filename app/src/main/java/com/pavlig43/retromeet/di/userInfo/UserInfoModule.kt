package com.pavlig43.retromeet.di.userInfo

import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetdata.resumeRepository.api.ResumeApi
import com.pavlig43.retromeetdata.userinfoRepository.api.UserInfoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
class UserInfoModule {

    @Provides
    @ViewModelScoped
    fun provideUserInfoApi(okHttpClient: OkHttpClient?): UserInfoApi {
        return UserInfoApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }

    @Provides
    @ViewModelScoped
    fun provideResumeApi(okHttpClient: OkHttpClient?): ResumeApi {
        return ResumeApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }
}
