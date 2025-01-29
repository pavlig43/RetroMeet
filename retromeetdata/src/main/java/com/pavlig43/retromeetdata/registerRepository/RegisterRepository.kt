package com.pavlig43.retromeetdata.registerRepository

import android.util.Log
import com.pavlig43.retromeetcommon.Logger
import com.pavlig43.retromeetdata.loginRepository.model.LoginRequest
import com.pavlig43.retromeetdata.registerRepository.api.RegisterApi
import com.pavlig43.retromeetdata.registerRepository.model.RegisterRequest
import com.pavlig43.retromeetdata.registerRepository.model.RegisterResponse
import com.pavlig43.retromeetdata.utils.database.RetromeetDataBase
import com.pavlig43.retromeetdata.utils.requestResult.RequestResult
import com.pavlig43.retromeetdata.utils.requestResult.toRequestResult
import jakarta.inject.Inject

class RegisterRepository @Inject constructor(
    private val dataBase: RetromeetDataBase,
    private val registerApi: RegisterApi,
    private val logger: Logger
) {

    suspend fun tryRegister(registerData: RegisterRequest): RequestResult<RegisterResponse> {

        val response = registerApi.register(registerData)
            .toRequestResult(
                TAG,
                logger,
            )

        if (response is RequestResult.Success){

            dataBase.loginDao.insertLogin(registerData.toLoginRequest(response.data?.loginId?:0))
        }
        return response
    }
    private fun RegisterRequest.toLoginRequest(id: Int): LoginRequest {
        return LoginRequest(login,password,id)
    }

    companion object {
        private const val TAG = "Register Repository"
    }
}
