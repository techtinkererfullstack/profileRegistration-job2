package com.example.job2nsda

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Profile::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao

    companion object {
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            instance = Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "app_database"
            ).build()



            return instance!!
        }

    }


}