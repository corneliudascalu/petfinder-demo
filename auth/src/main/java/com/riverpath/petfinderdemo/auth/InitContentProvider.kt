package com.riverpath.petfinderdemo.auth

import com.riverpath.petfinder.features.Features
import com.riverpath.petfinderdemo.common.StartupInitializer

class InitContentProvider : StartupInitializer() {
    override fun initialize() {
        Features.httpClientProvider =
            OkHttpClientProvider.also { it.appContext = context?.applicationContext!! }
    }
}