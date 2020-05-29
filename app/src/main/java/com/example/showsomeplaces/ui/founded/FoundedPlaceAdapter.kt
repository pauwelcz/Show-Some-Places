package com.example.showsomeplaces.ui.founded

import android.content.Context
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.showsomeplaces.R
import com.example.showsomeplaces.model.Place
import com.example.showsomeplaces.repository.PlaceRepository
import kotlinx.android.synthetic.main.item_founded_place.view.*

class FoundedPlaceAdapter(private val context: Context): RecyclerView.Adapter<FoundedPlaceAdapter.NoteViewHolder>() {
    private val places: MutableList<Place> = mutableListOf()
    private val placeRepository: PlaceRepository? by lazy { PlaceRepository(context) }

    var activity:Context = context
    //val currentLatitude = (activity as MainActivity).currentLatitude
    //val currentLongitude = (activity as MainActivity).currentLongitude

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_founded_place, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(places[position])

    }

    fun addPlace(place: Place) {
        places.add(place)
        notifyDataSetChanged()
    }

    fun deletePlace(place: Place) {
        places.remove(place)
        placeRepository?.deletePlace(place)
        notifyDataSetChanged()
    }

    fun updatePlace(place: Place) {
        placeRepository?.updatePlace(place)
        notifyDataSetChanged()
    }

    fun submitList(notes: List<Place>) {
        this.places.clear()
        this.places.addAll(notes)
        notifyDataSetChanged()
    }

    /*
        getting distance form current place
     */
    fun getDistance(startLat: Double, startLong: Double, endLat: Double, endLong: Double): Float {
        val result = FloatArray(3)
        Location.distanceBetween(startLat, startLong, endLat, endLong, result)
        return (result[0] / 1000)
    }

    inner class NoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val zoomButton = view.findViewById(R.id.founded_place_zoom_button) as ImageButton
        val saveButton = view.findViewById(R.id.founded_place_save_button) as ImageButton

        fun bind(place: Place) {
            view.founded_place_title_text_view.text = place.title
            view.founded_poi_text_view.text = place.poi
            view.founded_rating_text_view.text = "4.7"
            //view.note_distance_km.text = getDistance(place.longitude.toDouble(), place.latitude.toDouble(), place.latitude.toDouble(), place.longitude.toDouble()).toString() + " kilometers from you"
        }
    }
}