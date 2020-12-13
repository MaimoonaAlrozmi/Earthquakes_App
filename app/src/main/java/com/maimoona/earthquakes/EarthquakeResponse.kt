package com.maimoona.earthquakes

import com.google.gson.annotations.SerializedName
import com.maimoona.earthquakes.model.Features

class EarthquakeResponse {
    @SerializedName("features")
    lateinit var earthquakesItems: List<Features>
}