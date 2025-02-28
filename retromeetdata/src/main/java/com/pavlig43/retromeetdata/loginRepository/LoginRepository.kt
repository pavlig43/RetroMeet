package com.pavlig43.retromeetdata.loginRepository

import com.pavlig43.retromeetdata.loginRepository.api.LoginApi
import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.DateStoreSettings
import com.pavlig43.retromeetdata.loginRepository.model.LoginRequest
import com.pavlig43.retromeetdata.loginRepository.model.LoginResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import jakarta.inject.Inject

class LoginRepository @Inject constructor(
    private val dataBase: RetromeetDataBase,
    private val dataStore: DateStoreSettings,
    private val loginApi: LoginApi,
    private val logger: Logger

) {

    suspend fun tryLogin(loginRequest: LoginRequest): RequestResult<LoginResponse> {
        val result = loginApi.login(loginRequest)
            .toRequestResult(
                TAG,
                logger = logger,
            )
        if (result is RequestResult.Success){
            dataBase.loginDao.insertLogin(loginRequest.copy(id = result.data?.userId?:0))
            result.data?.let { dataStore.setUserId(it.userId) }
        }
        return result
    }


    companion object {
        private const val TAG = "Login Repository"
    }
}
