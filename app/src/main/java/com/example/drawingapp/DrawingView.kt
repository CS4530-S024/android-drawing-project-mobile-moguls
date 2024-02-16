package com.example.drawingapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LiveData


class DrawingView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private val path: Path = Path()
    private val paint = Paint()
    private lateinit var drawingBitmap : Bitmap
    var canvas = Canvas()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(drawingBitmap, 0f, 0f, null)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
    }

    fun setBitmap(bitmapToSet: Bitmap) {
        this.drawingBitmap = bitmapToSet
        invalidate()
    }

    fun drawCircle() {
    }

//    fun getCanvas(): Canvas {
//        return canvas
//    }


}
