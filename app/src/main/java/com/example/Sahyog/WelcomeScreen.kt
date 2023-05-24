package com.example.Sahyog

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.GridView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.Sahyog.adapters.LanguageAdapters
import com.example.Sahyog.model.LanguageItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth

class WelcomeScreen : AppCompatActivity() , AdapterView.OnItemClickListener{
    lateinit var mfusedlocation: FusedLocationProviderClient
    private var myRequestCode = 1111
    private lateinit var arrayList:ArrayList<LanguageItem>
    private lateinit var gridView: GridView
    private lateinit var languageAdapters:LanguageAdapters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        gridView = findViewById(R.id.essentials)
        arrayList = ArrayList()
        arrayList = setDataList()
        languageAdapters = LanguageAdapters(applicationContext , arrayList!!)
        gridView?.adapter = languageAdapters
        gridView?.onItemClickListener = this
        val btnSignOut = findViewById<Button>(R.id.btnSignOut)
        btnSignOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setDataList() : ArrayList<LanguageItem>{
        var arrayList:ArrayList<LanguageItem> = ArrayList()
        arrayList.add(LanguageItem(R.drawable.weather, "Weather"))
        arrayList.add(LanguageItem(R.drawable.moisture, "Moisture"))
        arrayList.add(LanguageItem(R.drawable.predict, "Crop Prediction"))
        arrayList.add(LanguageItem(R.drawable.current_crop, "Current Crop"))
        return arrayList
    }

    override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        var items:LanguageItem = arrayList!!.get(p2)
        val intent:Intent
        when(items.name){
            "Weather"->{
                mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
                getLastLocation()
                intent = Intent(this, WeatherScreen::class.java)
                startActivity(intent)
            }
            "Current Crop"->{
                intent = Intent(this, CurrentCrop::class.java)
                startActivity(intent)
            }
        }
        Toast.makeText(this, items.name, Toast.LENGTH_SHORT).show()
    }

    private fun getLastLocation() {
        if(checkPermission()) {
            if(locationEnable()) {
                mfusedlocation.lastLocation.addOnCompleteListener{
                        task ->
                    var location: Location?=task.result
                    if(location == null) {
                        newLocation()
                    }else{
                        val futureIntent =  Intent(this, WeatherScreen::class.java)
                        futureIntent.putExtra("Lat", location.latitude.toString())
                        futureIntent.putExtra("Long", location.longitude.toString())
                    }
                }
            }else{
                Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermission()
        }
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION),myRequestCode)
    }

    private val locationCallback=object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation: Location? =p0.lastLocation
        }
    }
    @SuppressLint("MissingPermission")
    private fun newLocation() {
        var locationRequest = com.google.android.gms.location.LocationRequest()
        locationRequest.priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        mfusedlocation.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())
    }

    private fun locationEnable(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }
}