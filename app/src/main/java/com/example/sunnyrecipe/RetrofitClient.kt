package com.example.weathercomparisonapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val NORWAY_BASE_URL = "https://api.met.no/"
    private const val KOREA_BASE_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/"

    private val retrofitNorway = Retrofit.Builder()
        .baseUrl(NORWAY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val retrofitKorea = Retrofit.Builder()
        .baseUrl(KOREA_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val norwayWeatherService: NorwayWeatherService = retrofitNorway.create(NorwayWeatherService::class.java)
    val koreaWeatherService: KoreaWeatherService = retrofitKorea.create(KoreaWeatherService::class.java)
}
