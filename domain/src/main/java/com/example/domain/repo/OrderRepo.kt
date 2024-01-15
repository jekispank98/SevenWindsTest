package com.example.domain.repo

import com.example.domain.model.MenuModel

interface OrderRepo {

    suspend fun getMenu(id: Int): List<MenuModel>

}