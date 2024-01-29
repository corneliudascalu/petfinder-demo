package com.riverpath.petfinder.features

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.unit.sp
import com.riverpath.petfinder.pets.Animal

interface PetDetails {
    suspend fun getPetDetails(id: String): Animal? = null

    @Composable
    fun PetDetailsUI(petID: String) {
        Placeholder(48.sp, 48.sp, PlaceholderVerticalAlign.Center)
    }
}

internal val EmptyPetDetails = object : PetDetails {}