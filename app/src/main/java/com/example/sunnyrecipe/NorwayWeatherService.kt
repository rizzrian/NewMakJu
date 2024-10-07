package com.example.weathercomparisonapp.network

import com.example.weathercomparisonapp.models.NorwayWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NorwayWeatherService {
    @GET("weatherapi/locationforecast/2.0/compact")
    suspend fun getWeatherData(
        @Query("lat") latitude: Double,   // 위도
        @Query("lon") longitude: Double   // 경도
    ): Response<NorwayWeatherResponse>
}
