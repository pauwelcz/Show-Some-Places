package com.example.showsomeplaces.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.showsomeplaces.model.Place

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(place: Place)

    @Query("SELECT * FROM place")
    fun getAllPlaces(): List<Place>

}