package io.austinray.slauncher

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            val launchIntent = packageManager.getLaunchIntentForPackage("com.android.chrome")
            startActivity(launchIntent)
        }

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        packageManager.queryIntentActivities(mainIntent, PackageManager.GET_META_DATA).forEach { act ->
            Log.e("TEST: ", act.activityInfo.name)
        }
    }
}
