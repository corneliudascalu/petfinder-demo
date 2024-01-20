package com.riverpath.petfinderdemo.common

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class ResultTest {

    @Test
    fun `toString test`() {
        val sample = Result.Success(true)
        assertThat(sample.toString()).isEqualTo("Success(data=true)")
    }

    @Test
    fun `mocking test`() {
        val mock = mockk<Result<String>>()

        every { mock.toString() } returns "fake string"

        assertThat(mock.toString()).isEqualTo("fake string")
    }
}