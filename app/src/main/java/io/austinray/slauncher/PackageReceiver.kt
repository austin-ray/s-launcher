package io.austinray.slauncher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import io.austinray.slauncher.util.iconHandler
import io.austinray.slauncher.util.loadApps
import io.austinray.slauncher.viewmodel.ApplicationsModel

class PackageReceiver(private val viewModel: ApplicationsModel) : BroadcastReceiver() {

    val filter: IntentFilter = IntentFilter()

    init {
        filter.addDataScheme("package")

        filter.addAction(Intent.ACTION_PACKAGE_ADDED)
        filter.addAction(Intent.ACTION_PACKAGE_CHANGED)
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val pm = context?.packageManager

        // Reload the list of icon packs. One may have been installed.
        iconHandler.loadAvailableIconPacks()

        pm?.let {
            viewModel.apps.value = loadApps(it)
        }
    }
}
