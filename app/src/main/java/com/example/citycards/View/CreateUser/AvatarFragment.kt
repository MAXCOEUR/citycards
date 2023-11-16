package com.example.citycards.View.CreateUser

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.citycards.Model.User
import com.example.citycards.R
import com.squareup.picasso.Picasso


class AvatarFragment : Fragment() {
    lateinit var createUser: User
    val REQUEST_IMAGE_OPEN = 1
    val REQUEST_STORAGE_PERMISSION=2
    lateinit var imagePickerView:ImageView

    val createUserViewModel by viewModels<CreateUserViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_avatar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val TitreActivity = activity?.findViewById<TextView>(R.id.tv_Titre);
        if (TitreActivity != null) {
            TitreActivity.text = "Avatar"
        }

        val buttonSuivant = view.findViewById<Button>(R.id.bt_Suivant)
        imagePickerView= view.findViewById<ImageView>(R.id.iv_ImagePicker)

        buttonSuivant.setOnClickListener {
            Log.i("click", "ButtonSuivant.setOnClickListener ")


            val userResponseCreate=createUserViewModel.createUser(createUser)

            userResponseCreate.observe(viewLifecycleOwner) { user->

                val transaction = parentFragmentManager.beginTransaction()
                val fragment = SuccesFragment.newInstance(user)
                transaction.replace(R.id.fragmentContainerView, fragment)
                transaction.commit()

            }



        }

        val myImageView = view.findViewById<ImageView>(R.id.iv_ImagePicker)

        myImageView.setOnClickListener {
            imagePicker()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == Activity.RESULT_OK) {
            val fullPhotoUri: Uri? = data?.data
            Log.i("REQUEST_IMAGE_OPEN", fullPhotoUri.toString())
            createUser.avatar=fullPhotoUri.toString();
            Picasso.get()
                .load(fullPhotoUri) // Précisez le chemin du fichier avec le préfixe "file://"
                .into(imagePickerView)
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_STORAGE_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imagePicker()
                } else {
                    // La permission a été refusée, vous pouvez informer l'utilisateur ici
                }
            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
    fun imagePicker(){
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
            // Si la permission n'a pas été accordée, demandez-la à l'utilisateur
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_STORAGE_PERMISSION)
        } else {
            // Si la permission a déjà été accordée, lancez l'intent pour sélectionner une image
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                type = "image/*"
                addCategory(Intent.CATEGORY_OPENABLE)
            }
            startActivityForResult(intent, REQUEST_IMAGE_OPEN)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(createUser: User) =
            AvatarFragment().apply {
                this.createUser=createUser
            }
    }
}