package com.riverpath.petfinder.features

object ServiceLocator {
    private var _httpClientProvider: HttpClientProvider = EmptyHttpClientProvider
    private var _petSearch: PetSearch = EmptyPetSearch
    private var _detailsTest: String = ""
    var httpClientProvider: HttpClientProvider
        get() = _httpClientProvider
        set(value) {
            _httpClientProvider = value
        }

    var petSearch: PetSearch
        get() = _petSearch
        set(value) {
            _petSearch = value
        }

    var detailsTest: String
        get() = _detailsTest
        set(value) {
            _detailsTest = value
        }
}

