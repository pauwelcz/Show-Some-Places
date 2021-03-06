package com.example.showsomeplaces.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.showsomeplaces.database.dao.PlaceDao
import com.example.showsomeplaces.model.Place

@Database(entities = [Place::class], version = 2)
abstract class PlaceDatabase : RoomDatabase() {

    abstract fun placeDao(): PlaceDao

    companion object {
        private const val DATABASE_NAME = "showsomeplaces.db"

        fun getDatabase(context: Context): PlaceDatabase =
            Room.databaseBuilder(context, PlaceDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

    }
}