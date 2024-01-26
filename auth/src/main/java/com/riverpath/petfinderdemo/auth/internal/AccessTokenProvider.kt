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

/**
 * Takes care of requesting an authentication token, storing it (in memory) and requesting a new one
 * when it expires.
 */
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

    /**
     * Get the cached token or a new one if the cached token is expired
     */
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
            val (clientId, clientSecret) = retrieveSecrets()

            val token = api.getToken(clientID = clientId, clientSecret = clientSecret)
            Timber.d("Received new token $token")

            // save the token in memory with an expiration date
            cachedToken = FleetingToken(
                token = token.accessToken,
                expirationDate = LocalDateTime.now().plusSeconds(token.expiresIn)
            )
            return token.accessToken
        } catch (e: HttpException) {
            // TODO In a real app we would handle different HTTP error codes differently
            // There is little chance the app could recover from a rejected authentication request,
            // but providing detailed logs could be useful in debugging the issue
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

    /**
     * Retrieve API secrets from secure storage
     */
    private fun retrieveSecrets(): Pair<String, String> {
        val secrets = Secrets()
        // Ideally we should get this from PackageManager
        val packageName = "com.riverpath.petfinderdemo.auth"
        val clientId = secrets.getclient_id(packageName)
        val clientSecret = secrets.getclient_secret(packageName)
        Timber.d("API credentials: clientID=${clientId}, clientSecret=$clientSecret")
        return Pair(clientId, clientSecret)
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