package com.example.data

import android.content.Context
import android.content.SharedPreferences

object SharPreferences {
    private const val REG_TOKEN = "reg_token"
    private lateinit var _sharedPreferences: SharedPreferences

    fun init(context: Context) {
        _sharedPreferences = context.getSharedPreferences("PREFERRENCES", Context.MODE_PRIVATE)
    }

    fun setToken(token: String) {
        _sharedPreferences.edit().putString(REG_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return _sharedPreferences.getString(REG_TOKEN, null)
    }
}