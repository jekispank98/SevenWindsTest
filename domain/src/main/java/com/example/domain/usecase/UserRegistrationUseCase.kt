package com.example.domain.usecase

import com.example.domain.model.RegisterModel
import com.example.domain.repo.LoginRepo

class UserRegistrationUseCase(private val loginRepo: LoginRepo) {

    suspend fun getUserRegistration(registerModel: RegisterModel): Boolean {
        return loginRepo.processUserRegistration(registerModel)
    }
}