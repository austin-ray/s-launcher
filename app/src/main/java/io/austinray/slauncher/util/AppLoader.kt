package io.austinray.slauncher.util

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import io.austinray.slauncher.icons.IconHandler
import io.austinray.slauncher.model.AppInfo

lateinit var iconHandler: IconHandler

fun loadApps(pm: PackageManager): List<AppInfo> {
    val mainIntent = Intent(Intent.ACTION_MAIN, null)
    mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
    return pm.queryIntentActivities(mainIntent, PackageManager.GET_META_DATA)
        .asSequence()
        .filter { it.activityInfo.loadLabel(pm).isNotEmpty() }
        .map {
            val packageName = it.activityInfo.packageName
            val activityName = it.activityInfo.name
            val componentName = ComponentName(packageName, activityName)
            AppInfo(
                packageName,
                it.loadLabel(pm).toString(),
                iconHandler.getDrawableIconForPackage(componentName)
            )
        }
        .sortedBy { it.label }
        .toList()
}
