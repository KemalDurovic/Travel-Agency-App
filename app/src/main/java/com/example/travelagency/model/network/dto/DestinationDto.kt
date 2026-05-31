package com.example.travelagency.model.network.dto

import com.google.gson.annotations.SerializedName

data class DestinationDto(
    @SerializedName("id")
    val id: String = "",

    @SerializedName("country")
    val country: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("price")
    val price: Int = 0,

    @SerializedName("description")
    val description: String = "",

    @SerializedName("duration")
    val duration: Int = 0,

    @SerializedName("category")
    val category: String = "",

    @SerializedName("rating")
    val rating: Float = 0f,

    @SerializedName("imageUrl")
    val imageUrl: String = ""
)