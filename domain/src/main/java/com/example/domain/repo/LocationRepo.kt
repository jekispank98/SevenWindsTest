package com.example.domain.repo

import com.example.domain.model.NearestLocationModel
import com.example.domain.model.Point

interface LocationRepo {

    suspend fun getCurrentLocation(): Point?

    suspend fun getNearestCoffeeShops(): List<NearestLocationModel>


}