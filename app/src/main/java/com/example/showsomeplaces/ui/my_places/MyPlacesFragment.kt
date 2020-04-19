package com.example.showsomeplaces.ui.my_places

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.showsomeplaces.R

class MyPlacesFragment : Fragment() {


    private lateinit var myPlacesViewModel: MyPlacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myPlacesViewModel =
            ViewModelProviders.of(this).get(MyPlacesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_my_places, container, false)
        val textView: TextView = root.findViewById(R.id.text_my_places)
        myPlacesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
