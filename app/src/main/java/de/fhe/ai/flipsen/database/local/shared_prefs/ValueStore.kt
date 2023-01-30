package de.fhe.ai.flipsen.database.local.shared_prefs

import android.content.Context
import android.preference.PreferenceManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ValueStore @Inject constructor(@ApplicationContext context : Context){
    val prefs = context.getSharedPreferences("flipsen_store", Context.MODE_PRIVATE)
    val PREF_TAG = "flipsen_store"

    fun getValue(Key : String): Long {
        return prefs.getLong(Key, 0)
    }
    fun setValue(Key: String, Value : Long) {
        prefs.edit().putLong(Key, Value).apply()
    }
}
