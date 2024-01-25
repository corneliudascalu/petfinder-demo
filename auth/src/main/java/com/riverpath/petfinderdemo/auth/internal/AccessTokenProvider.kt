package com.riverpath.petfinderdemo.auth.internal

import com.riverpath.petfinderdemo.common.Constants
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException
import java.time.LocalDateTime

internal class AccessTokenProvider(
    // TODO Provide API base URL from constants?
    private val api: AuthService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
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
            // TODO Provide secrets
            val token = api.getToken("asdasda", "asdasdasd")
            cachedToken = FleetingToken(
                token = token.accessToken,
                expirationDate = LocalDateTime.now().plusSeconds(token.expiresIn)
            )
            return token.accessToken
        } catch (e: HttpException) {
            // TODO In a real app we would handle HTTP error codes differently
            print("Failed to get token ${e.message}")
            throw AuthException(
                "Failed to refresh access token with code ${e.code()} ${e.message()}",
                e
            )
        } catch (e: IOException) {
            print("Failed to get token ${e.message}")
            throw ClientException("Failed to refresh access token", e)
        } catch (e: Exception) {
            print("Failed to get token ${e.message}")
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