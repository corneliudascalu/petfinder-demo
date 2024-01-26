package com.riverpath.petfinder.features

import androidx.activity.ComponentActivity

interface PetSearchFeature {
    fun show(activity:ComponentActivity)
}

internal val EmptyPetSearchScreen = object : PetSearchFeature {
    override fun show(activity: ComponentActivity) {

    }
}