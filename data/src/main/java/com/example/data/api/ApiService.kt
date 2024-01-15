package com.example.data.api

import com.example.domain.model.RegisterModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @Headers("Content-Type: application/json")
    @POST("/auth/register")
    suspend fun getUserRegistration(
        @Body registerModel: RegisterModel
    ): ResponseBody


    @POST("/auth/login")
    fun getUserAuth(@Body registerModel: RegisterModel): Call<ResponseBody>


    @Headers("Content-Type: application/json")
    @GET("/locations")
    suspend fun getNearestLocations(
        @Header("Authorization") token: String
    ): ResponseBody


    @Headers("Content-Type: application/json")
    @GET("/location/{id}/menu")
    suspend fun getMenu(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ResponseBody
}