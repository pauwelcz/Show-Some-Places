package com.example.showsomeplaces.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Place(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String = "",
    val note: String = "",
    val date: Long = 0L,
    val poi: String = "",
    val user: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val imageByteArray: ByteArray? = null
) : Parcelable