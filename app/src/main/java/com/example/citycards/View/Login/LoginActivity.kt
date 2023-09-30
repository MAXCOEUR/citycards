package com.example.citycards.View.Login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import com.example.citycards.Model.User
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.R
import com.example.citycards.View.CreateUser.CreateUserActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_login)

        val buttonSeConnecter = findViewById<Button>(R.id.bt_SeConnecter)
        val buttonCreationCompte = findViewById<Button>(R.id.bt_CreationCompte)

        buttonSeConnecter.setOnClickListener {
            val changePage = Intent(this, MainActivity::class.java)
            val  user = User(0,"max","email","mdp",null,0)
            changePage.putExtra(MainActivity.CLE_USER, user)
            startActivity(changePage)
        }
        buttonCreationCompte.setOnClickListener {
            val changePage = Intent(this, CreateUserActivity::class.java)
            startActivity(changePage)
        }
    }
}