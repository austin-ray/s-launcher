package io.austinray.slauncher

import android.annotation.TargetApi
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceActivity
import android.support.v14.preference.PreferenceFragment
import android.support.v7.preference.ListPreference
import android.view.MenuItem
import io.austinray.slauncher.util.iconHandler

/**
 * A [PreferenceActivity] that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 *
 * See [Android Design: Settings](http://developer.android.com/design/patterns/settings.html)
 * for design guidelines and the [Settings API Guide](http://developer.android.com/guide/topics/ui/settings.html)
 * for more information on developing a Settings UI.
 */
class SettingsActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActionBar()
    }

    /**
     * Set up the [android.app.ActionBar], if the API is available.
     */
    private fun setupActionBar() {
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * {@inheritDoc}
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    override fun onBuildHeaders(target: List<PreferenceActivity.Header>) {
        loadHeadersFromResource(R.xml.pref_headers, target)
    }

    /**
     * This method stops fragment injection in malicious applications.
     * Make sure to deny any unknown fragments here.
     */
    override fun isValidFragment(fragmentName: String): Boolean {
        return PreferenceFragment::class.java.name == fragmentName ||
                GeneralPreferenceFragment::class.java.name == fragmentName
    }

    /**
     * This fragment shows general preferences only. It is used when the
     * activity is showing a two-pane settings UI.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    class GeneralPreferenceFragment : PreferenceFragment() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) { }

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            addPreferencesFromResource(R.xml.pref_general)
            val iconPackPreference = findPreference("icon_pack") as ListPreference?
            initializeIconPackPreference(iconPackPreference!!)
            setHasOptionsMenu(true)
        }

        override fun onOptionsItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            if (id == android.R.id.home) {
                startActivity(Intent(activity, SettingsActivity::class.java))
                return true
            }
            return super.onOptionsItemSelected(item)
        }

        private fun initializeIconPackPreference(lp: ListPreference) {
            val iconPacks = iconHandler.iconPacks

            val entries = mutableListOf<CharSequence>("System Icons")
            val entryVals = mutableListOf<CharSequence>("default")

            iconPacks.forEach { it ->
                entries.add(it.value)
                entryVals.add(it.key)
            }

            lp.entries = entries.toTypedArray()
            lp.entryValues = entryVals.toTypedArray()
        }
    }
}
