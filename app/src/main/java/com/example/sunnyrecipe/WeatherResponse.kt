package com.example.weathercomparisonapp.models

data class NorwayWeatherResponse(
    val timeseries: List<TimeSeries>
)

data class TimeSeries(
    val time: String,
    val data: Data
)

data class Data(
    val instant: Instant
)

data class Instant(
    val details: Details
)

data class Details(
    val air_temperature: Double   // 기온
)
