package com.riverpath.petfinder.features

import okhttp3.OkHttpClient

interface HttpClientProvider {
    val httpClient: OkHttpClient
}

internal val EMPTY = object : HttpClientProvider {
    override val httpClient: OkHttpClient
        get() = OkHttpClient()
}
