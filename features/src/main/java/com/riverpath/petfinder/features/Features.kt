package com.riverpath.petfinder.features

/**
 * A ServiceLocator singleton used to hold references to Features.
 * A Feature is a major piece of business logic, usually implemented in its own library module, and
 * it's defined by an interface in the `:features` module.
 */
object Features {
    private var _httpClientProvider: HttpClientProvider = EmptyHttpClientProvider
    private var _petSearch: PetSearch = EmptyPetSearch
    private var _detailsTest: String = ""

    /**
     * The provider of the OkHttp client used throughout the application. It's assumed this client
     * will handle tasks like authentication, caching and graceful degradation of service
     */
    var httpClientProvider: HttpClientProvider
        get() = _httpClientProvider
        set(value) {
            _httpClientProvider = value
        }

    /**
     * The feature that lets users search for a pet. Its interface should declare all the
     * capabilities offered by the app in terms of pet search.
     */
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

