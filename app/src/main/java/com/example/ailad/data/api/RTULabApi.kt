package com.example.ailad.data.api

import com.example.ailad.data.entities.MessageNetworkEntity
import com.example.ailad.data.entities.Prompt
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RTULabApi {
    @POST("generate")
    suspend fun generateAnswer(@Body prompt: Prompt): Response<MessageNetworkEntity>
}