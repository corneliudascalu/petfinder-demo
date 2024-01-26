package com.riverpath.petfinder.features

import com.riverpath.petfinder.pets.Animal

interface PetDetails {
    suspend fun getPetDetails(id: String): Animal? = null
}

internal val EmptyPetDetails = object : PetDetails {}