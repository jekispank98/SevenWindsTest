package com.example.domain.usecase

import android.util.Log
import com.example.domain.model.NearestLocationModel
import com.example.domain.repo.LocationRepo

class GetNearestCoffeeShopsUseCase(private val locationRepo: LocationRepo) {

    suspend fun getNearestCoffeeShops(): List<NearestLocationModel> {
        val shops = locationRepo.getNearestCoffeeShops()
        Log.d("LIST_OF_POINTS", "Points are $shops")
        return shops
    }
}