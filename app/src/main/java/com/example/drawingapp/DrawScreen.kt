package com.example.drawingapp

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
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
    private val binding : FragmentDrawScreenBinding by lazy {FragmentDrawScreenBinding.inflate(layoutInflater)}
    private var touchCoordinateScalar : Float = 1F

    /**
     *
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding.view.setBitmap(viewModel.bitmap.value!!)

        binding.view.setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN || event.action == MotionEvent.ACTION_MOVE) {
                drawOnCustomView(event.x / touchCoordinateScalar, event.y / touchCoordinateScalar,
                    viewModel.penSize.value!!.penSize, viewModel.penShape.value!!)
            }
            true
        }

        // Switch to main screen
        binding.mainScreenButton.setOnClickListener {
            findNavController().navigate(R.id.action_drawScreen2_to_mainScreen)
        }

        // Switch to save screen
        binding.saveScreenButton.setOnClickListener {
            findNavController().navigate(R.id.action_drawScreen2_to_saveScreen2)
        }

        // Listeners for pen size buttons
        binding.smallBrushButton.setOnClickListener { viewModel.setPenSize(PenSize.Small) }
        binding.mediumBrushButton.setOnClickListener { viewModel.setPenSize(PenSize.Medium) }
        binding.largeBrushButton.setOnClickListener { viewModel.setPenSize(PenSize.Large) }
        // Listeners for pen shape buttons
        binding.circleShapeButton.setOnClickListener { viewModel.setPenShape(PenShape.Circle) }
        binding.ovalShapeButton.setOnClickListener { viewModel.setPenShape(PenShape.Oval) }
        binding.squareShapeButton.setOnClickListener { viewModel.setPenShape(PenShape.Square) }
        binding.starShapeButton.setOnClickListener { viewModel.setPenShape(PenShape.Star) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun drawOnCustomView(xIn: Float, yIn: Float, size: Float, shape: PenShape) {
        // Reassign so that we can optionally adjust in the if block below
        var x: Float = xIn
        var y: Float = yIn

        if (!binding.view.widthChecked && binding.view.width > 0) {
            binding.view.instantiateRect()
            takeNoteOfViewWidth()
            // If I didn't add this, the very first dot drawn would be
            // out of place and not scaled correctly
            x /= touchCoordinateScalar
            y /= touchCoordinateScalar
        }

        val viewCanvas = binding.view.canvas
        val viewPaint = Paint()

        viewPaint.color = Color.RED
        viewPaint.strokeWidth = 3F
        val halfSize: Float = size/2f
        val ovalStretch = 2f
        when (shape) {
            PenShape.Circle -> viewCanvas.drawCircle(x, y, halfSize, viewPaint)
            PenShape.Oval -> viewCanvas.drawOval(
                RectF(x-size-ovalStretch, y-halfSize+ovalStretch,
                     x+size+ovalStretch, y+halfSize-ovalStretch)
                , viewPaint)
            PenShape.Square -> viewCanvas.drawRect(x-halfSize, y-halfSize, x+halfSize, y+halfSize, viewPaint)
            else -> {}
        }
        binding.view.invalidate()

    }

    private fun takeNoteOfViewWidth() {
        // 500f here is the hard-coded width of the internal Bitmap being used
        touchCoordinateScalar = binding.view.width / 500f
    }

    

}
