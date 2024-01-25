package com.corneliudascalu.petsearch

import retrofit2.http.GET
import retrofit2.http.Query

internal interface PetSearchAPI {
    @GET("animals")
    suspend fun getAllAnimals(): AnimalsDto

    @GET("animals")
    suspend fun getAnimalsByType(@Query("type") type: String): AnimalsDto
}