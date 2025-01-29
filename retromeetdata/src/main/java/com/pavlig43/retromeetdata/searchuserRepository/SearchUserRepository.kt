package com.pavlig43.retromeetdata.searchuserRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData

import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserPreviewResponse
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserRequest
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResultWithMap
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class SearchUserRepository @Inject constructor(
    private val searchPagingSourceFactory: SearchPagingSourceFactory,
) {
     fun getUsers(
        request: SearchUserRequest,
    ): Flow<PagingData<SearchUserPreviewResponse>> {
        return Pager(
            config = PagingConfig(PAGE_SIZE),
            pagingSourceFactory = {searchPagingSourceFactory.create(request)}
        ).flow

    }
    companion object {
        private const val PAGE_SIZE = 10
    }
}

