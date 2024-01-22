package com.riverpath.petfinderdemo.auth

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class PetfinderAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        return null
    }
}

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }

}