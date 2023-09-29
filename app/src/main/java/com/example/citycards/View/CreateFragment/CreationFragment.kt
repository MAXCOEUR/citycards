package com.example.citycards.View.CreateFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.citycards.R

/**
 * A simple [Fragment] subclass.
 * Use the [CreationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreationFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_creation, container, false)

        val buttonSuivant = view.findViewById<Button>(R.id.bt_Suivant)

        buttonSuivant.setOnClickListener {
            Log.i("click", "ButtonSuivant.setOnClickListener ")
            // Commencez la transaction
            val transaction = parentFragmentManager.beginTransaction()

            // Créez une instance du fragment que vous souhaitez afficher
            val fragment = MotDePasseFragment.newInstance()

            // Remplacez le contenu du FragmentContainerView par votre fragment
            transaction.replace(R.id.fragmentContainerView, fragment)

            // Validez la transaction
            transaction.commit()
        }

        return view
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String?=null, param2: String?=null) =
            CreationFragment().apply {
            }
    }
}