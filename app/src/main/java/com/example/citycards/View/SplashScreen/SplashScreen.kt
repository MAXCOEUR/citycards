package com.example.citycards.View.SplashScreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.activity.viewModels
import com.example.citycards.R
import com.example.citycards.View.Main.MainActivity

class SplashScreen : AppCompatActivity() {
    val spashViewModel by viewModels<SplashScreenViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spash_screen)

        val imageView = findViewById<ImageView>(R.id.logo)
        applyReboundZoomAnimation(imageView,Int.MAX_VALUE)

        val getdata=spashViewModel.getData()

        getdata.observe(this) {
            val changePage = Intent(this, MainActivity::class.java)
            startActivity(changePage)
            finish()
        }
    }
    fun applyReboundZoomAnimation(imageView: ImageView, repeatCount: Int) {
        val zoomFactor = 1.2f // Change this value according to your desired zoom level

        val zoomInAnimation = ScaleAnimation(
            1.0f, zoomFactor, // X-axis scale from 1.0 to zoomFactor
            1.0f, zoomFactor, // Y-axis scale from 1.0 to zoomFactor
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point (X-axis) - center
            Animation.RELATIVE_TO_SELF, 0.5f // Pivot point (Y-axis) - center
        )
        zoomInAnimation.duration = 500 // Duration of zoom-in animation

        val zoomOutAnimation = ScaleAnimation(
            zoomFactor, 1.0f, // X-axis scale from zoomFactor to 1.0
            zoomFactor, 1.0f, // Y-axis scale from zoomFactor to 1.0
            Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point (X-axis) - center
            Animation.RELATIVE_TO_SELF, 0.5f // Pivot point (Y-axis) - center
        )
        zoomOutAnimation.duration = 500 // Duration of zoom-out animation
        zoomOutAnimation.startOffset = 300 // Delay before starting zoom-out animation

        val animationSet = AnimationSet(true)
        animationSet.addAnimation(zoomInAnimation)
        animationSet.addAnimation(zoomOutAnimation)

        animationSet.repeatCount = repeatCount // Set the number of times to repeat the animation
        imageView.startAnimation(animationSet)

        animationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Animation has ended, restart the animation
                imageView.startAnimation(animationSet)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}