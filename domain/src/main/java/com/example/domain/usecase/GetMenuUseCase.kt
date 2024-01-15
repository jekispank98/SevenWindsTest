package com.example.domain.usecase

import com.example.domain.model.MenuModel
import com.example.domain.repo.OrderRepo

class GetMenuUseCase(private val orderRepo: OrderRepo) {
    suspend fun getMenu(id: Int): List<MenuModel> {
        return orderRepo.getMenu(id)
    }
}