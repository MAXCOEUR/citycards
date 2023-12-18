package com.example.citycards.View.TirageCard

import android.R.bool
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import kotlin.random.Random

class TirageCardActivity : AppCompatActivity() {
    val tirageCardActivityViewModel by viewModels<TirageCardActivityViewModel>()
    var nbrTirage:Int = 0;
    public var compteurTirage:Int = 0;
    var user = UserRepository.getUserLogin();

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

        val transitionFragment = TransitionTirage.newInstance()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_tirage, transitionFragment)

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



        var plage= City.getPlagePop(rang)
        var offset = City.getOffset(rang)
        val cityResponseCreate = tirageCardActivityViewModel.getCitysRandom(1,offset,plage.first,plage.second)
        cityResponseCreate.observe(this) { cityListe ->
            cityListe.body()?.let {
                Log.d("city", it.toString() )
                setCity(it.data[0]);
                if(user.id!=null){
                    it.data[0].owner=user.id!!
                    it.data[0].dateObtention= Date().time
                    it.data[0].favori=false
                }
                tirageCardActivityViewModel.addCity(it.data[0])
            }
        }
    }
    fun setCity(city:City){
        cityWin=city

        val succesFragment = SuccesTirage.newInstance()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_tirage, succesFragment)

        // Validez la transaction
        transaction.commit()
    }
}