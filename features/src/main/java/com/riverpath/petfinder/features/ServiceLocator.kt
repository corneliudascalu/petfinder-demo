package com.riverpath.petfinder.features

object ServiceLocator {
    private var _httpClientProvider: HttpClientProvider = EMPTY
    private var _detailsTest: String = ""
    var httpClientProvider: HttpClientProvider
        get() = _httpClientProvider
        set(value) {
            _httpClientProvider = value
        }

    var detailsTest: String
        get() = _detailsTest
        set(value) {
            _detailsTest = value
        }
}

