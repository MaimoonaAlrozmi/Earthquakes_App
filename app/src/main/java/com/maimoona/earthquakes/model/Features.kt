package com.maimoona.earthquakes.model

import com.google.gson.annotations.SerializedName

data class Features(

    @SerializedName("properties") var properties: Properties,
    @SerializedName("geometry") var geometry: Geometry,
    @SerializedName("id") var id: String

)