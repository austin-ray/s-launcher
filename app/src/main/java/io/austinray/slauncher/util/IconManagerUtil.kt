package io.austinray.slauncher.util

import android.content.Intent
import android.content.pm.PackageManager
import io.austinray.slauncher.model.AppInfo

fun getInstalledIconPacks(pm: PackageManager, filter: String): MutableList<AppInfo> {
    val packs = mutableListOf<AppInfo>()

    pm.queryIntentActivities(Intent(filter), PackageManager.GET_META_DATA).forEach { info ->
        val activity = info.activityInfo
        val packageName = activity.packageName
        val packageLabel = activity.loadLabel(pm).toString()
        val packageIcon = activity.loadIcon(pm)

        packs.add(AppInfo(packageName, packageLabel, packageIcon))
    }

    return packs
}