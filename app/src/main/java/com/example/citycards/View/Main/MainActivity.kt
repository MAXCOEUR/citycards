package com.example.citycards.View.Main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageButton
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.citycards.Model.City
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.View.CityDetail.CityDetail
import com.example.citycards.databinding.ActivityMainBinding
import com.example.citycards.View.Main.collection.CollectionFragment
import com.example.citycards.View.Main.home.HomeFragment
import com.example.citycards.View.Main.notifications.NotificationsFragment
import com.example.citycards.View.Profile.ProfileActivity
import com.example.citycards.dataBase.CityListDataBase
import com.example.citycards.dataBase.DBDataSource

class MainActivity : AppCompatActivity() {
    val mainViewModel by viewModels<MainViewModel>()
    companion object {
        const val CLE_USER = "CLE_USER1"
        const val CLE_USER_RETURN = 1
    }

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User
    lateinit var homeFragment :Fragment
    lateinit var notificationFragment :Fragment
    lateinit var collectionFragment :Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val btProfile = findViewById<ImageButton>(R.id.bt_profil)
        val intent = intent
        user = intent.getSerializableExtra(MainActivity.CLE_USER) as User


        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher
        homeFragment = HomeFragment.newInstance()
        notificationFragment = NotificationsFragment.newInstance()
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
                R.id.navigation_notifications -> {
                    Log.e("TAG", "navigation_notifications" )
                    val transaction_activity_main = fragmentManager.beginTransaction()
                    /// Remplacez le contenu du FragmentContainerView par votre fragment
                    transaction_activity_main.replace(R.id.nav_host_fragment_activity_main, notificationFragment)

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
            changePage.putExtra(ProfileActivity.CLE_USER, user)
            startActivityForResult(changePage,CLE_USER_RETURN)
        }



        val cityResponseCreate=mainViewModel.getCitysSearch(QueryDataCity())

        cityResponseCreate.observe(this) { cityListe->
            Log.i("citys",cityListe.body().toString())
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==CLE_USER_RETURN){
            if(resultCode==Activity.RESULT_OK){
                user= data?.getSerializableExtra(CLE_USER) as User
            }
        }
        else if(requestCode== CityDetail.CLE_CITY_RETURN){
            collectionFragment.onActivityResult(requestCode,resultCode,data)
        }
    }
}


