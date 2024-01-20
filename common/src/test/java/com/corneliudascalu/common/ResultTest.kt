package com.corneliudascalu.common

import com.corneliudascalu.common.Result
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ResultTest{

    @Test
    fun `toString test`(){
        val sample = Result.Success<Boolean>(true)
        assertThat(sample.toString()).isEqualTo("Success(data=true)")
    }
}