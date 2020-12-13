package com.maimoona.earthquakes.api


import com.maimoona.earthquakes.EarthquakeResponse
import retrofit2.Call
import retrofit2.http.GET

interface EarthquakesApi {
    @GET("query?format=geojson&limit=10")
    fun fetchEarthquakes(): Call<EarthquakeResponse>
}