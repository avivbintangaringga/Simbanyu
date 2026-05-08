package com.avivba.simbanyu.data.utils

import android.util.Log
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
            Log.d("SDKSDK", "Executing api call: $apiCall")
            Result.Success(apiCall())
        } catch (t: Throwable) {
            Log.e("SDKSDK", "API Call Exception: $t")
            Log.e("SDKSDK", "API Call Exception Message: " + t.message)
            val errorCode = when (t) {
                is IOException -> {
                    DataError.NetworkError.NO_INTERNET
                }

                is HttpException -> {
                    val code = t.code()
                    t.response()?.let {
                        it.errorBody()?.let {
                            Log.e("SDKSDK", "HTTP Exception: " + it.string())
                        }
                    }
                    when (code) {
                        401 -> {
                            DataError.NetworkError.UNAUTHORIZED
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