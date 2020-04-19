package com.example.showsomeplaces.ui.fav

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.showsomeplaces.R
import com.example.showsomeplaces.extension.toPresentableDate
import com.example.showsomeplaces.model.Place
import com.example.showsomeplaces.repository.PlaceRepository
import com.example.showsomeplaces.ui.detail.DetailActivity
import com.example.showsomeplaces.util.PrefManager
import kotlinx.android.synthetic.main.fragment_list.view.*
import kotlinx.android.synthetic.main.fragment_list.view.add_button

class FavFragment : Fragment() {

    private val adapter = PlaceAdapter()
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
        inflater.inflate(R.layout.fragment_list, container, false).apply {
            last_app_start_text_view.text = prefManager?.lastAppStartDate?.toPresentableDate()

            recycler_view.layoutManager = LinearLayoutManager(context)

            val places = placeRepository?.getAllPlaces() ?: listOf()
            adapter.submitList(places)
            recycler_view.adapter = adapter

            add_button.setOnClickListener {
                startActivityForResult(DetailActivity.newIntent(context), REQ_PLACE)
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            REQ_PLACE -> {
                val place = data?.getParcelableExtra<Place>(DetailActivity.ARG_PLACE) ?: return
                adapter.addPlace(place)

                placeRepository?.insertPlace(place)
            }
        }
    }
}