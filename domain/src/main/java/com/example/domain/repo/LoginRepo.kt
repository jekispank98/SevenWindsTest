package com.example.domain.repo

import com.example.domain.model.RegisterModel

interface LoginRepo {

    suspend fun processUserRegistration(registerModel: RegisterModel): Boolean

    fun processUserAuth(registerModel: RegisterModel): Boolean
}