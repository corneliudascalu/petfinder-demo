package com.riverpath.petfinderdemo.ui

import android.app.Application
import com.riverpath.petfinderdemo.BuildConfig
import timber.log.Timber

class PetfinderApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            // Here we would configure Timber for logging in production to a remote service
        }
    }
}