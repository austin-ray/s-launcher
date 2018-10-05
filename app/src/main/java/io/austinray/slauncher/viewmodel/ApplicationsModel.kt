package io.austinray.slauncher.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.austinray.slauncher.model.AppInfo

class ApplicationsModel : ViewModel() {
    var apps: MutableLiveData<List<AppInfo>> = MutableLiveData()
}
