package io.austinray.slauncher.util

import android.view.View

/**
 * Determine SystemUI flags for the respective [hideNav] and [hideStatus] conditions.
 */
fun determineUiVisibility(hideNav: Boolean, hideStatus: Boolean): Int {
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
