package com.avivba.simbanyu.data.local.tokenmanager

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
import java.io.InputStream
import java.io.OutputStream

@OptIn(ExperimentalSerializationApi::class)
object TokenDataSerializer : Serializer<TokenData> {
    override val defaultValue = TokenData()

    override suspend fun readFrom(input: InputStream): TokenData {
        try {
            return Json.decodeFromStream<TokenData>(input)
        } catch (se: SerializationException) {
            throw CorruptionException("Unable to read settings", se)
        }
    }

    override suspend fun writeTo(
        t: TokenData,
        output: OutputStream
    ) {
        Json.encodeToStream(t, output)
    }
}

