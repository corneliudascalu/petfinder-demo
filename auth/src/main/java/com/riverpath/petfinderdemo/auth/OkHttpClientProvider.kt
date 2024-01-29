package com.riverpath.petfinderdemo.auth

import android.content.Context
import com.riverpath.petfinder.features.HttpClientProvider
import com.riverpath.petfinderdemo.auth.internal.AccessTokenProvider
import com.riverpath.petfinderdemo.auth.internal.PetfinderAuthenticator
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

internal object OkHttpClientProvider : HttpClientProvider {

    lateinit var appContext: Context

    override val httpClient: OkHttpClient by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        OkHttpClient.Builder()
            .authenticator(PetfinderAuthenticator(AccessTokenProvider()))
            .addInterceptor(logging)
            // TODO Implement smarter caching. In a real app we would want to cache only some
            // requests, not all. And we would want to invalidate the cache in some cases, like
            // a pull-to-refresh action.
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                val cacheControl = CacheControl.Builder()
                    .maxAge(1, TimeUnit.HOURS)
                    .build()

                response.newBuilder()
                    .removeHeader("cache-control")
                    .addHeader("cache-control",cacheControl.toString())
                    .build()
            }
            .cache(
                Cache(
                    directory = appContext.cacheDir,
                    maxSize = 50L * 1024L * 1024L // 50 MiB
                )
            )
            .build()
    }
}