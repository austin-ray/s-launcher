package io.austinray.slauncher.prefs

import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import io.austinray.slauncher.util.prefsMap

/**
 * Subscribes to all SharedPreference updates and ensures values are updated.
 */
class PreferencesUpdater : OnSharedPreferenceChangeListener {
    override fun onSharedPreferenceChanged(prefs: SharedPreferences?, key: String?) {
        if (prefs == null || key == null || prefsMap[key] == null) return

        // Ensure proper data type.
        when (prefsMap[key]) {
            is Boolean -> prefsMap[key] = prefs.getBoolean(key, false)
            is String -> prefsMap[key] = prefs.getString(key, "") as Any
        }
    }
}
