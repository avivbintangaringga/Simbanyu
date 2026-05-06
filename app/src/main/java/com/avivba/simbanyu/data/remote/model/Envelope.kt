package com.avivba.simbanyu.data.remote.model

data class Envelope<T>(
    val status: String,
    val message: String,
    val data: T?,
    val errors: List<String>?
)