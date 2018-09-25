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
import android.view.View
import android.view.inputmethod.InputMethodManager
import io.austinray.slauncher.prefs.HIDE_NAV
import io.austinray.slauncher.prefs.HIDE_STATUS
import io.austinray.slauncher.prefs.PreferencesUpdater
import io.austinray.slauncher.util.determineUiVisibility
import io.austinray.slauncher.util.helper.TextWatcherAdapter
import io.austinray.slauncher.util.loadPreferences
import io.austinray.slauncher.viewmodel.ApplicationsModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnSharedPreferenceChangeListener {

    private var packageReceiver: PackageReceiver? = null

    private var im: InputMethodManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getDefaultSharedPreferences(this).apply {
            val prefUpdater = PreferencesUpdater()
            registerOnSharedPreferenceChangeListener(prefUpdater)
            registerOnSharedPreferenceChangeListener(this@MainActivity)
            loadPreferences(this)
        }

        im = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        window.decorView.systemUiVisibility = determineUiVisibility(HIDE_NAV, HIDE_STATUS)

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

        configureSearchInput()
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

    /**
     * Configure the SearchInput in it's own function as it requires a lot of setup.
     */
    private fun configureSearchInput() {
        with(searchInput) {
            addTextChangedListener(object : TextWatcherAdapter() {
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    s?.toString()?.let {
                        (resultList.adapter as Adapter).applyFilter(it)

                        searchClear.visibility = if (it.isEmpty()) View.INVISIBLE else View.VISIBLE
                        settings.visibility = if (it.isEmpty()) View.VISIBLE else View.INVISIBLE
                    }
                }
            })
            imeActionDone = ::hideKeyboard
            onBackPress = {
                hideKeyboard()
                clearFocus()
            }
        }
    }

    override fun onResume() {
        super.onResume()

        (resultList.adapter as? Adapter)?.reset()
        clearSearchBar()
        window.decorView.systemUiVisibility = determineUiVisibility(HIDE_NAV, HIDE_STATUS)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(packageReceiver)
    }

    private fun hideKeyboard() {
        currentFocus?.clearFocus()
        im?.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        window.decorView.systemUiVisibility = determineUiVisibility(HIDE_NAV, HIDE_STATUS)
    }

    private fun clearSearchBar() {
        searchInput.text.clear()
        searchInput.clearFocus()
        hideKeyboard()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        sharedPreferences?.let {
            if (key == "pref_hide_nav" || key == "pref_hide_status") {
                window.decorView.systemUiVisibility = determineUiVisibility(HIDE_NAV, HIDE_STATUS)
            }
        }
    }
}
