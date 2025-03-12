package com.pavlig43.retromeet.di.searchuser

import com.pavlig43.retromeet.BuildConfig
import com.pavlig43.retromeetdata.searchUser.api.SearchUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient

@Module
@InstallIn(ViewModelComponent::class)
class SearchUserModule {
    @Provides
    @ViewModelScoped
    fun provideSearchUserApi(okHttpClient: OkHttpClient?): SearchUserApi {
        return SearchUserApi(
            baseUrl = BuildConfig.RETROMEET_API_BASE_URL,
            client = okHttpClient
        )
    }
}
