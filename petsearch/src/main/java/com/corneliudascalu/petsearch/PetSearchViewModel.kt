package com.corneliudascalu.petsearch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riverpath.petfinder.features.Features
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(FlowPreview::class)
class PetSearchViewModel : ViewModel() {
    private val ui = MutableStateFlow<SearchUiState>(SearchUiState.Searching())

    // The list of pet types can be prefetched and cached in memory or on local storage
    private val knownPetTypes = listOf("dog", "cat", "rabbit", "bird")

    val uiState = ui.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SearchUiState.Searching(),
    )

    private val _error = MutableSharedFlow<String>()
    val error = _error
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 0
        )

    init {
        viewModelScope.launch {
            ui.emit(SearchUiState.Loading())
            Features.petSearch.getAllAnimals()
                .onSuccess { animals -> ui.emit(SearchUiState.Searching(results = animals)) }
                .onFailure { _error.emit(it.message ?: "Unknown error") }

            ui.debounce(500)
                .filterIsInstance<SearchUiState.Searching>()
                .filter {
                    val contains = knownPetTypes.contains(it.searchTerm)
                    if (!contains) {
                        Timber.d("${it.searchTerm} is not a known pet type")
                    } else {
                        Timber.d("${it.searchTerm} is a known pet type")
                    }
                    contains
                }
                // because states can have different types (Searching vs Loading)
                .distinctUntilChanged { old, new -> old.searchTerm == new.searchTerm }
                .collect { state ->
                    Timber.d("Searching for ${state.searchTerm}s")
                    findPetByType(state.searchTerm!!)
                }
        }
    }

    fun onSearchTermChanged(term: String) {
        if (term != ui.value.searchTerm) {
            viewModelScope.launch {
                ui.emit(
                    SearchUiState.Searching(
                        searchTerm = term.trim(),
                        results = ui.value.currentResults
                    )
                )
            }
        }
    }

    fun search(term: String) {
        viewModelScope.launch {
            findPetByType(term)
        }
    }

    fun clear() {
        viewModelScope.launch {
            ui.emit(
                SearchUiState.Searching(
                    searchTerm = null,
                    results = ui.value.currentResults
                )
            )
        }
    }

    private suspend fun findPetByType(term: String) {
        ui.emit(SearchUiState.Loading(searchTerm = term, lastResults = ui.value.currentResults))
        Features.petSearch.getAnimals(type = term)
            .onSuccess { animals ->
                ui.emit(
                    SearchUiState.Searching(
                        searchTerm = term,
                        results = animals
                    )
                )
            }
            .onFailure {
                ui.emit(
                    SearchUiState.Searching(
                        searchTerm = term,
                        results = ui.value.currentResults
                    )
                )
                _error.emit(it.message ?: "Unknown error")
            }
    }
}