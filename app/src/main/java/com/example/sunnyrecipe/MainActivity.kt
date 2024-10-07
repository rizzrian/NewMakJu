package com.example.weathercomparisonapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.android.car.ui.toolbar.MenuItem
import com.example.sunnyrecipe.R
import com.example.weathercomparisonapp.viewmodel.WeatherViewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: WeatherViewModel by viewModels()

    private lateinit var regionSpinner: Spinner
    private lateinit var koreaTemperature: TextView
    private lateinit var norwayTemperature: TextView
    private lateinit var koreaWeatherIcon: ImageView
    private lateinit var norwayWeatherIcon: ImageView

    // Region list (major South Korean cities)
    private val regions = listOf(
        "Seoul" to Pair(60, 127),
        "Busan" to Pair(98, 76),
        "Daegu" to Pair(89, 90),
        "Incheon" to Pair(55, 124),
        "Gwangju" to Pair(58, 74),
        "Daejeon" to Pair(67, 100),
        "Ulsan" to Pair(102, 84),
        "Sejong" to Pair(66, 103)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        koreaTemperature = findViewById(R.id.koreaTemperature)
        norwayTemperature = findViewById(R.id.norwayTemperature)
        koreaWeatherIcon = findViewById(R.id.koreaWeatherIcon)
        norwayWeatherIcon = findViewById(R.id.norwayWeatherIcon)

        // Spinner for region selection
        val regionNames = regions.map { it.first }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, regionNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        regionSpinner.adapter = adapter

        // Region selection handler
        regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View, position: Int, id: Long) {
                val selectedRegion = regions[position]
                val (nx, ny) = selectedRegion.second
                fetchKoreaWeather(nx, ny)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Observe Norway weather
        /*
        viewModel.norwayWeather.observe(this, Observer { norwayWeather ->
            norwayWeather?.let {
                val temperature = it.timeseries[0].data.instant.details.air_temperature
                norwayTemperature.text = "Norway Temp: $temperature°C"
                updateWeatherIcon(temperature, "norway", norwayWeatherIcon)
            }
        })


         */
        // Observe Korea weather
        viewModel.koreaWeather.observe(this, Observer { koreaWeather ->
            koreaWeather?.let {
                val temperatureItem = it.response.body.items.item.firstOrNull { item -> item.category == "T1H" }
                temperatureItem?.let {
                    val temperature = temperatureItem.fcstValue.toDoubleOrNull()
                    temperature?.let {
                        koreaTemperature.text = "Korea Temp: $temperature°C"
                        updateWeatherIcon(temperature, "korea", koreaWeatherIcon)
                    }
                }
            }
        })

        // Initial call for Norway weather
        viewModel.fetchNorwayWeather(59.91, 10.75) // Coordinates for Oslo, Norway
    }

    private fun fetchKoreaWeather(nx: Int, ny: Int) {
        viewModel.fetchKoreaWeather(
            "mgWFrJu_TDKFhaybvwwyfQ",  // Your API key
            "20241002",  // Current date
            "0600",  // Reference time
            nx, ny
        )
    }

    private fun updateWeatherIcon(temperature: Double, condition: String, imageView: ImageView) {
        when {
            temperature >= 25 -> imageView.setImageResource(R.drawable.sunny)
            temperature in 0.0..24.9 -> imageView.setImageResource(R.drawable.cloud)
            else -> imageView.setImageResource(R.drawable.snow)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_weather, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_refresh -> {
                refreshWeatherData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun refreshWeatherData() {
        viewModel.fetchNorwayWeather(59.91, 10.75) // Oslo, Norway
        val selectedRegion = regions[regionSpinner.selectedItemPosition]
        fetchKoreaWeather(selectedRegion.second.first, selectedRegion.second.second)  // Fetch weather for selected region
    }
}
