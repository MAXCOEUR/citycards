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
import com.example.citycards.R
import com.squareup.picasso.Picasso

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SuccesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SuccesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var createUser: CreateUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_succes, container, false)

        val iv_imagePicker = view.findViewById<ImageView>(R.id.iv_ImagePicker)

        Picasso.get()
            .load(createUser.avatar) // Précisez le chemin du fichier avec le préfixe "file://"
            .into(iv_imagePicker)

        val buttonSuivant = view.findViewById<Button>(R.id.bt_Suivant)

        buttonSuivant.setOnClickListener{
            val changePage = Intent(requireActivity(), MainActivity::class.java)
            startActivity(changePage)
        }
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SuccesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(createUser: CreateUser) =
            SuccesFragment().apply {
                this.createUser=createUser
            }
    }
}