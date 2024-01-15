package com.example.domain.model

data class NearestLocationModel(
    val id: Int,
    val name: String,
    val point: Point,
    var distanceTo: Float = 0F
)