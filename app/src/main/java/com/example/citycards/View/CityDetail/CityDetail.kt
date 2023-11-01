package com.example.citycards.View.CityDetail

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.citycards.Model.City
import com.example.citycards.R
import com.example.citycards.View.Main.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.text.NumberFormat
import kotlin.concurrent.thread

class CityDetail : AppCompatActivity() {

    val mainViewModel by viewModels<MainViewModel>()
    lateinit var bt_favorie:ImageButton
    lateinit var bt_bin:ImageButton
    companion object {
        const val CLE_CITY = "CLE_CITY1"
        const val CLE_CITY_RETURN = 2
        const val DELETE_CITY = 101
    }

    lateinit var city: City
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_detail)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        val intent = intent
        city = intent.getSerializableExtra(CLE_CITY) as City

        val bt_back = findViewById<ImageButton>(R.id.bt_back)

        val tv_name = findViewById<TextView>(R.id.tv_name)
        val tv_region = findViewById<TextView>(R.id.tv_region)
        val tv_hab = findViewById<TextView>(R.id.tv_hab)
        val tv_rang = findViewById<TextView>(R.id.tv_rang)
        val tv_date = findViewById<TextView>(R.id.tv_date)
        val carteBack = findViewById<ConstraintLayout>(R.id.carte)
        val constraint_fav_del = findViewById<ConstraintLayout>(R.id.constraint_fav_del)

        bt_favorie = findViewById<ImageButton>(R.id.bt_favorie)
        bt_bin = findViewById<ImageButton>(R.id.bt_bin)

        tv_name.text=city.name

        tv_region.text=city.region

        val formattedPopulation = NumberFormat.getNumberInstance().format(city.population)
        tv_hab.text="$formattedPopulation habitants"

        val dateFormat = SimpleDateFormat("d MMM. yyyy HH:mm")
        tv_date.text = dateFormat.format(city.dateObtention)

        updateStar()

        if(city.favori==null){
            constraint_fav_del.visibility= View.GONE
        }else{
            constraint_fav_del.visibility= View.VISIBLE
        }

        val rang = city.getRang()
        when (rang){
            0 -> Log.e("erreur", "le rang est 0")
            5 -> {
                carteBack.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primaryContainer)
                tv_name.setTextColor(ContextCompat.getColorStateList(this,R.color.onPrimaryContainer))
                tv_region.setTextColor(ContextCompat.getColorStateList(this,R.color.onPrimaryContainer))
                tv_hab.setTextColor(ContextCompat.getColor(this,R.color.onPrimaryContainer))
                tv_rang.setTextColor(ContextCompat.getColor(this,R.color.onPrimaryContainer))
                tv_date.setTextColor(ContextCompat.getColor(this,R.color.onPrimaryContainer))

                tv_rang.text="Petite ville"

            }
            4 -> {
                carteBack.backgroundTintList = ContextCompat.getColorStateList(this,R.color.secondaryContainer)
                tv_name.setTextColor(ContextCompat.getColorStateList(this,R.color.onSecondaryContainer))
                tv_region.setTextColor(ContextCompat.getColorStateList(this,R.color.onSecondaryContainer))
                tv_hab.setTextColor(ContextCompat.getColor(this,R.color.onSecondaryContainer))
                tv_rang.setTextColor(ContextCompat.getColor(this,R.color.onSecondaryContainer))
                tv_date.setTextColor(ContextCompat.getColor(this,R.color.onSecondaryContainer))

                tv_rang.text="Moyenne ville"

            }
            3 -> {
                carteBack.backgroundTintList = ContextCompat.getColorStateList(this,R.color.primary)
                tv_name.setTextColor(ContextCompat.getColorStateList(this,R.color.onPrimary))
                tv_region.setTextColor(ContextCompat.getColorStateList(this,R.color.onPrimary))
                tv_hab.setTextColor(ContextCompat.getColor(this,R.color.onPrimary))
                tv_rang.setTextColor(ContextCompat.getColor(this,R.color.onPrimary))
                tv_date.setTextColor(ContextCompat.getColor(this,R.color.onPrimary))

                tv_rang.text="Grande ville"

            }
            2 -> {
                carteBack.backgroundTintList = ContextCompat.getColorStateList(this,R.color.secondary)
                tv_name.setTextColor(ContextCompat.getColorStateList(this,R.color.onSecondary))
                tv_region.setTextColor(ContextCompat.getColorStateList(this,R.color.onSecondary))
                tv_hab.setTextColor(ContextCompat.getColor(this,R.color.onSecondary))
                tv_rang.setTextColor(ContextCompat.getColor(this,R.color.onSecondary))
                tv_date.setTextColor(ContextCompat.getColor(this,R.color.onSecondary))

                tv_rang.text="Metropole"

            }
            1 -> {
                carteBack.backgroundTintList = ContextCompat.getColorStateList(this,R.color.tertiary)
                tv_name.setTextColor(ContextCompat.getColorStateList(this,R.color.onTertiary))
                tv_region.setTextColor(ContextCompat.getColorStateList(this,R.color.onTertiary))
                tv_hab.setTextColor(ContextCompat.getColor(this,R.color.onTertiary))
                tv_rang.setTextColor(ContextCompat.getColor(this,R.color.onTertiary))
                tv_date.setTextColor(ContextCompat.getColor(this,R.color.onTertiary))

                tv_rang.text="Megapole"
            }
        }

        bt_back.setOnClickListener(){
            onBackPressed()
        }

        bt_favorie.setOnClickListener(){
            Log.i("bt_favorie","bt_favorie")
            city.favori?.let {
                city.favori= !it
                updateStar()
            }
        }
        bt_bin.setOnClickListener(){
            Log.i("bt_bin","bt_bin")
            val resultIntent = Intent()
            resultIntent.putExtra(CLE_CITY, city)
            setResult(DELETE_CITY, resultIntent)
            finish()
        }


        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            // Latitude et longitude de votre marqueur
            val latitude = city.latitude
            val longitude = city.longitude
            if(latitude!=null && longitude!=null){

                // Créez un objet LatLng avec les coordonnées

                val latLng = LatLng(latitude.toDouble(), longitude.toDouble())

                // Ajoutez un marqueur à la carte
                googleMap.addMarker(MarkerOptions().position(latLng))

                // Déplacez la caméra vers la position du marqueur
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f),5000,null)
            }
        }
    }
    fun updateStar(){
        when (city.favori){
            null -> bt_favorie.visibility = View.GONE
            false -> bt_favorie.setImageResource(R.drawable.star_outline)
            true -> bt_favorie.setImageResource(R.drawable.star)
        }
    }
    override fun onBackPressed() {
        val resultIntent = Intent()
        resultIntent.putExtra(CLE_CITY, city)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}