package com.riverpath.petfinderdemo.petdetails

import com.riverpath.petfinder.features.PetDetails
import com.riverpath.petfinder.pets.Animal

class PetDetailsFeature : PetDetails {
    override suspend fun getPetDetails(id: String): Animal? {
        return super.getPetDetails(id)
    }
}