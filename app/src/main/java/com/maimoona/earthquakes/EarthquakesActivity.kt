package com.maimoona.earthquakes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EarthquakesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_earthquakes)

        val isFragmentContainerEmpty = savedInstanceState == null
        if (isFragmentContainerEmpty) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, EarthquakesFragment.newInstance())
                .commit()
        }
    }
}