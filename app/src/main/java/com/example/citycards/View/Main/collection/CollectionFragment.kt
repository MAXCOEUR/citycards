package com.example.citycards.View.Main.collection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citycards.Model.City
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.View.CityDetail.CityDetail
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.View.Main.MainViewModel
import com.example.citycards.adapter.ItemAdapter
import com.example.citycards.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment() {
    val mainViewModel by activityViewModels<MainViewModel>()
    val tabcity = mutableListOf<City>()
    lateinit var dropdown_region: Spinner
    lateinit var dropdown_rang: Spinner
    lateinit var recherche_field: EditText
    lateinit var adapter: ItemAdapter
    // This property is only valid between onCreateView and
    // onDestroyView.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_collection, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val query = QueryDataCity()
        var favori_checked = false
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val btn_favori = view.findViewById<Button>(R.id.favoritesButton)
        dropdown_region = view.findViewById(R.id.regionDropdown)
        dropdown_rang = view.findViewById(R.id.rankDropdown)
        recherche_field = view.findViewById(R.id.searchEditText)
        askBD_region()
        askBD_rang()
        adapter = ItemAdapter(requireContext(), tabcity)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
        if (tabcity.size == 0) {
            askAPI(query)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    query.offset += 10
                    if (favori_checked){
                        askAPI_favori(query)
                    }
                    else {
                        askAPI(query)
                    }

                }
            }
        })

        dropdown_region.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRang = dropdown_rang.selectedItem.toString()
                val selectedRegion = dropdown_region.selectedItem.toString()
                applyFilter(selectedRang, selectedRegion)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Faites quelque chose si rien n'est sélectionné
            }
        }
        btn_favori.setOnClickListener {
            tabcity.clear()
            favori_checked = !favori_checked
            if (favori_checked) {
                askAPI_favori(query)
                btn_favori.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                btn_favori.setTextColor(ContextCompat.getColor(requireContext(), R.color.onPrimary))
            }
            else {
                askAPI(query)
                btn_favori.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.background))
                btn_favori.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
            }
        }






    }

    fun askAPI(query : QueryDataCity ){
        val cityResponseCreate = mainViewModel.getCitysCollection(query)
        cityResponseCreate.observe(viewLifecycleOwner) { cityListe ->
            cityListe.body()?.let {
                tabcity.addAll(it.data)
                adapter.notifyDataSetChanged()
            }
        }
    }


    fun askAPI_favori(query : QueryDataCity){
        val cityResponseCreate = mainViewModel.getCitysCollection_favori(query)
        cityResponseCreate.observe(viewLifecycleOwner) { cityListe ->
            cityListe.body()?.let {
                tabcity.addAll(it.data)
                adapter.notifyDataSetChanged()
            }
        }
    }

    fun askBD_region(){
        var regions = mainViewModel.getRegion()
        regions.observe(viewLifecycleOwner){ regions ->
            regions.body()?.let{
                val region_adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                region_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dropdown_region.adapter = region_adapter
            }
        }
    }

    fun askBD_rang(){
        var rangs = mainViewModel.getRang()
        rangs.observe(viewLifecycleOwner){ rangs ->
            rangs.body()?.let{
                val rang_adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                rang_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dropdown_rang.adapter = rang_adapter
            }

        }
    }

    fun applyFilter(selectedRang: String, selectedRegion: String) {
        val tab = mutableListOf<City>()
        for (city in tabcity){
            tab.add(city)
        }
        tabcity.clear()
        // Filtrer les villes en fonction des sélections
        for (city in tab) {
            if (selectedRegion == "Toutes les régions") {

                tabcity.add(city)
            }
            else {
                if (city.region == selectedRegion){
                    tabcity.add(city)
                }
            }
        }
        adapter.notifyDataSetChanged() // Mettez à jour le RecyclerView
    }

    fun updateCityListBasedOnSearch(searchText: String) {
        for (city in tabcity){
            if(city.name?.contains(searchText,true) == false){
                tabcity.remove(city)
            }
        }
        adapter.notifyDataSetChanged()
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String?=null, param2: String?=null) =
            CollectionFragment().apply {

            }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            val citytmp= data?.getSerializableExtra(CityDetail.CLE_CITY) as City
            val city = tabcity.find { it.idUnique==citytmp.idUnique }
            city?.let {
                it.favori=citytmp.favori
                adapter.notifyDataSetChanged()
            }
        }
        else if (resultCode == CityDetail.DELETE_CITY) {
            val citytmp= data?.getSerializableExtra(CityDetail.CLE_CITY) as City
            val city = tabcity.find { it.idUnique==citytmp.idUnique }
            city?.let {
                tabcity.remove(it)
                adapter.notifyDataSetChanged()
            }
        }
    }

}