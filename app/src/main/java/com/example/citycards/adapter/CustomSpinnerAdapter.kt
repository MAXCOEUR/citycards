package com.example.citycards.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.citycards.R

class CustomSpinnerAdapter(private val context: Context, private val data: List<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false)

        // Personnalisez la vue ici en fonction des données
        val textView = view.findViewById<TextView>(R.id.tv_text)
        textView.text = data[position]

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.spinner_drop_down_item, parent, false)

        // Personnalisez la vue déroulée ici en fonction des données
        val textView = view.findViewById<TextView>(R.id.tv_text)
        textView.text = data[position]

        return view
    }
}
