package com.example.travelagency.model.local.dao

import androidx.room.Embedded
import androidx.room.Relation
import com.example.travelagency.model.local.entity.DestinationEntity
import com.example.travelagency.model.local.entity.DestinationIncludeEntity

// Result class for the One-to-Many relationship:
// One DestinationEntity has Many DestinationIncludeEntities
data class DestinationWithIncludes(
    @Embedded val destination: DestinationEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "destination_id"
    )
    val includes: List<DestinationIncludeEntity>
)