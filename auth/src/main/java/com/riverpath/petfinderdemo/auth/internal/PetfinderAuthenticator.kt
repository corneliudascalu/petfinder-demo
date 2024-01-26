package com.riverpath.petfinderdemo.auth.internal

import android.util.Log
import com.riverpath.petfinderdemo.auth.internal.AccessTokenProvider
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber

internal class PetfinderAuthenticator(
    private val tokenProvider: AccessTokenProvider,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        try {
            val token = runBlocking { tokenProvider.getToken() }
        } catch (e: Exception) {
            Timber.e(e, "Failed to provide authentication token")
            return null
        }
        return null
    }
}

internal class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}