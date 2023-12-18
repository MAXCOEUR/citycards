package com.example.citycards.View.Main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.R
import com.example.citycards.Repository.UserRepository
import com.example.citycards.View.Main.MainViewModel
import com.example.citycards.View.TirageCard.TirageCardActivity


class HomeFragment : Fragment() {
    var user = UserRepository.getUserLogin()
    val mainViewModel by activityViewModels<MainViewModel>()
    val query = QueryDataCity()
    lateinit var nb_jeton:TextView
    lateinit var switch:Switch
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }
    fun updateToken(){
        nb_jeton.text = user.token.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        val TitreActivity = activity?.findViewById<TextView>(R.id.tv_titre);
        if (TitreActivity != null) {
            TitreActivity.text="Ouverture"
        }
        switch = view.findViewById(R.id.switch1)

        nb_jeton = view.findViewById(R.id.nb_jeton)
        updateToken()

        val parentLayout = view.findViewById<ConstraintLayout>(R.id.parent_bt)
        val btOpenLayout = view.findViewById<ConstraintLayout>(R.id.bt_open)
        val bouton = btOpenLayout.findViewById<ImageButton>(R.id.image_rond)
        initClick(bouton);


        switch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            parentLayout.removeView(btOpenLayout)
            if(isChecked) {
                val btOpenX10 = LayoutInflater.from(requireContext()).inflate(R.layout.bouton_opening_x10, null)
                parentLayout.addView(btOpenX10, btOpenLayout.layoutParams)
                val bouton = btOpenX10.findViewById<ImageButton>(R.id.image_rond)
                initClick(bouton);
            }
            else{
                val btOpenX1 = LayoutInflater.from(requireContext()).inflate(R.layout.bouton_opening_x1, null)
                parentLayout.addView(btOpenX1, btOpenLayout.layoutParams)
                val bouton = btOpenX1.findViewById<ImageButton>(R.id.image_rond)
                initClick(bouton);
            }
        })
    }

    private fun initClick(view: View) {
        view.setOnClickListener{
            if (switch.isChecked){
                if(user.token<100){
                    Toast.makeText(this.context,"Il faut au minimum 100 jetons", Toast.LENGTH_LONG).show()
                }
                else{
                    val intent = Intent(requireContext(), TirageCardActivity::class.java)
                    intent.putExtra(TirageCardActivity.CLE_NBR_TIRAGE, 10)
                    startActivity(intent)
                }
            }
            else{
                if(user.token<10){
                    Toast.makeText(this.context,"Il faut au minimum 10 jetons", Toast.LENGTH_LONG).show()
                }
                else {
                    val intent = Intent(requireContext(), TirageCardActivity::class.java)
                    intent.putExtra(TirageCardActivity.CLE_NBR_TIRAGE, 1)
                    startActivity(intent)
                    //Afficher page
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String?=null, param2: String?=null) =
            HomeFragment().apply {
            }
    }

    override fun onResume() {
        super.onResume()
        user = UserRepository.getUserLogin()
        updateToken()
    }
}
