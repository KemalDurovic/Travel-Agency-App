package com.example.travelagency.model.network

import com.example.travelagency.model.network.dto.DestinationDto

class NetworkRepository(private val api: TravelApiService) {

    suspend fun getDestinations(): Result<List<DestinationDto>> {
        return try {
            Result.success(api.getDestinations())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getDestinationById(id: String): Result<DestinationDto> {
        return try {
            Result.success(api.getDestinationById(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun createDestination(destination: DestinationDto): Result<DestinationDto> {
        return try {
            Result.success(api.createDestination(destination))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateDestination(id: String, destination: DestinationDto): Result<DestinationDto> {
        return try {
            Result.success(api.updateDestination(id, destination))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteDestination(id: String): Result<DestinationDto> {
        return try {
            Result.success(api.deleteDestination(id))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}