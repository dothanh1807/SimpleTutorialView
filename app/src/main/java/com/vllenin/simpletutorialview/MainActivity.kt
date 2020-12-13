package com.vllenin.simpletutorialview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        val mapViews = mutableMapOf<String, View>()
        val textForIcon = "This is icon"
        mapViews[textForIcon] = iconX
        val textForTexView = "This is textView"
        mapViews[textForTexView] = textViewX
        val textButtonX = "This is button"
        mapViews[textButtonX] = buttonX

        Handler(Looper.getMainLooper()).postDelayed({
            tutorialView.focusToView(mapViews)
                .setCancelListener {

                }
        }, 2000)
        Log.d("XXX", "${actionBar?.isShowing}")
    }

}