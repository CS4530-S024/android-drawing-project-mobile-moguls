package com.example.drawingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
/**
 * @author          - Christian E. Anderson
 * @teammate        - Crosby White & Matthew Williams
 * @version         - 16-FEB-2024
 *
 *      This file defines the main activity for the Drawing App.
 */

/**
 *
 */
class MainActivity : AppCompatActivity() {

    /**
     *  This method creates the main activity
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}