package io.austinray.slauncher

import android.app.Application
import android.content.Context
import io.austinray.slauncher.icons.IconHandler
import io.austinray.slauncher.prefs.PreferencesUpdater
import io.austinray.slauncher.prefs.Prefs
import io.austinray.slauncher.prefs.Prefs.Keys.ICON_PACK
import io.austinray.slauncher.util.iconHandler

class LauncherApplication : Application() {

    private val prefUpdater = PreferencesUpdater()

    override fun onCreate() {
        super.onCreate()

        getSharedPreferences("${packageName}_preferences", Context.MODE_PRIVATE).apply {
            registerOnSharedPreferenceChangeListener(prefUpdater)
            Prefs.setResource(resources)
            Prefs.load(this)
        }

        iconHandler = IconHandler(baseContext)
        iconHandler.loadIconPack(Prefs[ICON_PACK])
    }
}
