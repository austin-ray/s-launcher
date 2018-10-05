package io.austinray.slauncher.util

import android.view.View
import io.austinray.slauncher.prefs.Prefs
import io.austinray.slauncher.prefs.Prefs.Keys.HIDE_NAV
import io.austinray.slauncher.prefs.Prefs.Keys.HIDE_STATUS

/**
 * Determine SystemUI flags for the respective [hideNav] and [hideStatus] conditions.
 */
fun determineUiVisibility(hideNav: Boolean = Prefs[HIDE_NAV], hideStatus: Boolean = Prefs[HIDE_STATUS]): Int {
    val hideNavFlags = when {
        hideNav -> View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        else -> 0
    }

    val hideStatusFlags = when {
        hideStatus -> View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_FULLSCREEN
        else -> 0
    }

    return hideStatusFlags or hideNavFlags
}
