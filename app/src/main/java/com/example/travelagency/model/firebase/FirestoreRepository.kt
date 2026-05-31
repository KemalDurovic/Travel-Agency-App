package com.example.travelagency.model.firebase

import com.example.travelagency.model.network.dto.DestinationDto
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class FirestoreRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val destinationsCollection = firestore.collection("destinations")

    // ── Realtime updates using Flow ──────────────────────────────────────────
    fun getDestinationsRealtime(): Flow<List<DestinationDto>> = callbackFlow {
        val listener = destinationsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }
            val destinations = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(DestinationDto::class.java)?.copy(id = doc.id)
            } ?: emptyList()
            trySend(destinations)
        }
        awaitClose { listener.remove() }
    }

    suspend fun addDestination(destination: DestinationDto): Result<String> {
        return try {
            val doc = destinationsCollection.add(destination).await()
            Result.success(doc.id)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateDestination(id: String, destination: DestinationDto): Result<Unit> {
        return try {
            destinationsCollection.document(id).set(destination).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteDestination(id: String): Result<Unit> {
        return try {
            destinationsCollection.document(id).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
