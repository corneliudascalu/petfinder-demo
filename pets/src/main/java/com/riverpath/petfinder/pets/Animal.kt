package com.riverpath.petfinder.pets

data class Animal(
    val id: String,
    val name: String,
    val description: String,
    val type: String,
    val color: String,
    val age: String,
    val gender: String,
    val breed: String,
    val size: String,
    val thumbnailURL: String,
    val portraitURL: String,
    val status: String,
    val distance: Int? = null,
)
