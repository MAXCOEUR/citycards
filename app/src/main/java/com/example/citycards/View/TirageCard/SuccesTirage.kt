package com.example.citycards.View.TirageCard

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.citycards.Model.City
import com.example.citycards.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class SuccesTirage : Fragment() {

    lateinit var cardCity:ConstraintLayout
    lateinit var tvNameCity:TextView
    lateinit var tvRegionCity:TextView
    lateinit var imageButtonStar:ImageButton
    lateinit var ligneView: View

    var city:City?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_succes_tirage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.e("TAG", "test" )
        cardCity = view.findViewById(R.id.card_city)
        tvNameCity = cardCity.findViewById(R.id.item_title)
        tvRegionCity = cardCity.findViewById(R.id.item_region)
        imageButtonStar = cardCity.findViewById(R.id.star)
        ligneView = cardCity.findViewById(R.id.separation_line)
        imageButtonStar.visibility = View.GONE

        val bt_replay = view.findViewById<Button>(R.id.bt_replay)
        bt_replay.setOnClickListener {
            (requireActivity() as TirageCardActivity).getOneCity()
        }

        val bt_return = view.findViewById<ImageButton>(R.id.bt_back)
        bt_return.setOnClickListener {
            requireActivity().onBackPressed()
        }

        updateCity()
    }

    private fun updateCity(){
        city = (requireActivity() as TirageCardActivity).cityWin

        if(city!=null){

            tvNameCity.text=city?.name
            tvRegionCity.text=city?.region

            val context = requireContext()

            when (city?.getRang()){
                0 -> Log.e("erreur", "le rang est 0")
                5 -> {
                    cardCity.backgroundTintList = ContextCompat.getColorStateList(context,R.color.primaryContainer)
                    tvRegionCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onPrimaryContainer))
                    tvNameCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onPrimaryContainer))
                    ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onPrimaryContainer))
                }
                4 -> {
                    cardCity.backgroundTintList = ContextCompat.getColorStateList(context,R.color.secondaryContainer)
                    tvRegionCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onSecondaryContainer))
                    tvNameCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onSecondaryContainer))
                    ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onSecondaryContainer))
                }
                3 -> {
                    cardCity.backgroundTintList = ContextCompat.getColorStateList(context,R.color.primary)
                    tvRegionCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onPrimary))
                    tvNameCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onPrimary))
                    ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onPrimary))
                }
                2 -> {
                    cardCity.backgroundTintList = ContextCompat.getColorStateList(context,R.color.secondary)
                    tvRegionCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onSecondary))
                    tvNameCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onSecondary))
                    ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onSecondary))
                }
                1 -> {
                    cardCity.backgroundTintList = ContextCompat.getColorStateList(context,R.color.tertiary)
                    tvRegionCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onTertiary))
                    tvNameCity.setTextColor(ContextCompat.getColorStateList(context,R.color.onTertiary))
                    ligneView.setBackgroundColor(ContextCompat.getColor(context,R.color.onTertiary))
                }
            }

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync { googleMap ->
                // Latitude et longitude de votre marqueur
                val latitude = city?.latitude
                val longitude = city?.longitude
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
    }

    companion object {
        private const val ARG_CITY = "city"
        fun newInstance() =
            SuccesTirage().apply {
            }
    }
}