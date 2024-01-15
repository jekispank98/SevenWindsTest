package com.example.domain.model

data class MenuModel(
    val id: Int,
    val imageURL: String,
    val name: String,
    val price: Int,
    var count: Int = 0
)