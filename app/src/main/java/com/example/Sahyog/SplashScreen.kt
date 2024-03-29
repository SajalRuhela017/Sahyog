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
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class SplashScreen : AppCompatActivity() {
    lateinit var mfusedlocation: FusedLocationProviderClient
    private var myRequestCode = 1111
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        mfusedlocation = LocationServices.getFusedLocationProviderClient(this)
        getLastLocation()
    }
    @SuppressLint("MissingPermission")
    private fun getLastLocation() {
        if(checkPermission()) {
            if(locationEnable()) {
                mfusedlocation.lastLocation.addOnCompleteListener{
                        task ->
                    var location: Location?=task.result
                    if(location == null) {
                        newLocation()
                    }else{
                        Handler(Looper.getMainLooper()).postDelayed({
                            val intent =  Intent(this, WeatherScreen::class.java)
                            intent.putExtra("Lat", location.latitude.toString())
                            intent.putExtra("Long", location.longitude.toString())
                            finish()
                        }, 500)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }else{
                Toast.makeText(this, "Please enable location services", Toast.LENGTH_SHORT).show()
            }
        }else{
            requestPermission()
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

    private val locationCallback=object: LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            var lastLocation: Location? =p0.lastLocation
        }
    }

    private fun locationEnable(): Boolean {
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION),myRequestCode)
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == myRequestCode) {
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation()
            }
        }
    }
}