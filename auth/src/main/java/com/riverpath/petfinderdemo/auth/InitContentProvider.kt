package com.riverpath.petfinderdemo.auth

import com.riverpath.petfinder.features.ServiceLocator
import com.riverpath.petfinderdemo.common.StartupInitializer

class InitContentProvider : StartupInitializer() {
    override fun initialize() {
        ServiceLocator.httpClientProvider = OkHttpClientProvider
    }
}