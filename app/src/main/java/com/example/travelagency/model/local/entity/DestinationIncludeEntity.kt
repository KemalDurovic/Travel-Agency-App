package com.example.travelagency.model.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

// One Destination has Many DestinationIncludes (One-to-Many relationship)
@Entity(
    tableName = "destination_includes",
    foreignKeys = [
        ForeignKey(
            entity = DestinationEntity::class,
            parentColumns = ["id"],
            childColumns = ["destination_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class DestinationIncludeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = "destination_id", index = true)
    val destinationId: Long,

    @ColumnInfo(name = "include_item")
    val includeItem: String
)