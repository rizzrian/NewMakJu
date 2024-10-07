package com.example.weathercomparisonapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weathercomparisonapp.api.KmaWeatherResponse
import com.example.weathercomparisonapp.api.NorwayWeatherResponse
import com.example.weathercomparisonapp.api.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel : ViewModel() {

    private val _norwayWeather = MutableLiveData<NorwayWeatherResponse>()
    val norwayWeather: LiveData<NorwayWeatherResponse> = _norwayWeather

    private val _koreaWeather = MutableLiveData<KmaWeatherResponse>()
    val koreaWeather: LiveData<KmaWeatherResponse> = _koreaWeather

    fun fetchNorwayWeather(lat: Double, lon: Double) {
        val service = WeatherService.create()
        service.getNorwayWeather(lat, lon).enqueue(object : Callback<NorwayWeatherResponse> {
            override fun onResponse(call: Call<NorwayWeatherResponse>, response: Response<NorwayWeatherResponse>) {
                _norwayWeather.value = response.body()
            }

            override fun onFailure(call: Call<NorwayWeatherResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    fun fetchKoreaWeather(apiKey: String, baseDate: String, baseTime: String, nx: Int, ny: Int) {
        val service = WeatherService.create()
        service.getKoreaWeather(apiKey, baseDate, baseTime, nx, ny)
            .enqueue(object : Callback<KmaWeatherResponse> {
                override fun onResponse(call: Call<KmaWeatherResponse>, response: Response<KmaWeatherResponse>) {
                    _koreaWeather.value = response.body()
                }

                override fun onFailure(call: Call<KmaWeatherResponse>, t: Throwable) {
                    // Handle failure
                }
            })
    }
}
