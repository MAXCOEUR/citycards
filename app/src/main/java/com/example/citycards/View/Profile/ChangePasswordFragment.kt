package com.example.citycards.View.Profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.OnBackPressedCallback
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.Repository.UserRepository
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.dataSource.CacheDataSource
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso

class ChangePasswordFragment : Fragment()  {
    val REQUEST_IMAGE_OPEN = 1
    val REQUEST_STORAGE_PERMISSION=2
    lateinit var imagePickerView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val transaction = parentFragmentManager.beginTransaction()

                // Créez une instance du fragment que vous souhaitez afficher
                val fragment = UserFragment.newInstance()

                // Remplacez le contenu du FragmentContainerView par votre fragment
                transaction.replace(R.id.fragmentContainerView, fragment)

                // Validez la transaction
                transaction.commit();
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_change_password,container,false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onCreate(savedInstanceState)
        val btnSave = view.findViewById<Button>(R.id.btn_Save)
        val intent = Intent()

        val textInputMdp = view.findViewById<TextInputEditText>(R.id.textInputChgMdp)
        val textInputConfMdp= view.findViewById<TextInputEditText>(R.id.textInputChgConMdp)

        val textInputLayoutMdp = view.findViewById<TextInputLayout>(R.id.textInputLayoutChgMdp)
        val textInputLayoutConfMdp = view.findViewById<TextInputLayout>(R.id.textInputLayoutChgConMdp)

        btnSave.setOnClickListener {
            Log.i("click", "ButtonSuivant.setOnClickListener ")

            if (textInputMdp.text.toString().isEmpty()|| !isValidPassword(textInputMdp.text.toString())) {
                textInputLayoutMdp.error = "le mdp est invalide\n - Au moins 8 caractères de longueur\n" +
                        "- Contient au moins une lettre majuscule\n" +
                        "- Contient au moins une lettre minuscule\n" +
                        "- Contient au moins un chiffre"
            } else {
                textInputLayoutMdp.error = null
            }

            if (textInputConfMdp.text.toString().isEmpty()|| textInputConfMdp.text.toString()!=textInputMdp.text.toString()) {
                textInputLayoutConfMdp.error = "le mdp est pas le meme"
            } else {
                textInputLayoutConfMdp.error = null
            }

            if (textInputMdp.text.toString().isNotEmpty() && isValidPassword(textInputMdp.text.toString()) && textInputConfMdp.text.toString().isNotEmpty() && textInputConfMdp.text.toString()==textInputMdp.text.toString()){
                var user = User(UserRepository.getUserLogin().id,UserRepository.getUserLogin().username,UserRepository.getUserLogin().email,textInputMdp.text.toString(),UserRepository.getUserLogin().avatar,UserRepository.getUserLogin().token)
                val returnIntent = Intent();
                UserRepository.setUserLogin(user)
                activity?.setResult(Activity.RESULT_OK, returnIntent);
                activity?.onBackPressed()
            }
        }
        val btnBack = activity?.findViewById<ImageButton>(R.id.bt_back)
        if (btnBack != null) {
            btnBack.setOnClickListener(){
                activity?.onBackPressed()
            }
        }
    }



    companion object {
        @JvmStatic
        fun newInstance() =
            ChangePasswordFragment().apply {
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == Activity.RESULT_OK) {
            val fullPhotoUri: Uri? = data?.data
            Log.i("REQUEST_IMAGE_OPEN", fullPhotoUri.toString())
            UserRepository.setUserLogin(User(UserRepository.getUserLogin().id,UserRepository.getUserLogin().username,UserRepository.getUserLogin().email,UserRepository.getUserLogin().password,fullPhotoUri.toString(),UserRepository.getUserLogin().token))
            Picasso.get()
                .load(fullPhotoUri) // Précisez le chemin du fichier avec le préfixe "file://"
                .into(imagePickerView)
        }
    }

}