package com.riverpath.petfinderdemo.ui

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.riverpath.petfinder.features.Features
import com.riverpath.petfinder.features.PetSearch
import com.riverpath.petfinder.pets.Animal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import java.lang.Exception

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val fakePetFinder = FakePetFinder()
    private val testAnimal = Animal(
        id = "1",
        name = "Spot",
        description = "A test dog",
        type = "dog",
        color = "black",
        age = "5",
        gender = "male",
        breed = "mixed",
        size = "small",
        thumbnailURL = "",
        portraitURL = "",
        status = "taken",
    )
    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        Features.petSearch = fakePetFinder
        viewModel = MainViewModel()
    }

    @Test
    fun `when a pet is found, the same pet is emitted`() = runTest {

        val initialState = SearchUiState.Searching()
        val expectedSearchingState = SearchUiState.Searching("test", listOf(testAnimal))
        val expectedLoadingState = SearchUiState.Loading("test", emptyList())

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(initialState)
            fakePetFinder.nextSearchResult.add(testAnimal)
            viewModel.search("test")
            assertThat(awaitItem()).isEqualTo(expectedSearchingState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `test failure case`() = runTest {
        fakePetFinder.nextError = Exception("test error")

        viewModel.uiState.test {
            assertThat(awaitItem()).isEqualTo(SearchUiState.Searching())
            viewModel.search("test")
            assertThat(awaitItem()).isEqualTo(SearchUiState.Searching(searchTerm = "test"))
        }

        viewModel.error.test {
            fakePetFinder.nextError = Exception("test error")

            viewModel.search("test")
            assertThat(awaitItem()).isEqualTo("test error")
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        fakePetFinder.reset()
    }
}