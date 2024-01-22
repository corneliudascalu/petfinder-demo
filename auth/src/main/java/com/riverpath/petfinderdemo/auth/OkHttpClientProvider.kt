package com.riverpath.petfinderdemo.auth

import okhttp3.OkHttpClient

class OkHttpClientProvider {
    fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .authenticator(PetfinderAuthenticator())
            .build()
    }
}