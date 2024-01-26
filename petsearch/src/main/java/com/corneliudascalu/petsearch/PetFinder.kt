package com.corneliudascalu.petsearch

import com.riverpath.petfinder.features.PetSearch
import com.riverpath.petfinder.pets.Animal
import retrofit2.HttpException

internal class PetFinder(private val api: PetSearchAPI) : PetSearch {
    override suspend fun getAllAnimals(): Result<List<Animal>> {
        try {
            return Result.success(api.getAllAnimals().animals?.map {
                Animal(
                    id = it.id,
                    name = it.name,
                    description = it.description ?: "No Description",
                    type = it.type,
                    color = it.colors["primary"] ?: "transparent",
                    age = it.age,
                    gender = it.gender,
                    breed = it.breeds.primary,
                    size = it.size,
                    thumbnailURL = it.photos.firstOrNull()?.get("small") ?: "",
                    portraitURL = it.photos.firstOrNull()?.get("full") ?: "",
                    status = it.status,
                    distance = it.distance?.toInt()
                )
            } ?: emptyList())
        } catch (e: HttpException) {
            return Result.failure(PetFindException.ServerException(e.code(), e))
        }
    }

    override suspend fun getAnimals(type: String): Result<List<Animal>> {
        return Result.success(emptyList())
    }
}

sealed class PetFindException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception() {
    class ServerException(val code: Int, e: Exception? = null) :
        PetFindException(message = e?.message, cause = e)
}