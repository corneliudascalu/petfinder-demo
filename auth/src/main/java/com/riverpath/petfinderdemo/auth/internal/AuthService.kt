package com.riverpath.petfinderdemo.auth.internal

import com.squareup.moshi.Json
import retrofit2.http.GET
import retrofit2.http.Query

internal interface AuthService {
    @GET("/oauth2/token?grant_type=client_credentials")
    suspend fun getToken(
        @Query("client_id") clientID: String,
        @Query("client_secret") clientSecret: String,
    ): Token
}

/*
{
  "token_type": "Bearer",
  "expires_in": 3600,
  "access_token": "..."
}
 */
internal data class Token(
    @Json(name = "token_type")
    val tokenType: String,
    @Json(name = "expires_in")
    val expiresIn: Long,
    @Json(name = "access_token")
    val accessToken: String,
)