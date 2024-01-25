package com.riverpath.petfinder.features

import com.riverpath.petfinder.pets.Animal

interface PetSearch {
    suspend fun getAllAnimals(): Result<List<Animal>> = Result.success(emptyList())

    suspend fun getAnimals(type: String): Result<List<Animal>> = Result.success(emptyList())
}

internal val EmptyPetSearch = object : PetSearch {}