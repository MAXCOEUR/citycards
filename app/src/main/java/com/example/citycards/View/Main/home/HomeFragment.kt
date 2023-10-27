package com.example.citycards.View.Main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        val TitreActivity = activity?.findViewById<TextView>(R.id.tv_titre);
        if (TitreActivity != null) {
            TitreActivity.text="Ouverture"
        }
        val switch = view.findViewById<Switch>(R.id.switch1)
        val image_rond = view.findViewById<ImageButton>(R.id.image_rond);
        val image_carte = view.findViewById<ImageView>(R.id.image_carte_1);
        val texte_carte = view.findViewById<TextView>(R.id.tirer_carte);
        switch.setOnClickListener {
            if(switch.isChecked) {
                image_rond.setImageResource(R.drawable.rond_ouverture_x10);
                texte_carte.text="Tirer 10 cartes";
                image_carte.setImageResource(R.drawable.carte_multiple);
            }
            else{
                image_rond.setImageResource(R.drawable.rond_ouverture_x1);
                texte_carte.text="Tirer 1 carte";
                image_carte.setImageResource(R.drawable.logo);
            }

        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String?=null, param2: String?=null) =
            HomeFragment().apply {
            }
    }
}