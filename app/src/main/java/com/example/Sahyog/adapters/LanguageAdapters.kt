package com.example.Sahyog.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.Sahyog.R
import com.example.Sahyog.model.LanguageItem

class LanguageAdapters(var context: Context, var arrayList: ArrayList<LanguageItem>) : BaseAdapter() {


    override fun getCount(): Int {
        return arrayList.size
    }

    override fun getItem(p0: Int): Any {
        return arrayList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view:View = View.inflate(context, R.layout.card_view_home_screen, null)
        var icons:ImageView = view.findViewById(R.id.weather)
        var names:TextView = view.findViewById(R.id.weathertext)
        var listItem:LanguageItem = arrayList.get(p0)
        icons.setImageResource(listItem.icons!!)
        names.text = listItem.name

        return view
    }
}