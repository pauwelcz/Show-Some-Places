package com.example.showsomeplaces.ui.fav

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.showsomeplaces.R
import com.example.showsomeplaces.extension.toBitmap
import com.example.showsomeplaces.extension.toPresentableDate
import com.example.showsomeplaces.model.Place
import kotlinx.android.synthetic.main.item_place.view.*

class PlaceAdapter: RecyclerView.Adapter<PlaceAdapter.NoteViewHolder>() {

    private val places: MutableList<Place> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_place, parent, false)
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
        places.add(place)
        notifyDataSetChanged()
    }

    fun submitList(notes: List<Place>) {
        this.places.clear()
        this.places.addAll(notes)
        notifyDataSetChanged()
    }

    inner class NoteViewHolder(private val view: View): RecyclerView.ViewHolder(view) {

        fun bind(place: Place) {
            view.place_title_text_view.text = place.title
            view.poi_text_view.text = place.poi
            view.note_text_view.text = place.note
            view.date_text_view.text = place.date.toPresentableDate()
            view.image_view.setImageBitmap(place.imageByteArray?.toBitmap())
        }
    }
}