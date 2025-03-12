package com.pavlig43.retromeet.di.mainScreen

import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetdata.mainScreenPreview.api.MainScreenPreviewApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
object MainScreenPreviewModule {
    @Provides
    @ViewModelScoped
    fun provideMainScreenPreviewApi(okHttpClient: OkHttpClient?): MainScreenPreviewApi {
        return MainScreenPreviewApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }
}
