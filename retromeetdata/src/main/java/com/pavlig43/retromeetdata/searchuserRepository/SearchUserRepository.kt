package com.pavlig43.retromeetdata.searchuserRepository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.searchuserRepository.api.SearchUserApi


import com.pavlig43.retromeetdata.searchuserRepository.model.FriendPreviewResponse
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserFilterRequest
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest

class SearchUserRepository @Inject constructor(
//    private val searchPagingSourceFactory: SearchPagingSourceFactory,
    private val dataStore: DateStoreSettings,
    private val searchUserApi: SearchUserApi,
    private val database: RetromeetDataBase,
) {

    suspend fun saveSearchFilter(searchFilter: SearchUserFilterRequest) {
        database.searchDao.saveSearchFilter(searchFilter)
    }


    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
    fun getSearchUsers(): Flow<PagingData<FriendPreviewResponse>> {
        return database.searchDao.getSearchFilter()
            .combine(dataStore.userId) { filter: SearchUserFilterRequest?, userId ->
                Pager(
                    config = PagingConfig(PAGE_SIZE),
                    pagingSourceFactory = {
                        database.searchDao.getUsersWithFilter(userId)
                    },
                    remoteMediator =
                        SearchPagingMediator(
                            searchUserApi = searchUserApi,
                            database = database,
                            userId = userId,
                            filterRequest = filter ?: SearchUserFilterRequest(isOnline = false)
                        )

                ).flow
            }.flatMapLatest { it }
    }


    companion object {
        private const val PAGE_SIZE = 10
    }
}

