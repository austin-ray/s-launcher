package io.austinray.slauncher.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.austinray.slauncher.model.AppInfo

class ApplicationsModel : ViewModel() {
    var apps: MutableLiveData<List<AppInfo>> = MutableLiveData()
}
