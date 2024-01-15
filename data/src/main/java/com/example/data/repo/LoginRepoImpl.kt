package com.example.data.repo

import com.example.data.SharPreferences
import com.example.data.api.ApiService
import com.example.domain.model.RegisterModel
import com.example.domain.model.RegisterResponseModel
import com.example.domain.repo.LoginRepo
import com.google.gson.Gson

class LoginRepoImpl(private val apiService: ApiService): LoginRepo {
    private val _gson: Gson by lazy { Gson() }
    private val _sharedPreferences = SharPreferences
    override suspend fun processUserRegistration(registerModel: RegisterModel): Boolean {

        val response = apiService.getUserRegistration(registerModel)
        return try {
            val regModel = _gson.fromJson(response.string(), RegisterResponseModel::class.java)
            if (!regModel.token.isNullOrEmpty()) {
                _sharedPreferences.apply {
                    setToken(regModel.token.toString())
                }
            }
            true
        } catch (e: Exception) {
            println(e.message)
            false
        }
    }

    override fun processUserAuth(registerModel: RegisterModel): Boolean {
        val response = apiService.getUserAuth(registerModel)
        return response.execute().isSuccessful
    }
}