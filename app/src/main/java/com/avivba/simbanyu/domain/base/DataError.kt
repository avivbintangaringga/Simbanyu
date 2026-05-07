package com.avivba.simbanyu.domain.base

sealed interface DataError : Error {
    enum class NetworkError : DataError {
        NO_INTERNET,
        NOT_FOUND,
        SERVER_ERROR,
        UNAUTHORIZED,
        TOO_LARGE,
        UNKNOWN
    }

    object NotFound : DataError
}