package com.example.drawingapp

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.drawingapp.databinding.FragmentDrawScreenBinding


/**
 * @author          - Christian E. Anderson
 * @teammate        - Crosby White & Matthew Williams
 * @version         - 16-FEB-2024
 *
 *      This file defines the draw screen for the Drawing App.
 */

/**
 *
 */
class DrawScreen : Fragment() {

    private val viewModel : MyViewModel by activityViewModels()
    private var canvas = Canvas()

    /**
     *
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentDrawScreenBinding.inflate(inflater)

        binding.view.setBitmap(viewModel.bitmap.value!!)
        canvas = binding.view.canvas

        binding.view.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
                val location = IntArray(2)
                v.getLocationOnScreen(location)
                binding.view.draw(event.x / 2.8f, event.y / 2.8f, viewModel.penSize.value!!.circleSize)
            }
            true
        }

        // Switch to main screen
        binding.mainScreenButton.setOnClickListener {
            Log.d("SPLASH", "navigating to main screen")
            findNavController().navigate(R.id.action_drawScreen2_to_mainScreen)
        }

        // Switch to save screen
        binding.saveScreenButton.setOnClickListener {
            Log.d("SPLASH", "navigating to save screen")
            findNavController().navigate(R.id.action_drawScreen2_to_saveScreen2)
        }

        // Listeners for pen size buttons
        binding.smallBrushButton.setOnClickListener {
            viewModel.setPenSize(PenSize.Small)
        }
        binding.mediumBrushButton.setOnClickListener {
            viewModel.setPenSize(PenSize.Medium)
        }
        binding.largeBrushButton.setOnClickListener {
            viewModel.setPenSize(PenSize.Large)
        }

        return binding.root
    }

    

}
