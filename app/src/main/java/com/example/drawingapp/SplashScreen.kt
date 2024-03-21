package com.example.drawingapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentSplashScreenBinding

/**
 * @author          - Christian E. Anderson
 * @teammate        - Crosby White & Matthew Williams
 * @version         - 16-FEB-2024
 *
 *      This file defines the splash screen for the Drawing App.
 */

/**
 *
 */
@SuppressLint("CustomSplashScreen")
class SplashScreen : Fragment() {
    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSplashScreenBinding.inflate(layoutInflater, container, false)

        // Switch to main screen
        binding.mainScreenButton.setOnClickListener {
            Log.d("SPLASH", "navigating to main screen")
            findNavController().navigate(R.id.action_splashScreen_to_artGalleryScreen)
        }

        // Switch to draw screen
        binding.drawScreenButton.setOnClickListener {
            Log.d("SPLASH", "navigating to draw screen")
            findNavController().navigate(R.id.action_splashScreen_to_drawScreen2)
        }
        return binding.root
    }
}