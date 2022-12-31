package com.example.Sahyog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import kotlin.math.ceil

//https://api.openweathermap.org/data/2.5/weather?q=delhi&appid=d782c3641dcc9cbcb8528dfb0245b3e4

class WeatherScreen: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_screen)
        val lat = intent.getStringExtra("Lat")
        val long = intent.getStringExtra("Long")
//        Toast.makeText(this, "Lat: " + lat + " Long: " + long, Toast.LENGTH_SHORT).show()
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
//                setValues(response)
                Toast.makeText(this, response.toString(), Toast.LENGTH_SHORT).show()
            },
            Response.ErrorListener {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        )
        queue.add(jsonRequest)
    }
//    private fun setValues(response: JSONObject?) {
//
//    }
}