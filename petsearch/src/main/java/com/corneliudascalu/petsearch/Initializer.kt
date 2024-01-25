package com.corneliudascalu.petsearch

import com.riverpath.petfinder.features.PetSearch
import com.riverpath.petfinder.features.ServiceLocator
import com.riverpath.petfinderdemo.common.Constants
import com.riverpath.petfinderdemo.common.StartupInitializer
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class Initializer : StartupInitializer() {
    override fun initialize() {
        ServiceLocator.petSearch = PetFinder(
            api = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(ServiceLocator.httpClientProvider.httpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(PetSearchAPI::class.java)
        )
    }
}
