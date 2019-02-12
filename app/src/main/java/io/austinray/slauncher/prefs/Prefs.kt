package io.austinray.slauncher.prefs

import android.content.SharedPreferences
import android.content.res.Resources
import io.austinray.slauncher.R

/**
 * Single truth for Preference information in code.
 *
 * Object handles key (loaded from values/prefs.xml) and value management.
 * Any class that
 */
object Prefs {
    // Maps Key-Value pairs. This simplifies logic in PreferenceUpdater.
    val prefs: MutableMap<String, Any> = HashMap()

    // Resource instance is needed to load the keys.
    private var res: Resources = Resources.getSystem()

    // Preference Keys loaded from values/prefs.xml
    object Keys {
        val HIDE_NAV: String = res.getString(R.string.pref_key_hide_nav)
        val HIDE_STATUS: String = res.getString(R.string.pref_key_hide_status)
        val ICON_PACK: String = res.getString(R.string.pref_key_icon_pack)
        val NAV_BAR_COLOR: String = res.getString(R.string.pref_key_nav_bar_color)
        val STATUS_BAR_COLOR: String = res.getString(R.string.pref_key_status_bar_color)
    }

    /**
     * Load all SharedPreferences and load non-null values in the HashMap.
     */
    fun load(sharedPrefs: SharedPreferences) = sharedPrefs.all.filter { it.value != null }
        .forEach { prefs[it.key] = it.value as Any }

    /**
     * A resource is required to load keys from values/prefs.xml, but we do not want anyone
     * to access that resource object. Unfortunately, Kotlin does not offer away to have public
     * setter, but private getter.
     */
    fun setResource(res: Resources) {
        this.res = res
    }

    inline operator fun <reified T : Any> get(key: String): T {
        return when (T::class) {
            Boolean::class -> prefs[key] as T
            String::class -> prefs[key] as T
            Int::class -> prefs[key] as T
            else -> throw UnsupportedOperationException("Preference type not implemented yet.")
        }
    }
}
