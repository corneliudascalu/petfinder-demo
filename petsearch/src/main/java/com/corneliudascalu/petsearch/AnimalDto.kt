package com.corneliudascalu.petsearch

internal data class AnimalsDto(val animals: List<AnimalDto>?)
internal data class AnimalDto(
    val id: String,
    val type: String,
    val species: String,
    val breeds: Breed,
    val colors: Map<String, String>,
    val age: String,
    val gender: String,
    val size: String,
    val coat: String,
    val name: String,
    val description: String,
    val photos: Map<String, String>,
    val status: String,
    val distance: Double,
)

internal data class Breed(
    val primary: String, val secondary: String?,
    val mixed: Boolean?, val unknown: Boolean?,
)