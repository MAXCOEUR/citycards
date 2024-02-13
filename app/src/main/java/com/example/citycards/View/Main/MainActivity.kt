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
import com.example.citycards.Model.LoginUser
import com.example.citycards.Model.QueryDataCity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.Repository.UserRepository
import com.example.citycards.View.CityDetail.CityDetail
import com.example.citycards.View.Login.LoginActivity
import com.example.citycards.View.Login.LoginViewModel
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
    val loginViewModel by viewModels<LoginViewModel>()
    companion object {
        //const val CLE_USER = "CLE_USER1"
        const val CLE_USER_RETURN = 1
    }

    private lateinit var binding: ActivityMainBinding
    lateinit var homeFragment :Fragment
    lateinit var searchFragment :Fragment
    lateinit var collectionFragment :Fragment

    lateinit var jetons:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //On instancie la base de données
        CityListDataBase.initDatabase(this)

        val btProfile = findViewById<ImageButton>(R.id.bt_profil)
        val btnTirage = findViewById<ImageButton>(R.id.image_rond)
        val switchTirage = findViewById<Switch>(R.id.switch1)
        jetons = findViewById(R.id.tv_nbrJeton)
        jetons.text = UserRepository.getUserLogin().token.toString()
        val btToken = findViewById<ConstraintLayout>(R.id.bt_token)

        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher
        homeFragment = HomeFragment.newInstance()
        searchFragment = SearchFragment.newInstance()
        collectionFragment = CollectionFragment.newInstance()

        if (UserRepository.getUserLogin().email == ""){
            val sh = getSharedPreferences("MySharedPref", MODE_PRIVATE)
            val email = sh.getString("email", "").toString()
            val psw = sh.getString("password", "").toString()

            val userResponseCreate=loginViewModel.loginUser(LoginUser(email,psw))

            userResponseCreate.observe(this) { user->
                if(user.email!="" && user.username!=""){
                    UserRepository.setUserLogin(user)
                    mainViewModel.updateUser(UserRepository.getUserLogin())
                    updateToken()
                    // Remplacez le contenu du FragmentContainerView par votre fragment
                    transaction.replace(R.id.nav_host_fragment_activity_main, homeFragment)

                    // Validez la transaction
                    transaction.commit()
                }
                else{
                    val changePage = Intent(this, LoginActivity::class.java)
                    startActivity(changePage)
                }

            }

        }

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
            val lastClaim = UserRepository.getUserLogin().lastClaimToken
            val cooldownClaim = TimeUnit.DAYS.toMillis(1) // 1 jour de délai entre la récupération des jetons
            if (Date().time - lastClaim  > cooldownClaim){
                UserRepository.getUserLogin().token += 30
                Toast.makeText(this,"+ 30 Jetons !",Toast.LENGTH_SHORT).show()
                UserRepository.getUserLogin().lastClaimToken = Date().time

                mainViewModel.updateUser(UserRepository.getUserLogin())
            }
            else{
                var prochainClaim = cooldownClaim - ((Date().time - lastClaim) % cooldownClaim)
                val sdf = SimpleDateFormat("HH:mm:ss")
                val date =sdf.format(prochainClaim)
                Toast.makeText(this, "Prochain tirage possible dans $date", Toast.LENGTH_SHORT).show()
            }
            updateToken()
        }

    }

    fun updateToken(){
        jetons.text = UserRepository.getUserLogin().token.toString()
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

    override fun onResume() {
        super.onResume()
        updateToken()

    }
}


