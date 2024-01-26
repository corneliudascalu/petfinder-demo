package com.corneliudascalu.petsearch

import com.riverpath.petfinder.features.PetSearch
import com.riverpath.petfinder.pets.Animal
import retrofit2.HttpException

internal class PetFinder(private val api: PetSearchAPI) : PetSearch {
    override suspend fun getAllAnimals(): Result<List<Animal>> {
        return try {
            Result.success(
                api.getAllAnimals().animals?.map(animalMapper) ?: emptyList()
            )
        } catch (e: HttpException) {
            Result.failure(PetFindException.ServerException(e.code(), e.message(), e))
        }
    }

    override suspend fun getAnimals(type: String): Result<List<Animal>> {
        return try {
            Result.success(
                api.getAnimalsByType(type).animals?.map(animalMapper) ?: emptyList()
            )
        } catch (e: HttpException) {

            val errorBody = e.response()?.errorBody()?.string()
            // TODO Parse errorBody json into an intelligible error type
            Result.failure(PetFindException.ServerException(e.code(), errorBody ?: e.message(), e))
        }
    }

    private val animalMapper: (AnimalDto) -> Animal = {
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
            thumbnailURL = getFirstValidPhotoURL(it.photos),
            portraitURL = getFirstValidFullPhotoURL(it.photos),
            status = it.status,
            distance = it.distance?.toInt()
        )
    }

    private fun getFirstValidPhotoURL(photos: List<Map<String, String>>): String {
        for (photo in photos) {
            photo["small"]?.also { return it }
            photo["medium"]?.also { return it }
            photo["large"]?.also { return it }
            photo["full"]?.also { return it }
        }
        return ""
    }

    private fun getFirstValidFullPhotoURL(photos: List<Map<String, String>>): String {
        for (photo in photos) {
            photo["full"]?.also { return it }
        }
        return ""
    }
}

sealed class PetFindException(
    override val message: String? = null,
    override val cause: Throwable? = null,
) : Exception() {
    class ServerException(
        val code: Int,
        override val message: String? = null,
        override val cause: Throwable? = null
    ) : PetFindException()
}