package com.example.citycards.View.Profile

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
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.View.Login.LoginActivity
import com.example.citycards.View.Main.MainActivity
import com.squareup.picasso.Picasso


class UserFragment : Fragment() {
    val REQUEST_IMAGE_OPEN = 1
    val REQUEST_STORAGE_PERMISSION = 2
    lateinit var imagePickerView: ImageView
    lateinit var user: User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        super.onCreate(savedInstanceState)
        val username = view.findViewById<TextView>(R.id.tv_userName)
        val email = view.findViewById<TextView>(R.id.tv_Email)

        val btnSave = view.findViewById<Button>(R.id.bt_Save)
        val btnChgMdp = view.findViewById<Button>(R.id.bt_ChgMdp)
        val btnExit = view.findViewById<ImageButton>(R.id.bt_Exit)

        imagePickerView = view.findViewById<ImageView>(R.id.iv_ImagePicker2)
        val avatar = view.findViewById<ImageView>(R.id.iv_ImagePicker2)
        val intent = Intent()


        username.text = user.username
        email.text = user.password

        avatar.setOnClickListener {
            imagePicker()
        }

        btnExit.setOnClickListener{
            val changePage = Intent(requireActivity(), LoginActivity::class.java)
            changePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(changePage)
        }

        btnSave.setOnClickListener {
            val returnIntent = Intent();
            returnIntent.putExtra(MainActivity.CLE_USER, user);
            activity?.setResult(Activity.RESULT_OK, returnIntent);
            activity?.finish();
        }

        btnChgMdp.setOnClickListener {
            val transaction = parentFragmentManager.beginTransaction()

            // Créez une instance du fragment que vous souhaitez afficher
            val fragment = ChangePasswordFragment.newInstance(user)

            // Remplacez le contenu du FragmentContainerView par votre fragment
            transaction.replace(R.id.fragmentContainerView, fragment)

            // Validez la transaction
            transaction.commit()
        }

        if (user.avatar != null) {
            Picasso.get()
                .load(user.avatar) // Précisez le chemin du fichier avec le préfixe "file://"
                .into(avatar)
        }

        val btnBack = activity?.findViewById<ImageButton>(R.id.bt_back)
        if (btnBack != null) {
            btnBack.setOnClickListener(){
                activity?.onBackPressed()
            }
        }

    }


    fun imagePicker() {
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (context?.let {
                ContextCompat.checkSelfPermission(
                    it,
                    permission
                )
            } != PackageManager.PERMISSION_GRANTED) {
            // Si la permission n'a pas été accordée, demandez-la à l'utilisateur
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_STORAGE_PERMISSION
            )
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
        fun newInstance(user: User) =
            UserFragment().apply {
                this.user = user
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == Activity.RESULT_OK) {
            val fullPhotoUri: Uri? = data?.data
            Log.i("REQUEST_IMAGE_OPEN", fullPhotoUri.toString())
            user = User(
                user.id,
                user.username,
                user.email,
                user.password,
                fullPhotoUri.toString(),
                user.token
            )
            Picasso.get()
                .load(fullPhotoUri) // Précisez le chemin du fichier avec le préfixe "file://"
                .into(imagePickerView)
        }
    }
}




