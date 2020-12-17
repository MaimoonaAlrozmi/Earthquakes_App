package com.maimoona.earthquakes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maimoona.earthquakes.model.Features
import com.maimoona.earthquakes.model.Properties
import java.math.RoundingMode
import java.util.*

private const val TAG = "EarthquakesFragment"

class EarthquakesFragment : Fragment() {

    private lateinit var earthquakesRecyclerView: RecyclerView
    private lateinit var earthquakesViewModel: EarthquakesViewModel

    companion object {
        fun newInstance() = EarthquakesFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        earthquakesViewModel =
            ViewModelProviders.of(this).get(EarthquakesViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_earthquakes, container, false)
        earthquakesRecyclerView = view.findViewById(R.id.earthquakes_recycler_view);
        earthquakesRecyclerView.layoutManager = LinearLayoutManager(context)

        return view;
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        earthquakesViewModel.earthItemLiveData.observe(
            viewLifecycleOwner,
            Observer { earthquakeItems ->
                earthquakesRecyclerView.adapter = EarthquakeAdapter(earthquakeItems)
            })
    }

    private class EarthquakeHolder(itemTextView: View) : RecyclerView.ViewHolder(itemTextView),
        View.OnClickListener {
        private val magTextView = itemView.findViewById(R.id.mag) as TextView
        private val placeTextView = itemView.findViewById(R.id.place) as TextView
        private val countryTextView = itemView.findViewById(R.id.country) as TextView
        private val timeTextView = itemView.findViewById(R.id.time) as TextView
        private val dateTextView = itemView.findViewById(R.id.date) as TextView


        init {
            itemView.setOnClickListener(this)
        }


        fun bind(features: Features) {
            mag(features.properties.mag)
            date(features.properties.time)
            time(features.properties.time)

            val place = features.properties.place
            val parts = place.split("of".toRegex()).toTypedArray()
            val address = parts[0]
            val country = parts[1]
            placeTextView.setText(country)
            countryTextView.setText(address)

        }

        fun date(date: Long) {
            val cal = Calendar.getInstance()
            cal.time = Date(date)
            val date = "${cal.get(Calendar.YEAR)}-" + "${cal.get(Calendar.MONTH)}-" + "${cal.get(Calendar.DAY_OF_MONTH)}"
            dateTextView.text = date
        }

        fun time(time:Long){
            val cal = Calendar.getInstance()
            cal.time = Date(time)
            val time = "${cal.get(Calendar.HOUR_OF_DAY)}:" + "${cal.get(Calendar.MINUTE)}"
            timeTextView.text = time
        }

        fun mag(mag: Double) {
            magTextView.apply {
                text = mag.toBigDecimal().setScale(1, RoundingMode.CEILING).toString()
                when {
                    mag < 4.0 -> setBackgroundResource(R.drawable.mag_low)
                    mag < 5.0 -> setBackgroundResource(R.drawable.mag_mid)
                    mag <= 6.0 -> setBackgroundResource(R.drawable.mag_more_mid)
                    mag in 6.0..10.0 -> setBackgroundResource(R.drawable.mag_hight)
                }
            }
        }

        fun countryAndPlace(str: String) {
            val parts: List<String> = str.split("of")
            placeTextView.text = """${parts[0]}OF"""
            countryTextView.text = "${parts[1]}"
        }

        override fun onClick(p0: View?) {
            TODO("Not yet implemented")
        }
    }

    private class EarthquakeAdapter(private val earthquakeItems: List<Features>) :
        RecyclerView.Adapter<EarthquakeHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): EarthquakeHolder {

            val view =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_earthquakes, parent, false)
            return EarthquakeHolder(view)
        }

        override fun getItemCount(): Int = earthquakeItems.size

        override fun onBindViewHolder(holder: EarthquakeHolder, position: Int) {
            val quakeItems = earthquakeItems[position]
            holder.bind(quakeItems)
        }
    }
}