package com.pavlig43.retromeetdata.searchuserRepository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pavlig43.retromeetdata.searchuserRepository.api.SearchUserApi
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserRequest
import com.pavlig43.retromeetdata.searchuserRepository.model.SearchUserPreviewResponse

import jakarta.inject.Inject

class SearchPagingSource(
    private val searchUserApi: SearchUserApi,
    private val request: SearchUserRequest
) : PagingSource<Int, SearchUserPreviewResponse>() {
    override fun getRefreshKey(state: PagingState<Int, SearchUserPreviewResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchUserPreviewResponse> {
        val page = params.key ?: 1
        val pageSize = params.loadSize
        val usersResponse = searchUserApi.getUsers(request, page, pageSize)
        val loadResult: LoadResult<Int, SearchUserPreviewResponse> = if (usersResponse.isSuccessful) {
            val nextKey = if ((usersResponse.body()?.users?.size ?: 0) < page) null else page + 1

            val prevKey = if (page == 1) null else page - 1

            val usersData = usersResponse.body()?.users?:listOf()

            LoadResult.Page(usersData,
                prevKey,
                nextKey)
        } else {
            LoadResult.Error(Throwable(usersResponse.toString()))
        }

        return loadResult
    }
}
class SearchPagingSourceFactory @Inject constructor(
    private val searchUserApi: SearchUserApi
) {
    fun create(request: SearchUserRequest): SearchPagingSource {
        return SearchPagingSource(searchUserApi, request)
    }
}

