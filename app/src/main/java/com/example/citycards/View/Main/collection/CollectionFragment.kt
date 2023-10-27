package com.example.citycards.View.Main.collection

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.citycards.Model.City
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.R
import com.example.citycards.View.Main.MainViewModel
import com.example.citycards.adapter.ItemAdapter
import com.example.citycards.databinding.FragmentCollectionBinding

class CollectionFragment : Fragment() {
    val mainViewModel by activityViewModels<MainViewModel>()
    val tabcity = mutableListOf<City>()

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
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        val adapter = ItemAdapter(requireContext(), tabcity)
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        recyclerView.adapter = adapter
        if (tabcity.size == 0) {
            askAPI(query, adapter)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    query.offset += 10
                    askAPI(query, adapter)
                }
            }
        })
    }

    fun askAPI(query : QueryDataCity , adapter: ItemAdapter){
        val cityResponseCreate = mainViewModel.getCitysCollection(query)
        cityResponseCreate.observe(viewLifecycleOwner) { cityListe ->
            cityListe.body()?.let {
                tabcity.addAll(it.data)
                adapter.notifyDataSetChanged()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String?=null, param2: String?=null) =
            CollectionFragment().apply {

            }
    }
}