package com.example.citycards.View.Main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.citycards.Model.City
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.Repository.CityRepository
import com.example.citycards.Repository.UserRepository
import com.example.citycards.View.Main.MainViewModel
import java.util.Date
import kotlin.random.Random
import kotlin.random.nextInt

class HomeFragment : Fragment() {
    var user = UserRepository.getUserLogin();
    val mainViewModel by activityViewModels<MainViewModel>()
    val query = QueryDataCity()
    lateinit var nb_jeton:TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }
    fun updateToken(){
        nb_jeton.text = UserRepository.getUserLogin().token.toString()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState);

        val TitreActivity = activity?.findViewById<TextView>(R.id.tv_titre);
        if (TitreActivity != null) {
            TitreActivity.text="Ouverture"
        }
        val switch = view.findViewById<Switch>(R.id.switch1)
        nb_jeton = view.findViewById(R.id.nb_jeton)
        updateToken()
        val image_rond = view.findViewById<ImageButton>(R.id.image_rond);
        val image_carte = view.findViewById<ImageView>(R.id.image_carte_1);
        val texte_carte = view.findViewById<TextView>(R.id.tirer_carte);
        switch.setOnClickListener {
            if(switch.isChecked) {
                image_rond.setImageResource(R.drawable.back_rond_ouverture_x10);
                texte_carte.text="Tirer 10 cartes";
                image_carte.setImageResource(R.drawable.img_multiple_card);
            }
            else{
                image_rond.setImageResource(R.drawable.back_rond_ouverture_x1);
                texte_carte.text="Tirer 1 carte";
                image_carte.setImageResource(R.drawable.img_logo);
            }
        }
        image_rond.setOnClickListener{
            if (switch.isChecked){
                if(user.token<100){
                    Toast.makeText(this.context,"Il faut au minimum 100 jetons", Toast.LENGTH_LONG).show()
                }
                else{
                    for (i in 1..10){
                        GetOneCity()
                        Thread.sleep(5_000)  // wait for 1 second
                    }
                }
            }
            else{
                if(user.token<10){
                    Toast.makeText(this.context,"Il faut au minimum 10 jetons", Toast.LENGTH_LONG).show()
                }
                else {
                    GetOneCity()
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


    fun GetOneCity(){
            user.token=user.token-10
            var rang= Random.nextInt(1,101)
            when {
                rang <= 80 -> rang= 5
                rang <= 90 -> rang=4
                rang <= 96 -> rang=3
                rang <= 99 -> rang=2
                rang <= 100 -> rang=1
                else -> {
                    rang=6
                }
            }

            var plage= City.getPlagePop(rang)
            var offset = City.getOffset(rang)
            val cityResponseCreate = mainViewModel.getCitysRandom(1,offset,plage.first,plage.second)
            cityResponseCreate.observe(viewLifecycleOwner) { cityListe ->
                cityListe.body()?.let {
                    it.data.first().owner= user.id!!
                    it.data.first().dateObtention= Date().time
                    mainViewModel.addCity(it.data.first())
                }
            }
    }
}
