package com.corneliudascalu.petsearch

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.riverpath.petfinder.features.PetSearchFeature
import com.riverpath.petfinderdemo.common.theme.PetfinderDemoTheme

class PetSearchUi : PetSearchFeature {

    override fun show(activity: ComponentActivity) {
        activity.setContent {
            PetfinderDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PetfinderUI(viewModel = activity.viewModels<PetSearchViewModel>().value) {
                        // TODO Navigate to details
                    }
                }
            }

        }
    }
}