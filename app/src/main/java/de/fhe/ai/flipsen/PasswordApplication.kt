package de.fhe.ai.flipsen

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import androidx.appcompat.app.AppCompatDelegate

@HiltAndroidApp
class PasswordApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}