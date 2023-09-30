package com.example.citycards.View.CreateUser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.Model.CreateUser
import com.example.citycards.Model.User
import com.example.citycards.R
import com.squareup.picasso.Picasso

class SuccesFragment : Fragment() {
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_succes, container, false)

        val iv_imagePicker = view.findViewById<ImageView>(R.id.iv_ImagePicker)

        if(user.avatar!=null){
            Picasso.get()
                .load(user.avatar) // Précisez le chemin du fichier avec le préfixe "file://"
                .into(iv_imagePicker)
        }


        val buttonSuivant = view.findViewById<Button>(R.id.bt_Suivant)

        buttonSuivant.setOnClickListener{
            val changePage = Intent(requireActivity(), MainActivity::class.java)
            changePage.putExtra(MainActivity.CLE_USER, user)
            startActivity(changePage)
        }
        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) =
            SuccesFragment().apply {
                this.user=user
            }
    }
}