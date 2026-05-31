package com.example.travelagency.model.network

import com.example.travelagency.model.network.dto.DestinationDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TravelApiService {

    @GET("destinations")
    suspend fun getDestinations(): List<DestinationDto>

    @GET("destinations/{id}")
    suspend fun getDestinationById(@Path("id") id: String): DestinationDto

    @POST("destinations")
    suspend fun createDestination(@Body destination: DestinationDto): DestinationDto

    @PUT("destinations/{id}")
    suspend fun updateDestination(
        @Path("id") id: String,
        @Body destination: DestinationDto
    ): DestinationDto

    @DELETE("destinations/{id}")
    suspend fun deleteDestination(@Path("id") id: String): DestinationDto
}