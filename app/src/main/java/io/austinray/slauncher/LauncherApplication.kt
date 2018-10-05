package io.austinray.slauncher

import android.app.Application
import android.content.Context
import io.austinray.slauncher.icons.IconHandler
import io.austinray.slauncher.prefs.ICON_PACK
import io.austinray.slauncher.prefs.PreferencesUpdater
import io.austinray.slauncher.util.iconHandler
import io.austinray.slauncher.util.loadPreferences

class LauncherApplication : Application() {

    val prefUpdater = PreferencesUpdater()

    override fun onCreate() {
        super.onCreate()

        getSharedPreferences("${packageName}_preferences", Context.MODE_PRIVATE).apply {
            registerOnSharedPreferenceChangeListener(prefUpdater)
            loadPreferences(this)
        }

        iconHandler = IconHandler(baseContext)
        iconHandler.loadIconPack(ICON_PACK)
    }
}
