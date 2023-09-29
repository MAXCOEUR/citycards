package com.example.citycards

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.citycards.RetrofitAPi.ApiClient
import com.example.citycards.RetrofitAPi.ApiService
import com.example.citycards.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        val Api = ApiClient.ApiService
        CoroutineScope(Dispatchers.IO).launch {
            val response = Api.getCities()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        Log.i("Test", "Succes")
                    } else {
                        Log.i("Test", "${response.code()}")
                    }
                } catch (e: HttpException) {
                    Log.i("Test", "${e.message}")
                } catch (e: Throwable) {
                    Log.i("Test", "Ooops: Something else went wrong")
                }
            }
        }
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}


