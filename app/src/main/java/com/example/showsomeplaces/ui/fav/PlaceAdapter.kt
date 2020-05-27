package com.example.showsomeplaces.ui.fav

import android.content.Context
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
import kotlinx.android.synthetic.main.item_place.view.*


class PlaceAdapter(private val context: Context): RecyclerView.Adapter<PlaceAdapter.NoteViewHolder>() {
    private val places: MutableList<Place> = mutableListOf()
    private val placeRepository: PlaceRepository? by lazy { PlaceRepository(context) }

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
        }
    }

    fun addPlace(place: Place) {
        places.add(place)
        notifyDataSetChanged()
    }

    fun deletePlace(place: Place) {
        places.remove(place)
        placeRepository?.deletePlace(place)
        // placeRepository?.deleteAll()
        // notifyItemRemoved(place.id.toInt())
        notifyDataSetChanged()
    }

    fun submitList(notes: List<Place>) {
        this.places.clear()
        this.places.addAll(notes)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        private val deleteButton = view.findViewById(R.id.note_delete_button) as ImageButton
        val editButton = view.findViewById(R.id.note_edit_button) as ImageButton
        private val placeButton = view.findViewById(R.id.note_place_button) as ImageButton

        fun bind(place: Place) {
            view.place_title_text_view.text = place.title
            view.poi_text_view.text = place.poi
            view.note_text_view.text = place.note
            if (place.imageByteArray != null) {
                view.image_view.setImageBitmap(place.imageByteArray?.toBitmap())
            }


            /*
                Deleting place (also from database)
             */
            deleteButton.setOnClickListener {
                deletePlace(place)
                val toastMessage: String = "Place \"" + place.title + "\" deleted."
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }



            /*
                animate place on map
             */
            placeButton.setOnClickListener {
                Toast.makeText(context, "You clicked place button", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
