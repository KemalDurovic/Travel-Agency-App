package com.example.travelagency.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.travelagency.model.local.entity.DestinationEntity
import com.example.travelagency.model.local.entity.DestinationIncludeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DestinationDao {

    // ── Reads ────────────────────────────────────────────────────────────────

    @Transaction
    @Query("SELECT * FROM destinations ORDER BY name ASC")
    fun getAllDestinationsWithIncludes(): Flow<List<DestinationWithIncludes>>

    @Transaction
    @Query("SELECT * FROM destinations WHERE id = :id")
    suspend fun getDestinationWithIncludes(id: Long): DestinationWithIncludes?

    @Query("SELECT * FROM destinations WHERE category = :category ORDER BY name ASC")
    fun getDestinationsByCategory(category: String): Flow<List<DestinationEntity>>

    // ── Writes ───────────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestination(destination: DestinationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDestinations(destinations: List<DestinationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIncludes(includes: List<DestinationIncludeEntity>)

    @Update
    suspend fun updateDestination(destination: DestinationEntity)

    @Delete
    suspend fun deleteDestination(destination: DestinationEntity)

    @Query("DELETE FROM destinations")
    suspend fun deleteAllDestinations()

    @Query("SELECT COUNT(*) FROM destinations")
    suspend fun getDestinationCount(): Int
}