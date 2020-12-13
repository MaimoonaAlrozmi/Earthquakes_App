package com.maimoona.earthquakes

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.maimoona.earthquakes.model.Features

class EarthquakesViewModel : ViewModel() {

    val earthItemLiveData: LiveData<List<Features>>
    init {
        earthItemLiveData = EarthquakesFetchr().fetchEarthquakes()
    }
}