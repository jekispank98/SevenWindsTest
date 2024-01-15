package com.example.data.repo

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.example.data.SharPreferences
import com.example.data.api.ApiService
import com.example.domain.model.NearestLocationModel
import com.example.domain.model.Point
import com.example.domain.repo.LocationRepo
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.lang.Exception
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class LocationRepoImpl(private val apiService: ApiService, private val application: Application) :
    LocationRepo {
    private val _sharedPreferences = SharPreferences
    private val _gson: Gson by lazy { Gson() }

    override suspend fun getCurrentLocation(): Point? {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)
        val isLocationPermission = checkLocationPermission(application)
        if (!isLocationPermission) {
            return null
        } else {
            try {
                return withContext(Dispatchers.IO) {
                    return@withContext suspendCoroutine { continuation ->
                        fusedLocationClient.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            CancellationTokenSource().token
                        )
                            .addOnCompleteListener { task ->
                                val point = Point(
                                    task.result.latitude,
                                    task.result.longitude
                                )
                                continuation.resume(point)
                            }
                    }
                }
            } catch (e: Exception) {
                Log.d("GPS.ERROR", "Couldn't get coordinates: ${e.message}")
                return null
            }
        }
    }

    override suspend fun getNearestCoffeeShops(): List<NearestLocationModel> {
        val myLocation = getCurrentLocation()
        val list = emptyList<NearestLocationModel>().toMutableList()
        val token = "Bearer " + _sharedPreferences.getToken()
        return try {
            if (token != null) {
                val response = apiService.getNearestLocations(token)
                val json: JsonArray = JsonParser().parse(response.string()).asJsonArray

               json.forEach { point ->
                   val coffeePoint = Point(
                       point.asJsonObject.get("point").asJsonObject.get("latitude").asDouble,
                       point.asJsonObject.get("point").asJsonObject.get("longitude").asDouble
                   )
                    list.add(
                        NearestLocationModel(
                            point.asJsonObject.get("id").asInt,
                            point.asJsonObject.get("name").asString,
                            coffeePoint,
                            getDistanceBetween(myLocation, coffeePoint)
                        )
                    )
                }
//                Log.d("POINTS", "Points list is $list")
            }
            list
        } catch (e: Exception) {
            println(e.message)
            emptyList()
        }
    }

    private fun checkLocationPermission(application: Application): Boolean {
        return (ActivityCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
                == PackageManager.PERMISSION_GRANTED)
    }

    private fun getDistanceBetween(point1: Point?, point2: Point): Float {
        if (point1 != null) {
            val myLocation = Location("")
            myLocation.latitude = point1.latitude
            myLocation.longitude = point1.longitude

            val coffeeLocation = Location("")
            coffeeLocation.latitude = point2.latitude
            coffeeLocation.longitude = point2.longitude
            return (myLocation.distanceTo(coffeeLocation))/1000F
        }
        else return 0F
    }
}