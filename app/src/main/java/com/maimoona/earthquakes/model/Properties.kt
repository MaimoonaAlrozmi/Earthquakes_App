package com.maimoona.earthquakes.model

import com.google.gson.annotations.SerializedName
import java.util.*


data class Properties(
    @SerializedName("mag") val mag: Double,
    @SerializedName("title") val title: String,
    @SerializedName("place") val place: String,
    @SerializedName("time") val time: Long,
    @SerializedName("date") val date: Date
) {}