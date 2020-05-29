package com.example.showsomeplaces.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class FoundedPlace(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val rating: String = "",
    val poi: String = "",
    val latitude: String = "",
    val longitude: String = ""
) : Parcelable