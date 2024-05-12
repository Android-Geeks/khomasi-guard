package com.company.khomasiguard

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application(){
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}