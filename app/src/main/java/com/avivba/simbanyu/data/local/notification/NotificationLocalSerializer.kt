package com.avivba.simbanyu.data.local.notification

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
object NotificationLocalSerializer : Serializer<NotificationLocalData> {
    override val defaultValue = NotificationLocalData()

    override suspend fun readFrom(input: InputStream): NotificationLocalData {
        try {
            return Json.decodeFromStream<NotificationLocalData>(input)
        } catch (se: SerializationException) {
            throw CorruptionException("Unable to read settings", se)
        }
    }

    override suspend fun writeTo(
        t: NotificationLocalData,
        output: OutputStream
    ) {
        Json.encodeToStream(t, output)
    }
}
