package com.example.citycards.View.MapCollection

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.viewModels
import com.example.citycards.Model.City
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.View.CityDetail.CityDetail
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.View.Main.MainViewModel

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.citycards.databinding.ActivityMapCollectionBinding
import com.google.maps.android.clustering.ClusterItem
import com.google.maps.android.clustering.ClusterManager

class MapCollection : AppCompatActivity(), OnMapReadyCallback {
    val viewModel by viewModels<MapCollectionViewModel>()
    private lateinit var clusterManager: ClusterManager<CityItemCluster>
    val query = QueryDataCity()
    val tabcity = mutableListOf<City>()

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_collection)

        val bt_back = findViewById<ImageButton>(R.id.bt_back)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val cityResponseCreate = viewModel.getCitysCollection(query)
        cityResponseCreate.observe(this) { cityListe ->
            cityListe.body()?.let {
                tabcity.clear()
                tabcity.addAll(it.data)
                updateCluster()
            }
        }
        bt_back.setOnClickListener(){
            finish()
        }
    }

    fun updateCluster(){
        clusterManager.clearItems()
        for (city in tabcity) {
            clusterManager.addItem(CityItemCluster(city))
        }
        clusterManager.cluster()
    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        clusterManager = ClusterManager(this, mMap)

        mMap.setOnCameraIdleListener(clusterManager)

        // Déplacez la caméra vers un emplacement par défaut, par exemple :
        val france = LatLng(46.603354, 1.888334)

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(france, 5f))


        clusterManager.setOnClusterItemInfoWindowClickListener {cityItem ->
            val changePage = Intent(this, CityDetail::class.java)
            changePage.putExtra(CityDetail.CLE_CITY, cityItem.city)
            startActivityForResult(changePage, CityDetail.CLE_CITY_RETURN)
            true
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== CityDetail.CLE_CITY_RETURN){
            if (resultCode == Activity.RESULT_OK) {
                val citytmp= data?.getSerializableExtra(CityDetail.CLE_CITY) as City
                val city = tabcity.find { it.idUnique==citytmp.idUnique }
                city?.let {
                    it.favori=citytmp.favori
                    updateCluster()
                }
            }
            else if (resultCode == CityDetail.DELETE_CITY) {
                val citytmp= data?.getSerializableExtra(CityDetail.CLE_CITY) as City
                val city = tabcity.find { it.idUnique==citytmp.idUnique }
                city?.let {
                    tabcity.remove(it)
                    updateCluster()
                }
            }
        }
    }
}
class CityItemCluster(val city: City) : ClusterItem {
    override fun getPosition(): LatLng {
        if(city.latitude==null || city.longitude==null){
            return LatLng(0.0, 0.0)
        }
        return LatLng(city.latitude.toDouble(), city.longitude.toDouble())
    }

    override fun getTitle(): String {
        city.name?.let { return it }
        return ""
    }

    override fun getSnippet(): String {
        city.region?.let { return it }
        return ""
    }

    override fun getZIndex(): Float? {
        return null
    }

}