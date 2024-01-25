package com.riverpath.petfinderdemo.petdetails

import com.riverpath.petfinder.features.ServiceLocator
import com.riverpath.petfinderdemo.common.StartupInitializer

class InitPetDetails : StartupInitializer() {
    override fun initialize() {
        ServiceLocator.detailsTest = "Hello World!"
    }
}