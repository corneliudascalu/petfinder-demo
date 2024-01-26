package com.riverpath.petfinderdemo.petdetails

import com.riverpath.petfinder.features.Features
import com.riverpath.petfinderdemo.common.StartupInitializer

class InitPetDetails : StartupInitializer() {
    override fun initialize() {
        Features.detailsTest = "Hello World!"
    }
}