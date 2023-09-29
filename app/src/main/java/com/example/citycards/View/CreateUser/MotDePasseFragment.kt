package com.example.citycards.View.CreateUser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.citycards.Model.CreateUser
import com.example.citycards.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MotDePasseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MotDePasseFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var createUser: CreateUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mot_de_passe, container, false)

        val textInputMdp = view.findViewById<TextInputEditText>(R.id.TinputMotDePasse)
        val textInputConfMdp= view.findViewById<TextInputEditText>(R.id.TinputConfirmationMotDePasse)

        val textInputLayoutMdp = view.findViewById<TextInputLayout>(R.id.TinputLayoutMotdePasse_creation)
        val textInputLayoutConfMdp = view.findViewById<TextInputLayout>(R.id.TinputLayoutConfirmationMotdePasse)

        val buttonSuivant = view.findViewById<Button>(R.id.bt_Suivant)

        buttonSuivant.setOnClickListener {
            Log.i("click", "ButtonSuivant.setOnClickListener ")

            if (textInputMdp.text.toString().isEmpty()|| !isValidPassword(textInputMdp.text.toString())) {
                textInputLayoutMdp.error = "le mdp est invalide\n - Au moins 8 caractères de longueur\n" +
                        "- Contient au moins une lettre majuscule\n" +
                        "- Contient au moins une lettre minuscule\n" +
                        "- Contient au moins un chiffre"
            } else {
                textInputLayoutMdp.error = null // Effacez l'erreur si le champ est valide
            }

            if (textInputConfMdp.text.toString().isEmpty()|| textInputConfMdp.text.toString()!=textInputMdp.text.toString()) {
                textInputLayoutConfMdp.error = "le mdp est pas le meme"
            } else {
                textInputLayoutConfMdp.error = null // Effacez l'erreur si le champ est valide
            }

            if (textInputMdp.text.toString().isNotEmpty() && isValidPassword(textInputMdp.text.toString()) && textInputConfMdp.text.toString().isNotEmpty() && textInputConfMdp.text.toString()==textInputMdp.text.toString()){

                createUser.password=textInputMdp.text.toString()

                val transaction = parentFragmentManager.beginTransaction()

                // Créez une instance du fragment que vous souhaitez afficher
                val fragment = AvatarFragment.newInstance(createUser)

                // Remplacez le contenu du FragmentContainerView par votre fragment
                transaction.replace(R.id.fragmentContainerView, fragment)

                // Validez la transaction
                transaction.commit()
            }

        }

        return view
    }
    fun isValidPassword(password: String): Boolean {
        // Exemple de critères de validation du mot de passe :
        // - Au moins 8 caractères de longueur
        // - Contient au moins une lettre majuscule
        // - Contient au moins une lettre minuscule
        // - Contient au moins un chiffre

        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}\$")

        return passwordRegex.matches(password)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MotDePasseFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(createUser: CreateUser) =
            MotDePasseFragment().apply {
                this.createUser=createUser
            }
    }
}