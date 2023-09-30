package com.example.citycards.View.CreateUser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import com.example.citycards.Model.CreateUser
import com.example.citycards.R
import com.example.citycards.View.CreateUser.CreationFragment

class CreateActivity : AppCompatActivity() {

    var createUser:CreateUser= CreateUser()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_create)
        // Obtenez le gestionnaire de fragments
        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher
        val fragment = CreationFragment.newInstance(createUser)

        // Remplacez le contenu du FragmentContainerView par votre fragment
        transaction.replace(R.id.fragmentContainerView, fragment)

        // Validez la transaction
        transaction.commit()

        val buttonBack = findViewById<ImageButton>(R.id.bt_back)
        buttonBack.setOnClickListener {
            onBackPressed()
        }
    }
}