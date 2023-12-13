package com.example.citycards.View.TirageCard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.example.citycards.Model.City
import com.example.citycards.R
import com.example.citycards.Repository.UserRepository
import kotlin.random.Random

class TirageCardActivity : AppCompatActivity() {
    val tirageCardActivityViewModel by viewModels<TirageCardActivityViewModel>()
    var nbrTirage:Int = 0;
    var user = UserRepository.getUserLogin();

    lateinit var succesFragment : Fragment
    lateinit var transitionFragment : Fragment

    public var cityWin:City?=null

    companion object {
        const val CLE_NBR_TIRAGE = "CLE_TIRAGE_1"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tirage_card)

        transitionFragment = TransitionTirage.newInstance()


        nbrTirage = intent.getIntExtra(CLE_NBR_TIRAGE, 0)


        getOneCity()
    }

    public fun getOneCity(){

        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_tirage, transitionFragment)

        user.token=user.token-10
        var rang= Random.nextInt(1,100)
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
        val cityResponseCreate = tirageCardActivityViewModel.getCitysRandom(1,offset,plage.first,plage.second)
        cityResponseCreate.observe(this) { cityListe ->
            cityListe.body()?.let {
                Log.d("city", it.toString() )
                setCity(it.data[0]);
            }
        }
    }
    fun setCity(city:City){
        cityWin=city

        succesFragment = SuccesTirage.newInstance()
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_tirage, succesFragment)

        // Validez la transaction
        transaction.commit()
    }
}