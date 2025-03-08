package com.example.ailad.data

import kotlin.Exception

sealed interface NetworkResponse<T : Any>

class Success<T : Any>(val data: T) : NetworkResponse<T>
class Error<T : Any>(val code: Int, val message: String? = null) : NetworkResponse<T>
class Exception<T : Any>(val e: Exception) : NetworkResponse<T>

