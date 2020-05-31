package com.example.showsomeplaces.ui.fav

import android.content.Context
import android.content.Intent
import android.location.Location
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.showsomeplaces.R
import com.example.showsomeplaces.extension.toBitmap
import com.example.showsomeplaces.model.Place
import com.example.showsomeplaces.repository.PlaceRepository
import com.example.showsomeplaces.ui.update.UpdateActivity
import kotlinx.android.synthetic.main.item_place.view.*


class PlaceAdapter(private val context: Context): RecyclerView.Adapter<PlaceAdapter.NoteViewHolder>() {
    private val places: MutableList<Place> = mutableListOf()
    private val placeRepository: PlaceRepository? by lazy { PlaceRepository(context) }

    var activity:Context = context
    //val currentLatitude = (activity as MainActivity).currentLatitude
    //val currentLongitude = (activity as MainActivity).currentLongitude

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = places.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(places[position])
        /*
            Editing place
         */
        holder.editButton.setOnClickListener {
            Toast.makeText(context, "You clicked edit button", Toast.LENGTH_SHORT).show()
            val intent = Intent(context, UpdateActivity::class.java)
            intent.putExtra("title", places[position].title)
            intent.putExtra("latitude", places[position].latitude)
            intent.putExtra("longitude", places[position].longitude)
            intent.putExtra("note", places[position].note)
            intent.putExtra("poi", places[position].poi)
            intent.putExtra("imageByteArray", places[position].imageByteArray)
            intent.putExtra("id", places[position].id)
            activity.startActivity(intent)
        }

        /*
               animate place on map
        */
        holder.placeButton.setOnClickListener {
            Toast.makeText(context, "You clicked place button", Toast.LENGTH_SHORT).show()
        }
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
    fun getDistance(startLat: Double, startLong: Double, endLat: Double, endLong: Double): String {
        val result = FloatArray(3)
        Location.distanceBetween(startLat, startLong, endLat, endLong, result)

        return String.format("%.3f", (result[0] / 1000))
    }

    inner class NoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val deleteButton = view.findViewById(R.id.note_delete_button) as ImageButton
        val editButton = view.findViewById(R.id.note_edit_button) as ImageButton
        val placeButton = view.findViewById(R.id.note_place_button) as ImageButton

        fun bind(place: Place) {
            view.place_title_text_view.text = place.title
            view.poi_text_view.text = place.poi
            view.note_text_view.text = place.note
            view.note_distance_km.text = getDistance(place.longitude.toDouble(), place.latitude.toDouble(), place.latitude.toDouble(), place.longitude.toDouble()).toString() + " kilometers from you"

            if (place.imageByteArray != null) {
                view.image_view.setImageBitmap(place.imageByteArray.toBitmap())
            }


            /*
                Deleting place (also from database)
             */
            deleteButton.setOnClickListener {
                deletePlace(place)
                val toastMessage: String = "Place \"" + place.title + "\" deleted."
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
