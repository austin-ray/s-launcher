package io.austinray.slauncher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
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
        viewModel.reloadApps()
    }
}
