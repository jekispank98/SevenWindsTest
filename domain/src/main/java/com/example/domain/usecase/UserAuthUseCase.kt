package com.example.domain.usecase

import com.example.domain.model.RegisterModel
import com.example.domain.repo.LoginRepo

class UserAuthUseCase(private val loginRepo: LoginRepo) {

    fun getUserAuth(registerModel: RegisterModel): Boolean {
        return loginRepo.processUserAuth(registerModel)
    }
}