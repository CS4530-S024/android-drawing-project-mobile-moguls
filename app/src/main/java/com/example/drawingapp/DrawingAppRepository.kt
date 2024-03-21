package com.example.drawingapp

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException


class DrawingAppRepository(private val scope: CoroutineScope,
                           private val dao: DrawingAppDAO) {

    // val currentDrawing = dao.latestDrawing()
    val allDrawings = dao.allDrawing()

    fun loadImage(fileName: String, context: Context): Bitmap? {
        val inputStream: FileInputStream
        var bitmap: Bitmap
        try {
            inputStream = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(inputStream);
            inputStream.close();
            return bitmap
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        return null
    }

    fun saveImage(fileName: String, image: Bitmap, context: Context){
        var success = true

        val outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE)
        try {
            // Use the compress method on the BitMap object to write image to outputStream
            image.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        } catch (e: Exception) {
            success = false
            e.printStackTrace()
        } finally {
            try {
                outputStream!!.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            // Assuming the file actually got saved, send the filename to the DB
            if (success) {
                scope.launch {
                    dao.addDrawing(Drawing(fileName))
                }
            }
        }
    }

}