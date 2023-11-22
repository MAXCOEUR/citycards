package com.example.citycards.View.Main.search

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citycards.Model.City
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.R
import com.example.citycards.View.Main.MainViewModel
import com.example.citycards.adapter.CustomSpinnerAdapter
import com.example.citycards.adapter.ItemAdapter

class SearchFragment : Fragment() {
    private lateinit var searchTimer: CountDownTimer
    val mainViewModel by activityViewModels<MainViewModel>()
    lateinit var dropdown_rang: Spinner
    lateinit var recherche_field: EditText
    val tabcity = mutableListOf<City>()
    val query = QueryDataCity()
    lateinit var adapter: ItemAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        dropdown_rang = view.findViewById(R.id.rankDropdown)
        recherche_field = view.findViewById(R.id.searchEditText)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        askBD_rang()
        adapter = ItemAdapter(requireContext(), tabcity)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
        if (tabcity.size == 0) {
            askAPI()
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    query.offset += 10
                    askAPI()
                }
            }
        })
        val TitreActivity = activity?.findViewById<TextView>(R.id.tv_titre);
        if (TitreActivity != null) {
            TitreActivity.text = "Recherche"
        }
        dropdown_rang.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedRang = dropdown_rang.selectedItem.toString()
                applyFilter(selectedRang)
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Faites quelque chose si rien n'est sélectionné
            }
        }
        recherche_field.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searchTimer.cancel()
                searchTimer.start()
                val searchtext = s.toString().lowercase()
                query.namePrefix=searchtext
                tabcity.clear()
                adapter.notifyDataSetChanged()
            }
            override fun afterTextChanged(s: Editable?) {
            }
        })

        searchTimer = object : CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) {
                // Ne rien faire ici, car nous n'avons pas besoin d'actions intermédiaires
            }

            override fun onFinish() {
                searchTimerTick()
            }
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String?=null, param2: String?=null) =
            SearchFragment().apply {

            }
    }
    private fun searchTimerTick() {
        // Faire la recherche ici
        askAPI()
    }
    fun askAPI(){


        val cityResponseCreate = mainViewModel.getCitysSearch(query)
        cityResponseCreate.observe(viewLifecycleOwner) { cityListe ->
            cityListe.body()?.let {
                tabcity.addAll(it.data)
                adapter.notifyDataSetChanged()
            }
        }
    }
    fun askBD_rang(){
        var rangs = mainViewModel.getRang()
        rangs.observe(viewLifecycleOwner){ rangs ->
            rangs.body()?.let{
                //val rang_adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, it)
                //rang_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                dropdown_rang.adapter =  CustomSpinnerAdapter(requireContext(), it)
            }
        }
    }

    fun applyFilter(selectedRang: String) {
        var nbrBornes : Pair<Int,Int>? =
            when {
            selectedRang == "Petite ville" -> City.getPlagePop(5)
            selectedRang == "Moyenne ville" -> City.getPlagePop(4)
            selectedRang == "Grande ville" -> City.getPlagePop(3)
            selectedRang == "Métropole" -> City.getPlagePop(2)
            selectedRang == "Mégapole" -> City.getPlagePop(1)
            else -> {Pair(0,Int.MAX_VALUE)}
        }
        if (nbrBornes != null) {
            query.minPop = nbrBornes.first
        }
        if (nbrBornes != null) {
            query.maxPop = nbrBornes.second
        }
        tabcity.clear()
        query.offset = 0
        askAPI()
    }
}