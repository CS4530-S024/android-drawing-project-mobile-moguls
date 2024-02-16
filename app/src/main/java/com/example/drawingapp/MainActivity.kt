package com.example.drawingapp

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.example.drawingapp.databinding.ActivityMainBinding
import com.example.drawingapp.databinding.FragmentDrawScreenBinding

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
        val myViewModel : ViewModel by viewModels()

        setContentView(R.layout.activity_main)
    }
}