package com.example.showsomeplaces.ui.founded

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.showsomeplaces.R
import com.example.showsomeplaces.model.FoundedPlace
import com.example.showsomeplaces.model.Place
import com.example.showsomeplaces.repository.PlaceRepository

class FoundedPlaceAdapter(private val context: Context): RecyclerView.Adapter<FoundedPlaceAdapter.NoteViewHolder>() {
    private val foundedPlaces: MutableList<FoundedPlace> = mutableListOf()
    private val places: MutableList<Place> = mutableListOf()
    private val placeRepository: PlaceRepository? by lazy { PlaceRepository(context) }

    var activity:Context = context

    fun addPlace(place: Place) {
        places.add(place)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoundedPlaceAdapter.NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_founded_place, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = foundedPlaces.size

    inner class NoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}