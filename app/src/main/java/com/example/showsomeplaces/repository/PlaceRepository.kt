package com.example.showsomeplaces.repository

import android.content.Context
import com.example.showsomeplaces.database.PlaceDatabase
import com.example.showsomeplaces.database.dao.PlaceDao
import com.example.showsomeplaces.model.Place

class PlaceRepository(private val context: Context) {

    private val placeDao: PlaceDao by lazy { PlaceDatabase.getDatabase(context).placeDao() }

    fun insertPlace(place: Place) {
        placeDao.insert(place)
    }

    fun getAllPlaces(): List<Place> = placeDao.getAllPlaces()
}