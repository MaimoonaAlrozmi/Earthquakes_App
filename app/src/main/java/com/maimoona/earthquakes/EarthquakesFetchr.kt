package com.maimoona.earthquakes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.maimoona.earthquakes.api.EarthquakesApi
import com.maimoona.earthquakes.model.Features
import com.maimoona.earthquakes.model.Properties
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "EarthquakesFetchr"

class EarthquakesFetchr {

    private val earthquakesApi: EarthquakesApi

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/fdsnws/event/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        earthquakesApi = retrofit.create(EarthquakesApi::class.java)
    }

    fun fetchEarthquakes(): LiveData<List<Features>> {
        val responseLiveData: MutableLiveData<List<Features>> = MutableLiveData()

        val flickrRequest: Call<EarthquakeResponse> = earthquakesApi.fetchEarthquakes()
        flickrRequest.enqueue(object : Callback<EarthquakeResponse> {
            override fun onFailure(call: Call<EarthquakeResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch data", t)
            }

            override fun onResponse(
                call: Call<EarthquakeResponse>,
                response: Response<EarthquakeResponse>
            ) {
                Log.d(TAG, "Response received suc")
                val earthquakeResponse: EarthquakeResponse? = response.body()
               // val photoResponse: EarthquakeResponse? = flickrResponse?.earthquakesItems
                var galleryItems: List<Features> = earthquakeResponse?.earthquakesItems
                    ?: mutableListOf()

                responseLiveData.value = galleryItems
            }
        })
        return responseLiveData
    }
}