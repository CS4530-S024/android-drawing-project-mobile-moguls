package com.example.drawingapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentMainScreenBinding

/**
 * @author          - Christian E. Anderson
 * @teammate        - Crosby White & Matthew Williams
 * @version         - 16-FEB-2024
 *
 *      This file defines the main screen for the Drawing App.
 */

/**
 *
 */
class MainScreen : Fragment() {

    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentMainScreenBinding.inflate(layoutInflater, container, false)

        // Switch to draw screen
        binding.button.setOnClickListener {
            Log.d("SPLASH", "navigating to draw screen")
            findNavController().navigate(R.id.action_mainScreen_to_drawScreen2)
        }

        // Switch to save screen
        binding.button2.setOnClickListener {
            Log.d("SPLASH", "navigating to save screen")
            findNavController().navigate(R.id.action_mainScreen_to_saveScreen2)
        }
        return binding.root
    }
}