package io.austinray.slauncher.util

import android.content.SharedPreferences
import io.austinray.slauncher.prefs.HIDE_NAV
import io.austinray.slauncher.prefs.HIDE_STATUS
import io.austinray.slauncher.prefs.ICON_PACK
import io.austinray.slauncher.prefs.KEY_HIDE_NAV
import io.austinray.slauncher.prefs.KEY_HIDE_STAT
import io.austinray.slauncher.prefs.KEY_ICON_PACK

val prefsMap: MutableMap<String, Any> = mutableMapOf(
    Pair(KEY_HIDE_NAV, HIDE_NAV),
    Pair(KEY_HIDE_STAT, HIDE_STATUS),
    Pair(KEY_ICON_PACK, ICON_PACK)
)

/**
 * Load and set the apps shared preferences
 */
fun loadPreferences(prefs: SharedPreferences) {
    HIDE_NAV = prefs.getBoolean(KEY_HIDE_NAV, false)
    HIDE_STATUS = prefs.getBoolean(KEY_HIDE_STAT, false)
    ICON_PACK = prefs.getString(KEY_ICON_PACK, "default")
}
