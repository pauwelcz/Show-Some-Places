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

    fun deletePlace(place: Place) {
        placeDao.delete(place)
    }

    fun deleteById(id: Long) {
        placeDao.deleteById(id)
    }

    fun updatePlace(place: Place) {
        placeDao.update(place)
    }
}