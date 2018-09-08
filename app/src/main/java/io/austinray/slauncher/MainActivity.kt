package io.austinray.slauncher

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.input.InputManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import io.austinray.slauncher.model.AppInfo
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adapter = Adapter(loadApps(), packageManager)

        list.layoutManager = LinearLayoutManager(applicationContext)
        list.adapter = adapter

        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) { }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let { adapter.applyFilter(it) }
            }
        })

        clear.setOnClickListener {
            input.text.clear()
            input.clearFocus()

            val im = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            im.hideSoftInputFromWindow(this.currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun loadApps() : List<AppInfo> {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        return packageManager.queryIntentActivities(mainIntent, PackageManager.GET_META_DATA)
                .filter { it.activityInfo.loadLabel(packageManager).isNotEmpty() }
                .map {
                    AppInfo(it.activityInfo.packageName, it.loadLabel(packageManager).toString(),
                            it.loadIcon(packageManager))
                }
                .sortedBy { it.label }
    }

    override fun onResume() {
        super.onResume()

        (list.adapter as? Adapter)?.reset()
        input.text.clear()
        input.clearFocus()
    }
}
