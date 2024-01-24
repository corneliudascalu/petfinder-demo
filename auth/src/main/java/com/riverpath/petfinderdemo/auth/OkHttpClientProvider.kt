package com.riverpath.petfinderdemo.auth

import com.riverpath.petfinderdemo.auth.internal.AccessTokenProvider
import com.riverpath.petfinderdemo.auth.internal.PetfinderAuthenticator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object OkHttpClientProvider {

    val client: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .authenticator(PetfinderAuthenticator(AccessTokenProvider()))
            .addInterceptor(logging)
            .build()
    }
}