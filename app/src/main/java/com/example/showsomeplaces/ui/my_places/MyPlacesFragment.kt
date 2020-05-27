package com.example.showsomeplaces.ui.my_places

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.showsomeplaces.R
import com.example.showsomeplaces.model.Place
import com.example.showsomeplaces.repository.PlaceRepository
import com.example.showsomeplaces.ui.detail.DetailActivity
import com.example.showsomeplaces.ui.fav.PlaceAdapter
import com.example.showsomeplaces.util.PrefManager
import kotlinx.android.synthetic.main.fragment_my_places.view.*

class MyPlacesFragment : Fragment() {

    private val adapter: PlaceAdapter? by lazy { context?.let { PlaceAdapter(it) } }
    private val prefManager: PrefManager? by lazy {
        context?.let { PrefManager(it) }
    }

    private val placeRepository: PlaceRepository? by lazy { context?.let { PlaceRepository(it) } }

    companion object {
        const val REQ_PLACE = 1000
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_my_places, container, false).apply {
            fav_places_list.layoutManager = LinearLayoutManager(context)

            val places = placeRepository?.getAllPlaces() ?: listOf()
            adapter?.submitList(places)

            fav_places_list.adapter = adapter
            add_place_button.setOnClickListener {
                startActivityForResult(DetailActivity.newIntent(context), REQ_PLACE)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            REQ_PLACE -> {
                val place = data?.getParcelableExtra<Place>(DetailActivity.ARG_PLACE) ?: return
                adapter?.addPlace(place)

                placeRepository?.insertPlace(place)
            }
        }
    }
}
/*
class MyPlacesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_places, container, false)

        val addPlaceButton = view.findViewById(R.id.add_place_button) as Button
        addPlaceButton.setOnClickListener {
            // Toast.makeText(context, "You clicked me.", Toast.LENGTH_SHORT).show()
            // startActivityForResult(DetailActivity.newIntent(context), FavFragment.REQ_PLACE)
        }
        return view
    }
}
*/