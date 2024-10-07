package com.example.weathercomparisonapp.models

data class KoreaWeatherResponse(
    val response: ResponseWrapper
)

data class ResponseWrapper(
    val body: Body
)

data class Body(
    val items: Items
)

data class Items(
    val item: List<Item>
)

data class Item(
    val baseDate: String,
    val baseTime: String,
    val category: String,      // 예보 요소 (기온, 강수량 등)
    val fcstValue: String      // 예보 값 (기온, 강수량 등)
)
