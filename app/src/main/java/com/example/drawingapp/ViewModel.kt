package com.example.drawingapp

import android.graphics.Bitmap
import androidx.core.graphics.set
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
class ViewModel : ViewModel() {

    private val _bitmap : MutableLiveData<Bitmap> = MutableLiveData(Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888))

    var bitmap = Bitmap.createBitmap(128, 128, Bitmap.Config.ARGB_8888)


}