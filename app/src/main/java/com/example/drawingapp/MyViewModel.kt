package com.example.drawingapp

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * @author          - Christian E. Anderson
 * @teammate        - Crosby White & Matthew Williams
 * @version         - 16-FEB-2024
 *
 *      This file defines the view model for the Drawing App.
 */

enum class PenSize(val penSize: Float) {
    Small(6F), Medium(12F), Large(20F), ExtraLarge(50F)
}

enum class PenShape(val penShape: Int) {
    Circle(1), Oval(2)
}

/**
 *
 */
class MyViewModel : ViewModel() {

    private val _bitmap : MutableLiveData<Bitmap> = MutableLiveData(
        Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
    )
    val bitmap = _bitmap as LiveData<Bitmap>

    private var _penSize : MutableLiveData<PenSize> = MutableLiveData(
        PenSize.Medium
    )
    var penSize = _penSize as LiveData<PenSize>

    private var _penShape : MutableLiveData<PenShape> = MutableLiveData(
        PenShape.Circle
    )

    var penShape = _penShape as LiveData<PenShape>

    fun setPenSize(newSize: PenSize) {
        _penSize.value = newSize
    }


}