package com.example.myweatherapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import com.example.myweatherapp.api.WeatherService

object RetrofitInstance {

    private val client = OkHttpClient.Builder().build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .client(client)
            .baseUrl("https://api.met.no/")  // 기본 URL 설정
            .addConverterFactory(GsonConverterFactory.create())  // Gson 사용
            .build()
    }

    val api: WeatherService by lazy {
        retrofit.create(WeatherService::class.java)
    }
}

class OkHttpClient {

}
