package com.example.travelagency.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class DestinationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @ColumnInfo(name = "image_url")
    val imageUrl: String = "",

    @ColumnInfo(name = "category")
    val category: String = "Adventure",

    @ColumnInfo(name = "rating")
    val rating: Float = 4.5f
)