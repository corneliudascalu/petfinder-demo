package com.corneliudascalu.petsearch

import com.riverpath.petfinder.features.Features
import com.riverpath.petfinderdemo.common.Constants
import com.riverpath.petfinderdemo.common.StartupInitializer
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class Initializer : StartupInitializer() {
    override fun initialize() {
        Features.petSearch = PetFinder(
            api = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(Features.httpClientProvider.httpClient)
                .addConverterFactory(
                    MoshiConverterFactory.create(
                        Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
                    )
                )
                .build().create(PetSearchAPI::class.java)
        )
    }
}
