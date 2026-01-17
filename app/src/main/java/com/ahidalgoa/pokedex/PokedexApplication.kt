package com.ahidalgoa.pokedex

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class required for Hilt.
 * The @HiltAndroidApp annotation triggers Hilt's code generation, including a base class
 * that serves as the application-level dependency container.
 */
@HiltAndroidApp
class PokedexApplication : Application()
