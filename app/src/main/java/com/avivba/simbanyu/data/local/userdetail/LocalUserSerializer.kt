package com.avivba.simbanyu.data.local.userdetail

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
object LocalUserSerializer : Serializer<LocalUser> {
    override val defaultValue = LocalUser()

    override suspend fun readFrom(input: InputStream): LocalUser {
        try {
            return Json.decodeFromStream<LocalUser>(input)
        } catch (se: SerializationException) {
            throw CorruptionException("Unable to read settings", se)
        }
    }

    override suspend fun writeTo(
        t: LocalUser,
        output: OutputStream
    ) {
        Json.encodeToStream(t, output)
    }
}