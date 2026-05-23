package com.example.travelagency.model.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.travelagency.model.local.entity.TeamMemberEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamMemberDao {

    // ── Reads ────────────────────────────────────────────────────────────────

    @Query("SELECT * FROM team_members ORDER BY name ASC")
    fun getAllTeamMembers(): Flow<List<TeamMemberEntity>>

    @Query("SELECT * FROM team_members WHERE id = :id")
    suspend fun getTeamMemberById(id: Long): TeamMemberEntity?

    // ── Writes ───────────────────────────────────────────────────────────────

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamMember(member: TeamMemberEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeamMembers(members: List<TeamMemberEntity>)

    @Update
    suspend fun updateTeamMember(member: TeamMemberEntity)

    @Delete
    suspend fun deleteTeamMember(member: TeamMemberEntity)

    @Query("SELECT COUNT(*) FROM team_members")
    suspend fun getTeamMemberCount(): Int
}