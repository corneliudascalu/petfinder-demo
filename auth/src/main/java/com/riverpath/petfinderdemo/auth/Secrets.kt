package com.riverpath.petfinderdemo.auth

class Secrets {

    // Method calls will be added by gradle task hideSecret
    // Example : external fun getWellHiddenSecret(packageName: String): String

    companion object {
        init {
            System.loadLibrary("secrets")
        }
    }

    external fun getclient_secret(packageName: String): String

    external fun getclient_id(packageName: String): String
}