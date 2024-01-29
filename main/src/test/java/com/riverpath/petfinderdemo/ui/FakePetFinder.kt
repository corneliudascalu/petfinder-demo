package com.riverpath.petfinderdemo.ui

import com.riverpath.petfinder.features.PetSearch
import com.riverpath.petfinder.pets.Animal
import kotlin.Exception

class FakePetFinder : PetSearch {

    var nextSearchResult = mutableListOf<Animal>()
    var nextError: Exception? = null
    override suspend fun getAnimals(type: String): Result<List<Animal>> {
        return if (nextError == null) {
            Result.success(nextSearchResult.toList()).also { nextSearchResult.clear() }
        } else {
            Result.failure<List<Animal>>(nextError!!).also { nextError = null }
        }
    }

    fun reset() {
        nextError = null
        nextSearchResult.clear()
    }
}