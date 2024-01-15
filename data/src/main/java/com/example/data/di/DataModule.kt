package com.example.data.di

import com.example.data.api.ApiService
import com.example.data.repo.LocationRepoImpl
import com.example.data.repo.LoginRepoImpl
import com.example.data.repo.OrderRepoImpl
import com.example.domain.repo.LocationRepo
import com.example.domain.repo.LoginRepo
import com.example.domain.repo.OrderRepo
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


private const val BASE_URL = "http://147.78.66.203:3210/"

val dataModule = module {

    single<ApiService> {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    single<LoginRepo> { LoginRepoImpl(apiService = get()) }

    single<LocationRepo> { LocationRepoImpl(apiService = get(), application = get()) }

    single<OrderRepo> { OrderRepoImpl(apiService = get()) }
}