package com.riverpath.petfinderdemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.riverpath.petfinder.features.Features

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Features.petSearchScreen.show(this)
    }
}
