package com.example.showsomeplaces.ui.search

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.showsomeplaces.R
import com.example.showsomeplaces.ui.founded.FoundedActivity
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        val unitArray = resources.getStringArray(R.array.array_units)
        context?.let { context ->
            view.unit_spiner.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, unitArray)

            view.unit_spiner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {}
                }
        }

        val poiArray = resources.getStringArray(R.array.array_points_of_interests)

        context?.let { context ->
            view.poi_spinner.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, poiArray)

            view.poi_spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {}
                }
        }

        // val searchButton = view.findViewById(R.id.search_button) as Button
// set on-click listener
        view.search_button.setOnClickListener {
            Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
            val intent = Intent (context, FoundedActivity::class.java)

            startActivity(intent)
        }
        // toto je google place apicko
        // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant|atm&keyword=cruise&key=AIzaSyCyk1JhB3_EmxLiC7bs3_knIBuEqOUK_1I
        return view
    }

}
