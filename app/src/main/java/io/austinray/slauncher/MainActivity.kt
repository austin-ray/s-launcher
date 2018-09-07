package io.austinray.slauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = Adapter(loadApps())

        list.layoutManager = LinearLayoutManager(applicationContext)
        list.adapter = adapter
    }

    private fun loadApps() : List<ResolveInfo> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        return packageManager.queryIntentActivities(mainIntent, PackageManager.GET_META_DATA)
                .filter { it.activityInfo.loadLabel(packageManager).isNotEmpty() }
                .sortedBy { it.activityInfo.loadLabel(packageManager).toString() }
    }
}
