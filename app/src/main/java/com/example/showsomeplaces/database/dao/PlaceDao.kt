package com.example.showsomeplaces.database.dao

import androidx.room.*
import com.example.showsomeplaces.model.Place

@Dao
interface PlaceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(place: Place)

    @Query("SELECT * FROM place")
    fun getAllPlaces(): List<Place>

    @Delete
    fun delete(place: Place)

    @Query("DELETE FROM place WHERE id = :id")
    fun deleteById(id: Long)

    @Update
    fun update(place: Place)
}