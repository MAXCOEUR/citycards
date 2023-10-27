package com.example.citycards.View.Main.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.citycards.R
import com.example.citycards.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState);
        val TitreActivity = activity?.findViewById<TextView>(R.id.tv_titre);
        if (TitreActivity != null) {
            TitreActivity.text = "Collection"
        }
    }
    companion object {
        @JvmStatic
        fun newInstance(param1: String?=null, param2: String?=null) =
            NotificationsFragment().apply {

            }
    }
}