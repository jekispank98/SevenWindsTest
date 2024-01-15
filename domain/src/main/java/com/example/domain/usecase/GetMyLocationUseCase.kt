package com.example.domain.usecase

import com.example.domain.model.Point
import com.example.domain.repo.LocationRepo

class GetMyLocationUseCase(private val locationRepo: LocationRepo) {
    suspend fun getMyLocation(): Point? {
        return locationRepo.getCurrentLocation()
    }
}