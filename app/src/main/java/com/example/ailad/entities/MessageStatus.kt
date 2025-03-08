package com.example.ailad.entities

enum class MessageStatus(val id: Int) {
    Success(0),
    Error(1),
    WaitingForResponse(2),
    TimeoutError(3),
    ConnectionError(4),
}