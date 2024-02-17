package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View


class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val paint = Paint()
    private lateinit var drawingBitmap: Bitmap
    var canvas = Canvas()
    var widthChecked: Boolean = false;
    // This will get overridden in the instantiateRect method
    private var destRect : Rect = Rect(0, 0, 500, 500)
    private val srcRect : Rect = Rect(0, 0, 500, 500)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(drawingBitmap, srcRect, destRect, paint)
    }

    fun setBitmap(bitmapToSet: Bitmap) {
        drawingBitmap = bitmapToSet
        canvas.setBitmap(drawingBitmap)
    }

    fun instantiateRect() {
        destRect = Rect(0, 0, width, width)
        widthChecked = true
    }

}
