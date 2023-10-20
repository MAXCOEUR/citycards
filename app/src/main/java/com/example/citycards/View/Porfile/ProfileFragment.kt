package com.example.citycards.View.Porfile

import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.View.Main.dashboard.DashboardFragment
import com.example.citycards.View.Main.home.HomeFragment
import com.example.citycards.View.Main.notifications.NotificationsFragment
import com.example.citycards.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ProfileFragment : AppCompatActivity() {

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
        user = intent.getSerializableExtra(ProfileFragment.CLE_USER) as User


        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher


        // Validez la transaction
        transaction.commit()



    }
}
