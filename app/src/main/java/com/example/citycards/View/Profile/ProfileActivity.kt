package com.example.citycards.View.Profile

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.citycards.Model.User
import com.example.citycards.R
import com.example.citycards.View.Main.MainActivity
import com.example.citycards.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso


class ProfileActivity : AppCompatActivity() {

    companion object {
        const val CLE_USER = "CLE_USER1"
    }

    val REQUEST_IMAGE_OPEN = 1
    val REQUEST_STORAGE_PERMISSION=2
    private lateinit var binding: ActivityProfileBinding
    lateinit var imagePickerView:ImageView
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val username = findViewById<TextView>(R.id.tv_userName)
        val email = findViewById<TextView>(R.id.tv_Email)

        val btnBack = findViewById<ImageButton>(R.id.bt_back)
        val btnSave = findViewById<Button>(R.id.bt_Save)

        imagePickerView= this.findViewById<ImageView>(R.id.iv_ImagePicker2)
        val avatar = findViewById<ImageView>(R.id.iv_ImagePicker2)
        val intent = intent
        user = intent.getSerializableExtra(CLE_USER) as User
        username.text=user.username
        email.text=user.email

        avatar.setOnClickListener {
            imagePicker()
        }

        btnSave.setOnClickListener {
            val returnIntent = Intent();
            returnIntent.putExtra(MainActivity.CLE_USER,user);
            setResult(Activity.RESULT_OK,returnIntent);
            finish();
        }

        if(user.avatar!=null){
            Picasso.get()
                .load(user.avatar) // Précisez le chemin du fichier avec le préfixe "file://"
                .into(avatar)
        }

        btnBack.setOnClickListener(){
            finish();
        }

        val fragmentManager = supportFragmentManager

        // Commencez la transaction
        val transaction = fragmentManager.beginTransaction()

        // Créez une instance du fragment que vous souhaitez afficher

        // Validez la transaction
        transaction.commit()

    }
    fun imagePicker(){
        val permission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(baseContext, permission) != PackageManager.PERMISSION_GRANTED) {
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
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_OPEN && resultCode == Activity.RESULT_OK) {
            val fullPhotoUri: Uri? = data?.data
            Log.i("REQUEST_IMAGE_OPEN", fullPhotoUri.toString())
            user = User(user.id,user.username,user.email,user.password,fullPhotoUri.toString(),user.jeton)
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
}
