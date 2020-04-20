package com.example.showsomeplaces.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.showsomeplaces.R
import kotlinx.android.synthetic.main.fragment_detail.view.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
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
        return view
    }
}
