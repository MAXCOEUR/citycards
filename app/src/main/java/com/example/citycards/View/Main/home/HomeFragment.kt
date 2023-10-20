package com.example.citycards.View.Main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.citycards.R
import com.example.citycards.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String?=null, param2: String?=null) =
            HomeFragment().apply {

            }
    }
}