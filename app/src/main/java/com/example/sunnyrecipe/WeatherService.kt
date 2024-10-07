package com.example.weathercomparisonapp.api


import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("VilageFcstInfoService_2.0/getVilageFcst")
    fun getKoreaWeather(
        @Query("serviceKey") apiKey: String,
        @Query("numOfRows") numOfRows: Int,
        @Query("pageNo") pageNo: Int,
        @Query("base_date") baseDate: String,
        @Query("base_time") baseTime: String,
        @Query("nx") nx: Int,
        @Query("ny") ny: Int
    )
}