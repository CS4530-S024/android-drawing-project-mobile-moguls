package com.example.drawingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity

/**
 * @author          - Christian E. Anderson
 * @teammate        - Crosby White & Matthew Williams
 * @version         - 16-FEB-2024
 *
 *      This file defines the main activity for the Drawing App.
 */

class MainActivity : AppCompatActivity() {

    /**
     *  This method creates the main activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val myViewModel : MyViewModel by viewModels()

        myViewModel.bitmap.observe(this) {
            Log.e("Debug", "Bitmap changed!")
        }

        setContentView(R.layout.activity_main)
    }
}