package com.example.drawingapp

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
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

    val viewModel : ViewModel by activityViewModels()

    private var canvas = Canvas()

    /**
     *
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentDrawScreenBinding.inflate(inflater)

        binding.view.setBitmap(viewModel.bitmap)
        canvas = binding.view.canvas

        val paint = Paint()
        paint.strokeWidth = 1000f
        paint.style = Paint.Style.STROKE
        paint.setColor(Color.BLACK)

        binding.view.setOnClickListener {
            binding.view.drawCircle()
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

        return binding.root
    }

    

}

private fun View.onGenericMotionEvent(function: () -> Unit) {

}
