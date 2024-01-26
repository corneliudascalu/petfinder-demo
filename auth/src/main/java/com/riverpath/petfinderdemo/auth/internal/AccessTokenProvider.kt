package com.riverpath.petfinderdemo.auth.internal

import com.riverpath.petfinderdemo.auth.Secrets
import com.riverpath.petfinderdemo.common.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.io.IOException
import java.time.LocalDateTime

internal class AccessTokenProvider(
    private val api: AuthService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(
            MoshiConverterFactory.create(
                Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            )
        )
        .client(
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor()
                    .also { it.setLevel(HttpLoggingInterceptor.Level.BODY) })
                .build()
        )
        .build()
        .create(AuthService::class.java)
) {
    // Cached in memory, but it could just as well be saved in Shared Preferences
    private var cachedToken: FleetingToken? = null
    suspend fun getToken(): String {
        cachedToken?.also {
            return if (it.isExpired) {
                refreshToken()
            } else {
                it.token
            }
        }
        return refreshToken();
    }

    private suspend fun refreshToken(): String {
        try {
            val secrets = Secrets()
            // Ideally we should get this from PackageManager
            val packageName = "com.riverpath.petfinderdemo.auth"
            val clientId = secrets.getclient_id(packageName)
            val clientSecret = secrets.getclient_secret(packageName)
            Timber.d("API credentials: clientID=${clientId}, clientSecret=$clientSecret")

            val token = api.getToken(clientID = clientId, clientSecret = clientSecret)
            Timber.d("Received new token $token")

            cachedToken = FleetingToken(
                token = token.accessToken,
                expirationDate = LocalDateTime.now().plusSeconds(token.expiresIn)
            )
            return token.accessToken
        } catch (e: HttpException) {
            // TODO In a real app we would handle different HTTP error codes differently
            Timber.e(e, "Failed to get token ${e.message}")
            throw AuthException(
                "Failed to refresh access token with code ${e.code()} ${e.message()}",
                e
            )
        } catch (e: IOException) {
            Timber.e(e, "Failed to get token ${e.message}")
            throw ClientException("Failed to refresh access token", e)
        } catch (e: Exception) {
            Timber.e(e, "Failed to get token ${e.message}")
            throw UnexpectedException("Failed to refresh access token", e)
        }
    }
}

/**
 * An access token with an expiration date.
 */
internal data class FleetingToken(val token: String, val expirationDate: LocalDateTime) {
    val isExpired: Boolean
        get() {
            return LocalDateTime.now().isAfter(expirationDate)
        }
}

internal class AuthException(override val message: String?, override val cause: Throwable?) :
    Exception()

internal class ClientException(override val message: String?, override val cause: Throwable?) :
    Exception()

internal class UnexpectedException(override val message: String?, override val cause: Throwable?) :
    Exception()