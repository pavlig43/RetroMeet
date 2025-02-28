package com.pavlig43.retromeet.di.register

import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetdata.registerRepository.api.RegisterApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
class RegisterModule {

    @Provides
    @ViewModelScoped
    fun provideRegisterApi(okHttpClient: OkHttpClient?): RegisterApi {
        return RegisterApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient

        )
    }
}
