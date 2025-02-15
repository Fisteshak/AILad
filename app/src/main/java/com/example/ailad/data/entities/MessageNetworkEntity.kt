package com.example.ailad.data.entities

import com.google.gson.annotations.SerializedName

data class MessageNetworkEntity(
    @SerializedName("response")
    val text: String,
)
