package com.avivba.simbanyu.data.utils

import com.avivba.simbanyu.domain.base.DataError
import com.avivba.simbanyu.domain.base.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException


suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> T
): Result<T, DataError> {
    return withContext(dispatcher) {
        try {
            Result.Success(apiCall())
        } catch (t: Throwable) {
            val errorCode = when (t) {
                is IOException -> {
                    DataError.NetworkError.NO_INTERNET
                }

                is HttpException -> {
                    val code = t.code()
                    when (code) {
                        401 -> {
                            DataError.AuthError
                        }

                        // TODO: Handle more error codes

                        else -> {
                            DataError.NetworkError.SERVER_ERROR
                        }
                    }
                }

                else -> {
                    DataError.NetworkError.UNKNOWN
                }
            }

            Result.Error(errorCode)
        }
    }
}