package com.example.citycards.View.Main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.View.Login.LoginViewModel
import com.example.citycards.databinding.ActivityMainBinding
import com.example.citycards.View.Main.collection.CollectionFragment
import com.example.citycards.View.Main.home.HomeFragment
import com.example.citycards.View.Main.notifications.NotificationsFragment

class MainActivity : AppCompatActivity() {
    val mainViewModel by viewModels<MainViewModel>()
    companion object {
        const val CLE_USER = "CLE_USER1"
    }

    private lateinit var binding: ActivityMainBinding
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        user = intent.getSerializableExtra(MainActivity.CLE_USER) as User


        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher
        val homeFragment = HomeFragment.newInstance()
        val notificationFragment = NotificationsFragment.newInstance()
        val dashFragment = CollectionFragment.newInstance()

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
                    transaction_activity_main.replace(R.id.nav_host_fragment_activity_main, dashFragment)

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


        val cityResponseCreate=mainViewModel.getCitysSearch(QueryDataCity())

        cityResponseCreate.observe(this) { cityListe->
            Log.i("citys",cityListe.body().toString())
        }






    }
}


