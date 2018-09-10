package io.austinray.slauncher.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Intent
import android.content.pm.PackageManager
import io.austinray.slauncher.model.AppInfo

class ApplicationsModel : ViewModel() {

    var pm: PackageManager? = null
    private var initialized = false
    var apps: MutableLiveData<List<AppInfo>> = MutableLiveData()
        get() {
        if (!initialized) {
            initialized = true
            apps.value = loadApps()
        }

        return field
    }

    private fun loadApps() : List<AppInfo> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        return if (pm == null) return listOf() else
            pm!!.queryIntentActivities(mainIntent, PackageManager.GET_META_DATA)
                .filter { it.activityInfo.loadLabel(pm!!).isNotEmpty() }
                .map {
                    AppInfo(it.activityInfo.packageName, it.loadLabel(pm).toString(),
                            it.loadIcon(pm))
                }
                .sortedBy { it.label }
    }
}
