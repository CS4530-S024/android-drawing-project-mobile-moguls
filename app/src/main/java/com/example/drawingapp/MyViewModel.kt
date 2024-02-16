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

/**
 *
 */
class MyViewModel : ViewModel() {

    private val _bitmap : MutableLiveData<Bitmap> = MutableLiveData(
        Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
    )
    val bitmap = _bitmap as LiveData<Bitmap>


}