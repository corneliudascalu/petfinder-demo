package com.riverpath.petfinderdemo.auth


import com.google.common.truth.Truth.assertThat
import com.riverpath.petfinderdemo.auth.internal.FleetingToken
import org.junit.Test
import java.time.LocalDateTime

class FleetingTokenTest {

    @Test
    fun `cached token with expiration date in 30 minutes is not expired`() {
        val cachedToken = FleetingToken(
            token = "fake token",
            expirationDate = LocalDateTime.now().plusMinutes(30)
        )

        assertThat(cachedToken.isExpired).isFalse()
    }

    @Test
    fun `cached token with expiration date 5 minutes ago is expired`() {
        val cachedToken = FleetingToken(
            token = "fake token",
            expirationDate = LocalDateTime.now().minusMinutes(5)
        )

        assertThat(cachedToken.isExpired).isTrue()
    }
}