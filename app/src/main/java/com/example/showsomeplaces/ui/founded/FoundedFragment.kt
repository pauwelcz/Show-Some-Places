package com.example.showsomeplaces.ui.founded

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
import com.example.showsomeplaces.util.PrefManager
import kotlinx.android.synthetic.main.fragment_list_founded.view.*

class FoundedFragment : Fragment() {
    private val adapter: FoundedPlaceAdapter? by lazy { context?.let { FoundedPlaceAdapter(it) } }
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
        inflater.inflate(R.layout.fragment_list_founded, container, false).apply {

              recycler_view_founded.layoutManager = LinearLayoutManager(context)

              val places = placeRepository?.getAllPlaces() ?: listOf()

              /*
                Tady musim nejak ziskat ten json
               */
              adapter?.submitList(places)
              recycler_view_founded.adapter = adapter
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            REQ_PLACE -> {
                val place = data?.getParcelableExtra<Place>(FoundedActivity.ARG_PLACE) ?: return
                adapter?.addPlace(place)

                placeRepository?.insertPlace(place)
            }
        }
    }
}