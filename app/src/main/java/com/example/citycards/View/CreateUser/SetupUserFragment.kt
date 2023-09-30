package com.example.citycards.View.CreateUser

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.citycards.Model.CreateUser
import com.example.citycards.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

/**
 * A simple [Fragment] subclass.
 * Use the [SetupUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetupUserFragment : Fragment() {
    lateinit var createUser: CreateUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_creation, container, false)

        val textInputUserName = view.findViewById<TextInputEditText>(R.id.TinputUserName)
        val textInputEmail = view.findViewById<TextInputEditText>(R.id.TinputEmail)

        val textInputLayoutUserName = view.findViewById<TextInputLayout>(R.id.TinputLayoutUserName)
        val textInputLayoutEmail = view.findViewById<TextInputLayout>(R.id.TinputLayoutEmail)

        val buttonSuivant = view.findViewById<Button>(R.id.bt_Suivant)

        buttonSuivant.setOnClickListener {
            Log.i("click", "ButtonSuivant.setOnClickListener ")


            if (textInputUserName.text.toString().isEmpty()) {
                textInputLayoutUserName.error = "Le nom d'utilisateur ne peut pas être vide"
            } else {
                textInputLayoutUserName.error = null // Effacez l'erreur si le champ est valide
            }

            if (textInputEmail.text.toString().isEmpty()|| !isValidEmail(textInputEmail.text.toString())) {
                textInputLayoutEmail.error = "L'email est invalide"
            } else {
                textInputLayoutEmail.error = null // Effacez l'erreur si le champ est valide
            }

            if (textInputUserName.text.toString().isNotEmpty() && textInputEmail.text.toString().isNotEmpty() && isValidEmail(textInputEmail.text.toString())) {
                // Commencez la transaction

                createUser.email=textInputEmail.text.toString()
                createUser.username=textInputUserName.text.toString()

                val transaction = parentFragmentManager.beginTransaction()

                // Créez une instance du fragment que vous souhaitez afficher
                val fragment = MotDePasseFragment.newInstance(createUser)

                // Remplacez le contenu du FragmentContainerView par votre fragment
                transaction.replace(R.id.fragmentContainerView, fragment)

                // Validez la transaction
                transaction.commit()
            }

        }

        return view
    }
    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")
        return emailRegex.matches(email)
    }

    companion object {
        @JvmStatic
        fun newInstance(createUser: CreateUser) =
            SetupUserFragment().apply {
                this.createUser=createUser
            }
    }
}