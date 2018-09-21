package io.austinray.slauncher.util

import android.view.View

/**
 * Determine what combination of SystemUI flags to return
 * @param hideNav Hide navigation bar?
 * @param hideStatus Hide status bar?
 * @return SystemUI flag for correct visibility
 */
fun determineUiVisibility(hideNav: Boolean, hideStatus: Boolean): Int {
    val hideNavFlags = if (hideNav) {
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    } else {
        0
    }

    val hideStatusFlags = if (hideStatus) {
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_FULLSCREEN
    } else {
        0
    }

    return hideStatusFlags or hideNavFlags
}