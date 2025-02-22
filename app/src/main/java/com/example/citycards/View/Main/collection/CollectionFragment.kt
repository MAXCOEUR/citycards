package com.example.citycards.View.Main.collection

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.size
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citycards.Model.City
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.Repository.CityRepository
import com.example.citycards.View.CityDetail.CityDetail
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.View.Main.MainViewModel
import com.example.citycards.View.MapCollection.MapCollection
import com.example.citycards.adapter.CustomSpinnerAdapter
import com.example.citycards.adapter.ItemAdapter
import com.example.citycards.dataBase.DBDataSource
import com.google.android.material.floatingactionbutton.FloatingActionButton

class CollectionFragment : Fragment() {
    val mainViewModel by activityViewModels<MainViewModel>()
    val tabcity = mutableListOf<City>()
    lateinit var dropdown_region: Spinner
    lateinit var dropdown_rang: Spinner
    lateinit var recherche_field: EditText
    lateinit var adapter: ItemAdapter
    val query = QueryDataCity()
    var favori_checked = false
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

        val TitreActivity = activity?.findViewById<TextView>(R.id.tv_titre)
        if (TitreActivity != null) {
            TitreActivity.text = "Collection"
        }
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val btn_favori = view.findViewById<Button>(R.id.favoritesButton)
        val btn_map = view.findViewById<FloatingActionButton>(R.id.bt_map)
        dropdown_region = view.findViewById(R.id.regionDropdown)
        dropdown_rang = view.findViewById(R.id.rankDropdown)
        recherche_field = view.findViewById(R.id.searchEditText)
        askBD_region()
        askBD_rang()
        adapter = ItemAdapter(requireContext(), tabcity)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter

        dropdown_region.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                query.region=dropdown_region.selectedItem.toString();
                askAPI()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Faites quelque chose si rien n'est sélectionné
            }
        }
        dropdown_rang.onItemSelectedListener = object :AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                query.rang= Math.abs(dropdown_rang.selectedItemPosition-dropdown_rang.adapter.count)
                askAPI()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Faites quelque chose si rien n'est sélectionné
            }
        }
        btn_favori.setOnClickListener {
            favori_checked = !favori_checked
            if (favori_checked) {
                btn_favori.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primary))
                btn_favori.setTextColor(ContextCompat.getColor(requireContext(), R.color.onPrimary))

            }
            else {
                btn_favori.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.background))
                btn_favori.setTextColor(ContextCompat.getColor(requireContext(), R.color.primary))
            }
            askAPI()
        }


        recherche_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchtext = s.toString().lowercase()
                query.namePrefix=searchtext
                askAPI()
                adapter.notifyDataSetChanged()
                }
            override fun afterTextChanged(s: Editable?) {

            }
        })
        btn_map.setOnClickListener(){
            val changePage = Intent(context, MapCollection::class.java)
            (context as Activity).startActivity(changePage)
        }
    }

    fun askAPI(){
        if (favori_checked) {
            val cityResponseCreate = mainViewModel.getCitysCollection_favori(query)
            cityResponseCreate.observe(viewLifecycleOwner) { cityListe ->
                cityListe.body()?.let {
                    tabcity.clear()
                    tabcity.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        else {
            val cityResponseCreate = mainViewModel.getCitysCollection(query)
            cityResponseCreate.observe(viewLifecycleOwner) { cityListe ->
                cityListe.body()?.let {
                    tabcity.clear()
                    tabcity.addAll(it.data)
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }

    fun askBD_region(){
        var regions = mainViewModel.getRegion()
        regions.observe(viewLifecycleOwner){ regions ->
            regions.body()?.let{
                dropdown_region.adapter = CustomSpinnerAdapter(requireContext(), it)
            }
        }
    }

    fun askBD_rang(){
        var rangs = mainViewModel.getRang()
        rangs.observe(viewLifecycleOwner){ rangs ->
            rangs.body()?.let{
                dropdown_rang.adapter =  CustomSpinnerAdapter(requireContext(), it)
            }

        }
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
                Toast.makeText(context, "+ jetons", Toast.LENGTH_SHORT).show()
                adapter.notifyDataSetChanged()
                mainViewModel.deleteCity(it)
            }
        }
    }

}