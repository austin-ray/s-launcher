package io.austinray.slauncher.icons

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import org.xmlpull.v1.XmlPullParserException

class IconHandler(ctx: Context) {

    private val pm = ctx.packageManager
    val iconPacks = mutableMapOf<String, String>()

    private var currIconPack = "default"
    internal val iconPackDrawables = mutableMapOf<String, String>()
    private var iconPackRes = ctx.resources

    init {
        loadAvailableIconPacks()
    }

    fun loadAvailableIconPacks() {
        val adwLauncherThemes = pm.queryIntentActivities(
            Intent("org.adw.launcher.THEMES"),
            PackageManager.GET_META_DATA
        )

        adwLauncherThemes.forEach { info ->
            val packageName = info.activityInfo.packageName
            val appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            val packageLabel = pm.getApplicationLabel(appInfo).toString()
            iconPacks[packageName] = packageLabel
        }
    }

    fun loadIconPack(packageName: String) {
        currIconPack = packageName
        iconPackDrawables.clear()

        if (currIconPack == "default") return

        iconPackRes = pm.getResourcesForApplication(currIconPack)

        val appFilterId = iconPackRes.getIdentifier("appfilter", "xml", currIconPack)

        if (appFilterId > 0) {
            // Create a new parser for this package.
            val parser = IconPackParser(this, iconPackRes)
            parser.parseXml(appFilterId)
        } else {
            throw XmlPullParserException("Unable to parse icon pack.")
        }
    }

    private fun getDefaultAppDrawable(compName: ComponentName): Drawable {
        return pm.getActivityIcon(compName)
    }

    fun getDrawableIconForPackage(compName: ComponentName): Drawable {
        val drawable = iconPackDrawables[compName.toString()]

        // Custom icon available
        if (drawable != null) {
            val id = iconPackRes.getIdentifier(drawable, "drawable", currIconPack)

            if (id > 0) {
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    iconPackRes.getDrawable(id, null)
                } else {
                    // Use the deprecated version on platforms that don't have the new method.
                    @Suppress("DEPRECATION")
                    iconPackRes.getDrawable(id)
                }
            }
        }

        return getDefaultAppDrawable(compName) // Fallback to system icon
    }
}
