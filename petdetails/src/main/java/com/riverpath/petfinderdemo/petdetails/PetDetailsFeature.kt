package com.riverpath.petfinderdemo.petdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.riverpath.petfinder.features.PetDetails
import com.riverpath.petfinder.pets.Animal

@OptIn(ExperimentalMaterial3Api::class)
class PetDetailsFeature : PetDetails {
    override suspend fun getPetDetails(id: String): Animal? {
        return super.getPetDetails(id)
    }

    @Composable
    override fun PetDetailsUI(petID: String) {
        Scaffold(topBar = { CenterAlignedTopAppBar(title = { Text("Pet Details") }) }) {
            Column(modifier = Modifier.padding(it)) {
                Text("Pet Details $petID")
            }
        }
    }
}