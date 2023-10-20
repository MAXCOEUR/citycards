package com.example.citycards.View.Profile

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.citycards.Model.User
import com.example.citycards.R


class ProfileActivity : AppCompatActivity() {

    companion object {
        const val CLE_USER = "CLE_USER1"
    }
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContentView(R.layout.activity_create)

        user = intent.getSerializableExtra(CLE_USER) as User

        // Obtenez le gestionnaire de fragments
        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher
        val fragment = UserFragment.newInstance(user)

        // Remplacez le contenu du FragmentContainerView par votre fragment
        transaction.replace(R.id.fragmentContainerView, fragment)

        // Validez la transaction
        transaction.commit()

    }



}