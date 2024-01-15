package com.example.domain.model

data class RegisterResponseModel(
    val token: String?,
    val tokenLifetime: Long
)
