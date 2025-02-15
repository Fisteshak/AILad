package com.example.ailad.data.repositories

import com.example.ailad.data.db.MessageDao
import javax.inject.Inject

class AnswerLocalRepository @Inject constructor(
    private val dao: MessageDao
)