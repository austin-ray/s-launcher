package io.austinray.slauncher.util

import android.content.SharedPreferences
import io.austinray.slauncher.prefs.HIDE_NAV
import io.austinray.slauncher.prefs.HIDE_STATUS
import io.austinray.slauncher.prefs.KEY_HIDE_NAV
import io.austinray.slauncher.prefs.KEY_HIDE_STAT

val prefsMap: MutableMap<String, Any> = mutableMapOf(
    Pair(KEY_HIDE_NAV, HIDE_NAV),
    Pair(KEY_HIDE_STAT, HIDE_STATUS)
)

/**
 * Load and set the apps shared preferences
 */
fun loadPreferences(prefs: SharedPreferences) {
    HIDE_NAV = prefs.getBoolean(KEY_HIDE_NAV, false)
    HIDE_STATUS = prefs.getBoolean(KEY_HIDE_STAT, false)
}
