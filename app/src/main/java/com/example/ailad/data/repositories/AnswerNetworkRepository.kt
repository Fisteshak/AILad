package com.example.ailad.data.repositories

import android.util.Log
import com.example.ailad.data.Error
import com.example.ailad.data.Exception
import com.example.ailad.data.NetworkResponse
import com.example.ailad.data.Success
import com.example.ailad.data.api.RTULabApi
import com.example.ailad.data.entities.MessageNetworkEntity
import com.example.ailad.data.entities.RequestDTO
import java.io.IOException
import javax.inject.Inject

class AnswerNetworkRepository @Inject constructor(
    private val api: RTULabApi
) {
    suspend fun fetchAnswer(prompt: String): NetworkResponse<MessageNetworkEntity> {
        return try {
            Log.d("AnswerNetworkRepository", "generating message with prompt: $prompt")

            val response = api.generateAnswer(RequestDTO(prompt))
            if (response.code() == 200) {
                Log.d("AnswerNetworkRepository", "response body: ${response.body().toString()}")

                val message = response.body()
                if (message != null) {
                    Log.d("AnswerNetworkRepository", "got successful response: $message.text")
                    Success(message)
                } else {
                    Log.d("AnswerNetworkRepository", "Error: response body is null")
                    Error(1)
                }
            } else {
                Log.d("AnswerNetworkRepository", "got response with code: ${response.code()}; message: ${response.message()}")
                Error(response.code())

            }
        } catch (e: IOException) {
            Log.d("AnswerNetworkRepository", "got exception: ${e.message}")

            Exception(e)
        }
    }
}