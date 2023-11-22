package com.example.citycards.View.CreateUser

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.Repository.UserRepository
import com.example.citycards.dataSource.CacheDataSource
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
        return inflater.inflate(R.layout.fragment_succes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val TitreActivity = activity?.findViewById<TextView>(R.id.tv_Titre);
        if (TitreActivity != null) {
            TitreActivity.text = "Succes"
        }

        val tv_userName = activity?.findViewById<TextView>(R.id.tv_userName);
        val tv_email = activity?.findViewById<TextView>(R.id.tv_email);

        tv_userName?.text=user.username;
        tv_email?.text=user.email;

        val iv_imagePicker = view.findViewById<ImageView>(R.id.iv_ImagePicker)

        if(user.avatar!=null){
            Picasso.get()
                .load(user.avatar) // Précisez le chemin du fichier avec le préfixe "file://"
                .into(iv_imagePicker)
        }


        val buttonSuivant = view.findViewById<Button>(R.id.bt_Suivant)

        buttonSuivant.setOnClickListener{
            UserRepository.setUserLogin(user)
            val changePage = Intent(requireActivity(), MainActivity::class.java)
            startActivity(changePage)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(user: User) =
            SuccesFragment().apply {
                this.user=user
            }
    }
}