package com.example.citycards.View.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.example.citycards.Model.LoginUser
import com.example.citycards.Model.User
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.R
import com.example.citycards.View.CreateUser.CreateUserActivity
import com.example.citycards.dataBase.CityListDataBase
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class LoginActivity : AppCompatActivity() {
    val loginViewModel by viewModels<LoginViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_login)

        //On instancie la base de données
        CityListDataBase.initDatabase(this)

        val buttonSeConnecter = findViewById<Button>(R.id.bt_SeConnecter)
        val buttonCreationCompte = findViewById<Button>(R.id.bt_CreationCompte)

        val inputEmail = findViewById<TextInputEditText>(R.id.textInputChgMdp)
        val inputPassword = findViewById<TextInputEditText>(R.id.textInputChgConMdp)

        val inputLayoutUsernameEmail = findViewById<TextInputLayout>(R.id.TinputLayoutUserNameEmail)
        val inputLayoutPassword = findViewById<TextInputLayout>(R.id.textInputLayoutChgConMdp)

        //for dev
        val changePage = Intent(this, MainActivity::class.java)
        changePage.putExtra(MainActivity.CLE_USER, User(username = "username", email = "email"))
        startActivity(changePage)

        buttonSeConnecter.setOnClickListener {

            if (inputEmail.text.toString().isEmpty()) {
                inputLayoutUsernameEmail.error = "le username/Email ne peut pas etre vide"
            } else {
                inputLayoutUsernameEmail.error = null // Effacez l'erreur si le champ est valide
            }

            if (inputPassword.text.toString().isEmpty()) {
                inputLayoutPassword.error = "le mdp  ne peut pas etre vide"
            } else {
                inputLayoutPassword.error = null // Effacez l'erreur si le champ est valide
            }

            if(inputEmail.text.toString().isNotEmpty() && inputPassword.text.toString().isNotEmpty()){
                val loginUser= LoginUser(inputEmail.text.toString(),inputPassword.text.toString())

                val userResponseCreate=loginViewModel.loginUser(loginUser)

                userResponseCreate.observe(this) { user->
                    if(user.email!="" && user.username!=""){
                        val changePage = Intent(this, MainActivity::class.java)
                        changePage.putExtra(MainActivity.CLE_USER, user)
                        startActivity(changePage)
                    }
                    else{
                        Toast.makeText(this,"email ou password faux",Toast.LENGTH_LONG).show()
                    }
                }
            }


        }
        buttonCreationCompte.setOnClickListener {
            val changePage = Intent(this, CreateUserActivity::class.java)
            startActivity(changePage)
        }
        /*
        val Api = ApiClient.getApiService
        CoroutineScope(Dispatchers.IO).launch {
            val response = Api.getCities()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        Log.i("Test", response.body().toString())
                    } else {
                        Log.i("Test", "Fail")
                    }
                } catch (e: HttpException) {
                    Log.i("Test", "HttpException")
                } catch (e: Throwable) {
                    Log.i("Test", "Ooops: Something else went wrong")
                }
            }
        }*/
    }
}