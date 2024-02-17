package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.View


class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()
    private lateinit var drawingBitmap: Bitmap
    var canvas = Canvas()
    // TODO: Get this width programmatically
    private var destRect : Rect = Rect(0, 0, 1400, 1400)
    private val srcRect : Rect = Rect(0, 0, 500, 500)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.e("View width", width.toString())
        Log.e("View height", height.toString())

        canvas.drawBitmap(drawingBitmap, srcRect, destRect, paint)
    }

    fun setBitmap(bitmapToSet: Bitmap) {
        drawingBitmap = bitmapToSet
        canvas.setBitmap(drawingBitmap)
        Log.e("Debug", "Setting Bitmap within drawView")

        invalidate()
    }

    fun draw(x: Float, y: Float, size: Float, shape: Int) {
        paint.color = Color.RED
        paint.strokeWidth = 3F
        when (shape) {
            1 -> canvas.drawCircle(x, y, size, paint)
        }
        invalidate()
    }

}
