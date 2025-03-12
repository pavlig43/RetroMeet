package com.pavlig43.retromeetdata.dialog

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.dialog.api.DialogApi
import com.pavlig43.retromeetdata.dialog.model.DialogResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import jakarta.inject.Inject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class DialogsRepository @Inject constructor(
    private val dataStore: DateStoreSettings,
    private val database: RetromeetDataBase,
    private val logger: Logger,
    private val dialogApi: DialogApi
) {

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
    fun getDialogs(): Flow<PagingData<DialogResponse>> {
        return dataStore.userId.map { userId ->
            Pager(
                config = PagingConfig(PAGE_SIZE),
                pagingSourceFactory = { database.dialogsDao.pagingDialogs(userId) },
                remoteMediator = DialogsPagingMediator(
                    dialogApi = dialogApi,
                    database = database,
                    userId = userId,
                )
            ).flow
        }.flatMapLatest { it }
    }
    companion object {
        private const val TAG = "Dialog Repository"
        private const val PAGE_SIZE = 10
    }
}
