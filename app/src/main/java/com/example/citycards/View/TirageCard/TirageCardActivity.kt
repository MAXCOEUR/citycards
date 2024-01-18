package com.example.citycards.View.TirageCard

import android.R.bool
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.citycards.Model.City
import com.example.citycards.R
import com.example.citycards.Repository.UserRepository
import com.example.citycards.dataBase.CityListDataBase
import java.util.Date
import java.util.Timer
import java.util.TimerTask
import kotlin.random.Random

class TirageCardActivity : AppCompatActivity() {
    val tirageCardActivityViewModel by viewModels<TirageCardActivityViewModel>()
    var nbrTirage:Int = 0;
    public var compteurTirage:Int = 0;
    var user = UserRepository.getUserLogin();
    val fragmentManager = supportFragmentManager
    lateinit var transitionFragment:TransitionTirage

    var city:City?=null
    val timer = object : CountDownTimer(3750, 3750) {
        override fun onTick(millisUntilFinished: Long) {
            // La méthode onTick est appelée à chaque tick du timer
            // Cependant, dans votre cas, vous semblez vouloir exécuter une action seulement après le délai complet, pas à chaque tick.
        }

        override fun onFinish() {
            // La méthode onFinish est appelée lorsque le timer atteint 0
            transitionFragment.stopTimer()
            city?.let {
                updateCity(it)
            }
        }
    }

    public var cityWin:City?=null
    public fun resetCompteur(){
        compteurTirage=nbrTirage
    }
    public

    companion object {
        const val CLE_NBR_TIRAGE = "CLE_TIRAGE_1"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tirage_card)

        nbrTirage = intent.getIntExtra(CLE_NBR_TIRAGE, 0)
        compteurTirage=nbrTirage
        transitionFragment = TransitionTirage.newInstance()

        getOneCity()
    }

    private fun verifRight(): Boolean {
        if (nbrTirage==10){
            if(user.token<100){
                Toast.makeText(baseContext,"Il faut au minimum 100 jetons", Toast.LENGTH_LONG).show()
                return false;
            }
            else{
                return true;
            }
        }
        else if (nbrTirage==1){
            if(user.token<10){
                Toast.makeText(baseContext,"Il faut au minimum 10 jetons", Toast.LENGTH_LONG).show()
                return false;
            }
            else {
               return true;
            }
        }
        return false
    }

    public fun getOneCity(){

        if(!verifRight()){
            return
        }

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_tirage, transitionFragment)
        transaction.addToBackStack(null)
        transaction.commit()

        transitionFragment.startTimer()


        var rang= Random.nextInt(1,101)

        if(nbrTirage==1){
            user.token=user.token-10
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
        }
        else if (nbrTirage==10){
            if(compteurTirage==10){
                user.token=user.token-100
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
            }
            else if(compteurTirage==1){
                when {
                    rang <= 80 -> rang=3
                    rang <= 95 -> rang=2
                    rang <= 100 -> rang=1
                    else -> {
                        rang=6
                    }
                }
            }
            else{
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
            }
        }
        compteurTirage--
        tirageCardActivityViewModel.updateUser()


        var plage= City.getPlagePop(rang)
        var offset = City.getOffset(rang)
        Log.e("API", "demande api")
        val cityResponseCreate = tirageCardActivityViewModel.getCitysRandom(1,offset,plage.first,plage.second)
        cityResponseCreate.observe(this) { cityListe ->
            cityListe.body()?.let {
                Log.e("API", "recois api "+it.toString())
                timer.start()
                city=it.data[0]
                if(user.id!=null){
                    it.data[0].owner=user.id!!
                    it.data[0].dateObtention= Date().time
                    it.data[0].favori=false
                }
                tirageCardActivityViewModel.addCity(it.data[0])

            }
        }
    }
    fun updateCity(city:City){
        cityWin=city

        val succesFragment = SuccesTirage.newInstance()
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_tirage, succesFragment)
        transaction.addToBackStack(null) // Ajouter la transaction à la pile
        transaction.commit()
    }

    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)

        if(nbrTirage==compteurTirage){
            finish()
            return
        }
        // Définissez le titre et le message de la boîte de dialogue
        builder.setTitle("Confirmation")
        builder.setMessage("Êtes-vous sûr de vouloir quitter ? Vous allez perdre $compteurTirage cartes.")

        // Ajoutez les boutons "Oui" et "Non" à la boîte de dialogue
        builder.setPositiveButton("Oui") { dialogInterface: DialogInterface, i: Int ->
            // Si l'utilisateur clique sur "Oui", appelez la méthode onBackPressed pour quitter
            finish()
        }

        builder.setNegativeButton("Non") { dialogInterface: DialogInterface, i: Int ->
            // Si l'utilisateur clique sur "Non", ne faites rien (la boîte de dialogue se fermera)
        }

        // Affichez la boîte de dialogue
        builder.show()
    }
}