package com.example.data.repo

import android.util.Log
import com.example.data.SharPreferences
import com.example.data.api.ApiService
import com.example.domain.model.MenuModel
import com.example.domain.model.NearestLocationModel
import com.example.domain.repo.OrderRepo
import com.google.gson.Gson
import com.google.gson.JsonParser

class OrderRepoImpl(private val apiService: ApiService): OrderRepo {
    private val _gson: Gson by lazy { Gson() }
    private val _sharedPreferences = SharPreferences
    override suspend fun getMenu(id: Int): List<MenuModel> {
        val list = emptyList<MenuModel>().toMutableList()
        val token = "Bearer " + _sharedPreferences.getToken()
        return try {
            if (token != null) {
                val response = apiService.getMenu(token, id)
                val jsonMenu = JsonParser().parse(response.string()).asJsonArray

                jsonMenu.forEach {
                    list.add(_gson.fromJson(it, MenuModel::class.java))
                }
            }
            list
        } catch (e: Exception) {
            println(e.message)
            list
        }
    }
}