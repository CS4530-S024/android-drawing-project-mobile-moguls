package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException


class DrawingAppRepository(private val scope: CoroutineScope,
                           private val dao: DrawingAppDAO) {
    val currentDrawing = dao.latestDrawingFilename()

    val allDrawings = dao.allDrawingFilenames()

    fun loadImage(name: String, context: Context): Bitmap? {
        val path = context.filesDir
        try {
            val file = File(path, "$name.png")
            return BitmapFactory.decodeStream(FileInputStream(file))
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun saveImage(name: String, image: Bitmap, context: Context){
        val outputStream = context.openFileOutput(name, Context.MODE_PRIVATE)
        try {
            // Use the compress method on the BitMap object to write image to outputStream
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                outputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    /***
     * Helper method used to save bitmaps to the apps file directory
     */
    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }
}