package com.example.martinzamarski.moviedatabasekotlin.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.martinzamarski.moviedatabasekotlin.model.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
