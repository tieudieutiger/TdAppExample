package com.tieudieu.appexample

import android.app.Application

class ExampleApp: Application() {

    companion object {

        @get: Synchronized lateinit var instance: ExampleApp
            private set

    }

    override fun onCreate() {
        super.onCreate()

        instance = this
    }

}