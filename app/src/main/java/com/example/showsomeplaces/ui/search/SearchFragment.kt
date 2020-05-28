package com.example.showsomeplaces.ui.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.showsomeplaces.MainActivity
import com.example.showsomeplaces.R
import com.example.showsomeplaces.ui.founded.FoundedActivity
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*


class SearchFragment : Fragment() {

    var unit: String = "Meters"
    var range: String = ""
    var poi: String = "restaurant"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.search_button.isEnabled = false

        val currentLatitude = (activity as MainActivity).currentLatitude
        val currentLongitude = (activity as MainActivity).currentLongitude

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
                    ) {
                        unit = unit_spiner.selectedItem.toString()
                    }
                }

        }

        // view.edit_select_range.setText("298")
        view.edit_select_range.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(s.toString().trim().isNotEmpty()){
                    range = s.toString()
                    search_button.isEnabled = true
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        /*
            Body zajmu
         */
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
                    ) {
                        poi = poi_spinner.selectedItem.toString()
                    }
                }
        }

        // val searchButton = view.findViewById(R.id.search_button) as Button
// set on-click listener
        view.search_button.setOnClickListener {
            /*
            * Potrebuji ziskat spravnou jednotku, v tomto pripade metry
            * */
            val getMetersValue = getMeters(unit, range)

            val nearbyPlacesAddress = getRequestAddress(currentLatitude, currentLongitude, getMetersValue.toString(), getProperlyPOI(poi))
            Toast.makeText(context, nearbyPlacesAddress, Toast.LENGTH_SHORT).show()
            val intent = Intent (context, FoundedActivity::class.java)

            startActivity(intent)
        }
        // toto je google place apicko
        // https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant|atm&keyword=cruise&key=AIzaSyCyk1JhB3_EmxLiC7bs3_knIBuEqOUK_1I
        return view
    }

    /*
        Just creating properly poi
     */
    private fun getProperlyPOI(poi: String): String {
        return poi.decapitalize().replace(" ", "_")
    }
    /*
        Getting Meters function for convert
        default is meters
     */
    private fun getMeters(unit: String, size: String): Long {
        return when (unit) {
            "Kilometers" -> 1000*size.toLong()
            "Miles" -> 1609*size.toLong()
            else -> { // Note the block
                size.toLong()
            }
        }
    }
    /*
        Creating request
     */
    private fun getRequestAddress(latitude: String, longitude: String, poi: String, size: String): String {

        return "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$latitude,$longitude&radius=$size&type=$poi&key=AIzaSyCyk1JhB3_EmxLiC7bs3_knIBuEqOUK_1I"
    }

}
