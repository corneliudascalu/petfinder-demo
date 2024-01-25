package com.riverpath.petfinderdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riverpath.petfinder.features.ServiceLocator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val ui = MutableStateFlow(SearchUiState())

    val uiState = ui.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = SearchUiState(),
    )

    fun search(term: String) {
        viewModelScope.launch {
            ui.emit(ui.value.copy(searchTerm = term))
        }
    }

    fun testAuth() {
        viewModelScope.launch {
            ServiceLocator.petSearch.getAllAnimals()
                .onSuccess { animals -> ui.emit(ui.value.copy(results = animals.map { it.description })) }
                .onFailure { ui.emit(ui.value.copy(searchTerm = it.message)) }

        }
    }
}