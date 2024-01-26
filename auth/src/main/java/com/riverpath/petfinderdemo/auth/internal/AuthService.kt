package com.riverpath.petfinderdemo.auth.internal

import com.squareup.moshi.Json
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

internal interface AuthService {
    @FormUrlEncoded
    @POST("/v2/oauth2/token")
    suspend fun getToken(
        @Field("grant_type") grant_type: String = "client_credentials",
        @Field("client_id") clientID: String,
        @Field("client_secret") clientSecret: String,
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