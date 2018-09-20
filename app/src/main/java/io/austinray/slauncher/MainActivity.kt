package io.austinray.slauncher

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.InputMethodManager
import io.austinray.slauncher.viewmodel.ApplicationsModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var packageReceiver: PackageReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                s?.toString()?.let { adapter.applyFilter(it) }
            }
        })

        searchClear.setOnClickListener { clearSearchBar() }

        packageReceiver = PackageReceiver(appsModel)
        registerReceiver(packageReceiver, packageReceiver?.filter)
    }

    override fun onResume() {
        super.onResume()

        (resultList.adapter as? Adapter)?.reset()
        clearSearchBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(packageReceiver)
    }

    private fun hideKeyboard() {
        val im =
            applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        im.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }

    private fun clearSearchBar() {
        searchInput.text.clear()
        searchInput.clearFocus()
        hideKeyboard()
    }
}
