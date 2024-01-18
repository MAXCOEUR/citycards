package com.example.citycards.View.TirageCard

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import com.example.citycards.R
import kotlinx.coroutines.selects.select
import java.util.Timer
import java.util.TimerTask

class TransitionTirage : Fragment() {
    lateinit var background:ConstraintLayout
    var compteur:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        background = view.findViewById(R.id.frameLayout2)
    }

    fun startTimer(){
        timer.start()
    }

    fun stopTimer(){
        timer.cancel()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transition_tirage, container, false)

    }

    companion object {
        fun newInstance() =
            TransitionTirage().apply {

            }
    }
    val timer = object : CountDownTimer(9999999, 500) {
        override fun onTick(p0: Long) {
            changeBackGround(background, compteur)
            compteur += 1
        }

        override fun onFinish() {

        }
    }

    fun changeBackGround(background: ConstraintLayout,compteur: Int){
        var compteur = (compteur % 5) + 1
        when(compteur){
            1 -> {Log.e("primaryContainer","primary")
                background.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.primaryContainer))}
            2 -> {Log.e("secondaryContainer","secondary")
                background.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.secondaryContainer))}
            3 -> {Log.e("primary","primary 2")
                background.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.primary))}
            4 -> {Log.e("secondary","secondary 2")
                background.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.secondary))}
            5 -> {Log.e("tertiary","tertiary")
                background.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.tertiary))}
        }
    }
}