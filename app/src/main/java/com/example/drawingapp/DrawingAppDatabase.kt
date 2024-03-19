package com.example.drawingapp

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.flow.Flow

@Database(entities = [DrawScreen::class], version = 1, exportSchema = false)
abstract class DrawingAppDatabase : RoomDatabase() {
    abstract fun drawingAppDao(): DrawingAppDAO

    companion object {
        @Volatile
        private var INSTANCE: DrawingAppDatabase? = null

        fun getDatabase(context: Context): DrawingAppDatabase {
            return INSTANCE ?: synchronized(this) {
                if(INSTANCE != null) return INSTANCE!!

                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DrawingAppDatabase::class.java,
                    "drawingapp_database"
                    ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}


@Dao
interface DrawingAppDAO {
    @Insert
    suspend fun addDrawingAppFilename(filename: String)

    @Query("SELECT * from drawings ORDER BY fileName DESC LIMIT 1")
    fun latestDrawingFilename() : Flow<String>

    @Query("SELECT * from drawings ORDER BY fileName DESC")
    fun allDrawingFilenames() : Flow<List<String>>

    @Query("SELECT :name from drawings")
    fun getFilename(name: String) : String


}