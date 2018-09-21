package io.austinray.slauncher

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.os.Bundle
import android.preference.PreferenceManager.getDefaultSharedPreferences
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import io.austinray.slauncher.util.determineUiVisibility
import io.austinray.slauncher.viewmodel.ApplicationsModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {

    private var packageReceiver: PackageReceiver? = null

    private var hideNav = false
    private var hideStatus = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPrefs = getDefaultSharedPreferences(this)
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)
        hideNav = sharedPrefs.getBoolean("pref_hide_nav", false)
        hideStatus = sharedPrefs.getBoolean("pref_hide_status", false)

        window.decorView.systemUiVisibility =
                determineUiVisibility(hideNav, hideStatus)

        val appsModel = ViewModelProviders.of(this).get(ApplicationsModel::class.java)
        appsModel.pm = packageManager

        val adapter = Adapter(mutableListOf(), packageManager)
        val layoutManager = LinearLayoutManager(applicationContext)
        layoutManager.stackFromEnd = true

        appsModel.apps.observeForever { apps ->
            adapter.data = apps?.toList()!!
            adapter.applyFilter(adapter.filter)
            adapter.notifyDataSetChanged()
        }

        resultList.layoutManager = layoutManager
        resultList.adapter = adapter

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.toString()?.let {
                    adapter.applyFilter(it)

                    if (it.isEmpty()) {
                        searchClear.visibility = View.INVISIBLE
                        settings.visibility = View.VISIBLE
                    } else {
                        searchClear.visibility = View.VISIBLE
                        settings.visibility = View.INVISIBLE
                    }
                }
            }
        })

        searchClear.setOnClickListener { clearSearchBar() }

        // Open launcher settings
        settings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        // Open system settings
        settings.setOnLongClickListener {
            startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
            true
        }

        packageReceiver = PackageReceiver(appsModel)
        registerReceiver(packageReceiver, packageReceiver?.filter)
    }

    override fun onResume() {
        super.onResume()

        (resultList.adapter as? Adapter)?.reset()
        clearSearchBar()
        window.decorView.systemUiVisibility =
                determineUiVisibility(hideNav, hideStatus)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(packageReceiver)
    }

    private fun hideKeyboard() {
        val im =
            applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        window.decorView.systemUiVisibility =
                determineUiVisibility(hideNav, hideStatus)
    }

    private fun clearSearchBar() {
        searchInput.text.clear()
        searchInput.clearFocus()
        hideKeyboard()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        sharedPreferences?.let {
            if (key == "pref_hide_nav" || key == "pref_hide_status") {
                hideNav = sharedPreferences.getBoolean("pref_hide_nav", false)
                hideStatus = sharedPreferences.getBoolean("pref_hide_status", false)

                window.decorView.systemUiVisibility =
                        determineUiVisibility(hideNav, hideStatus)
            }
        }
    }
}
