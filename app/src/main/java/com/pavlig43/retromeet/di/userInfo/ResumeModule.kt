package com.pavlig43.retromeet.di.userInfo

import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetdata.userinfoRepository.api.ResumeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient


@Module
@InstallIn(ViewModelComponent::class)
class ResumeModule {

    @Provides
    @ViewModelScoped
    fun provideResumeApi(okHttpClient: OkHttpClient?): ResumeApi {
        return ResumeApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }
}


