package com.example.Sahyog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.Sahyog.databinding.ActivityWeatherScreenBinding
import org.json.JSONObject
import kotlin.math.ceil

//https://api.openweathermap.org/data/2.5/weather?q=delhi&appid=d782c3641dcc9cbcb8528dfb0245b3e4

class WeatherScreen: AppCompatActivity() {
    private lateinit var binding: ActivityWeatherScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeatherScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val lat = intent.getStringExtra("Lat")
        val long = intent.getStringExtra("Long")
        getJsonData(lat , long)
    }

    private fun getJsonData(lat: String?, long: String?)
    {
        val queue = Volley.newRequestQueue(this)
        val APIKey = "d782c3641dcc9cbcb8528dfb0245b3e4"
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=${lat}&lon=${long}&appid=${APIKey}"
        val jsonRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                setValues(response)
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonRequest)
    }
    private fun setValues(response: JSONObject?) {
        binding.tvCity.text = response?.getString("name")
        binding.tvType.text = response?.getJSONArray("weather")?.getJSONObject(0)?.getString("main")
        var currTemp = response?.getJSONObject("main")?.getString("temp")
        currTemp = (((currTemp)?.toFloat()?.minus(273.15))?.toInt()).toString()
        binding.tvCurrTemp.text = currTemp
        var minTemp = response?.getJSONObject("main")?.getString("temp_min")
        minTemp = (((minTemp)?.toFloat()?.minus(300.15))?.toInt()).toString()
        binding.tvMinTemp.text = minTemp
        var maxTemp = response?.getJSONObject("main")?.getString("temp_max")
        maxTemp = (((maxTemp)?.toFloat()?.minus(273.15))?.toInt()).toString()
        binding.tvMaxTemp.text = maxTemp
        binding.tvPressure.text = "Pressure: " + response?.getJSONObject("main")?.getString("pressure")
        binding.tvHumidity.text = "Humidity: " + response?.getJSONObject("main")?.getString("humidity")
        binding.tvWindSpeed.text = "Wind Speed: " + response?.getJSONObject("wind")?.getString("speed")
        binding.tvWindDegree.text = "Wind Degree: " + response?.getJSONObject("wind")?.getString("deg")

    }
}