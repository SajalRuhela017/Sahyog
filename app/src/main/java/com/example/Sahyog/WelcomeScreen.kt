package com.example.Sahyog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import com.example.Sahyog.adapters.LanguageAdapters
import com.example.Sahyog.model.LanguageItem

class WelcomeScreen : AppCompatActivity() , AdapterView.OnItemClickListener{

    private lateinit var arrayList:ArrayList<LanguageItem>
    private lateinit var gridView: GridView
    private lateinit var languageAdapters:LanguageAdapters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        gridView = findViewById(R.id.essentials)
        arrayList = ArrayList()
        arrayList = setDataList()
        languageAdapters = LanguageAdapters(applicationContext , arrayList!!)
        gridView?.adapter = languageAdapters
        gridView?.onItemClickListener = this

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
}