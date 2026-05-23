package com.example.travelagency.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookings",
    foreignKeys = [
        ForeignKey(
            entity = DestinationEntity::class,
            parentColumns = ["id"],
            childColumns = ["destination_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class BookingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "travelers")
    val travelers: Int,

    @ColumnInfo(name = "destination_id", index = true)
    val destinationId: Long,

    @ColumnInfo(name = "total_price")
    val totalPrice: Int,

    @ColumnInfo(name = "booked_at")
    val bookedAt: Long = System.currentTimeMillis()
)