package com.corneliudascalu.petsearch

import retrofit2.http.GET
import retrofit2.http.Query

internal interface PetSearchAPI {
    @GET("/v2/animals")
    suspend fun getAllAnimals(): AnimalsDto

    @GET("/v2/animals")
    suspend fun getAnimalsByType(@Query("type") type: String): AnimalsDto
}