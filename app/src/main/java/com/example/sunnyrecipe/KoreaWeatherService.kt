package com.example.weathercomparisonapp.network

import com.example.weathercomparisonapp.models.KoreaWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface KoreaWeatherService {
    @GET("getVilageFcst")
    suspend fun getWeatherData(
        @Query("serviceKey") apiKey: String,     // API 키
        @Query("dataType") dataType: String,     // 데이터 형식 (JSON)
        @Query("base_date") baseDate: String,    // 기준 날짜 (YYYYMMDD)
        @Query("base_time") baseTime: String,    // 기준 시간 (HHmm)
        @Query("nx") nx: Int,                    // X 좌표
        @Query("ny") ny: Int                     // Y 좌표
    ): Response<KoreaWeatherResponse>
}
