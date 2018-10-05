package io.austinray.slauncher.prefs

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener

/**
 * Subscribes to all SharedPreference updates and ensures values are updated.
 */
class PreferencesUpdater : OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(sharedPrefs: SharedPreferences?, key: String?) {
        if (sharedPrefs == null || key == null) return
        Prefs.load(sharedPrefs)
    }
}
