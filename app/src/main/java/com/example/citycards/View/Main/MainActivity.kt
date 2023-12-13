package com.example.citycards.View.Main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Switch
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.Repository.UserRepository
import com.example.citycards.View.CityDetail.CityDetail
import com.example.citycards.databinding.ActivityMainBinding
import com.example.citycards.View.Main.collection.CollectionFragment
import com.example.citycards.View.Main.home.HomeFragment
import com.example.citycards.View.Main.search.SearchFragment
import com.example.citycards.View.Profile.ProfileActivity
import com.example.citycards.dataBase.CityListDataBase
import com.example.citycards.dataBase.DBDataSource
import com.example.citycards.dataSource.CacheDataSource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val mainViewModel by viewModels<MainViewModel>()
    companion object {
        //const val CLE_USER = "CLE_USER1"
        const val CLE_USER_RETURN = 1
    }

    private lateinit var binding: ActivityMainBinding
    lateinit var homeFragment :Fragment
    lateinit var searchFragment :Fragment
    lateinit var collectionFragment :Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        UserRepository.getUserLogin().token=100000000;

        val btProfile = findViewById<ImageButton>(R.id.bt_profil)
        val btnTirage = findViewById<ImageButton>(R.id.image_rond)
        val switchTirage = findViewById<Switch>(R.id.switch1)
        val jetons = findViewById<TextView>(R.id.tv_nbrJeton)
        jetons.text = UserRepository.getUserLogin().token.toString()
        val btToken = findViewById<ConstraintLayout>(R.id.bt_token)

        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher
        homeFragment = HomeFragment.newInstance()
        searchFragment = SearchFragment.newInstance()
        collectionFragment = CollectionFragment.newInstance()

        // Remplacez le contenu du FragmentContainerView par votre fragment
        transaction.replace(R.id.nav_host_fragment_activity_main, homeFragment)

        // Validez la transaction
        transaction.commit()

        val navView: BottomNavigationView = binding.navView
        navView.setOnNavigationItemSelectedListener {
                menuItem ->
            when (menuItem.itemId) {

                R.id.navigation_home -> {
                    Log.e("TAG", "navigation_home" )
                    // Remplacez le contenu du FragmentContainerView par votre fragment
                    val transaction_activity_main = fragmentManager.beginTransaction()
                    transaction_activity_main.replace(R.id.nav_host_fragment_activity_main, homeFragment)

                    // Validez la transaction
                    transaction_activity_main.commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_collection -> {
                    Log.e("TAG", "navigation_collection" )
                    val transaction_activity_main = fragmentManager.beginTransaction()
                    /// Remplacez le contenu du FragmentContainerView par votre fragment
                    transaction_activity_main.replace(R.id.nav_host_fragment_activity_main, collectionFragment)

                    // Validez la transaction
                    transaction_activity_main.commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.navigation_search -> {
                    Log.e("TAG", "navigation_notifications" )
                    val transaction_activity_main = fragmentManager.beginTransaction()
                    /// Remplacez le contenu du FragmentContainerView par votre fragment
                    transaction_activity_main.replace(R.id.nav_host_fragment_activity_main, searchFragment)

                    // Validez la transaction
                    transaction_activity_main.commit()
                    return@setOnNavigationItemSelectedListener true
                }
                // Ajoutez d'autres cas pour chaque élément de votre BottomNavigationView
                else -> return@setOnNavigationItemSelectedListener false
            }
        }

        btProfile.setOnClickListener {
            val changePage = Intent(this, ProfileActivity::class.java)
            startActivityForResult(changePage,CLE_USER_RETURN)
        }

        btToken.setOnClickListener{
            val user = UserRepository.getUserLogin()
            val lastClaim = user.lastClaimToken
            val cooldownClaim = TimeUnit.DAYS.toMillis(1) // 1 jour de délai entre la récupération des jetons
            if (Date().time - lastClaim  > cooldownClaim){
                user.token += 30
                Toast.makeText(this,"+ 30 Jetons !",Toast.LENGTH_SHORT).show()
                user.lastClaimToken = Date().time
                jetons.text = user.token.toString()
                (homeFragment as HomeFragment).updateToken()
                mainViewModel.updateUser(user)
            }
            else{
                var prochainClaim = cooldownClaim - ((Date().time - lastClaim) % cooldownClaim)
                val sdf = SimpleDateFormat("HH:mm:ss")
                val date =sdf.format(prochainClaim)
                Toast.makeText(this, "Prochain tirage possible dans $date", Toast.LENGTH_SHORT).show()
            }

        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==CLE_USER_RETURN){
            if(resultCode==Activity.RESULT_OK){
            }
        }
        else if(requestCode== CityDetail.CLE_CITY_RETURN){
            collectionFragment.onActivityResult(requestCode,resultCode,data)
        }
    }
}


