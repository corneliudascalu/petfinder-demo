package com.corneliudascalu.petsearch

import com.riverpath.petfinder.pets.Animal

sealed class SearchUiState(open val searchTerm: String? = null) {
    data class Loading(
        override val searchTerm: String? = null,
        val lastResults: List<Animal> = emptyList(),
    ) : SearchUiState()

    data class Searching(
        override val searchTerm: String? = null,
        val results: List<Animal> = emptyList(),
    ) : SearchUiState()

    val currentResults: List<Animal>
        get() {
            return when (this) {
                is Loading -> this.lastResults
                is Searching -> this.results
            }
        }
}