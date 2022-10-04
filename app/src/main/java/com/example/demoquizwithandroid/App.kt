package com.example.demoquizwithandroid

import android.app.Application
import android.content.res.Resources

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Companion.resources = resources
    }

    companion object {
        private var resources: Resources? = null
        val appResources: Resources?
            get() = resources
    }
}
