package com.example.citycards.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import com.example.citycards.R
import com.example.citycards.View.CreateFragment.CreationFragment

class CreateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)
        // Obtenez le gestionnaire de fragments
        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher
        val fragment = CreationFragment.newInstance()

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