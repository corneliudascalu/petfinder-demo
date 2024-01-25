package com.riverpath.petfinderdemo.ui

data class SearchUiState(
    val searchTerm: String? = null,
    val results: List<String> = emptyList(),
)
