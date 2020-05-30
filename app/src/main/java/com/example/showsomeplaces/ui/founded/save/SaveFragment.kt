package com.example.showsomeplaces.ui.founded.save

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.showsomeplaces.R
import com.example.showsomeplaces.extension.toByteArray
import com.example.showsomeplaces.model.Place
import com.example.showsomeplaces.model.REQUEST_CAMERA_PERMISSION
import com.example.showsomeplaces.model.REQUEST_IMAGE_CAPTURE
import com.example.showsomeplaces.repository.PlaceRepository
import com.example.showsomeplaces.ui.fav.PlaceAdapter
import com.example.showsomeplaces.util.PrefManager
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_save.view.*

class SaveFragment : Fragment() {

    private var place = Place()

    private val adapter: PlaceAdapter? by lazy { context?.let { PlaceAdapter(it) } }
    private val prefManager: PrefManager? by lazy {
        context?.let { PrefManager(it) }
    }

    private val placeRepository: PlaceRepository? by lazy { context?.let { PlaceRepository(it) } }
    // private val userRepository by lazy { UserRepository() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        retainInstance = true
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        val currentLatitude = (activity as SaveActivity).currentLatitude
        val currentLongitude = (activity as SaveActivity).currentLongitude
        val currentTitle = (activity as SaveActivity).title
        val currentPoi = (activity as SaveActivity).poi
        // ukladam title
        view.title_edit_text.setText(currentTitle)
        place = place.copy(title = currentTitle)
        view.latitude_edit_text.setText(currentLatitude)
        place = place.copy(latitude = currentLatitude)
        view.longitude_edit_text.setText(currentLongitude)
        place = place.copy(longitude = currentLongitude)
        place = place.copy(poi = currentPoi)

        view.title_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                /*
                    Save button is disabled until string with places is not empty
                */
                if(s.toString().trim().isNotEmpty()){
                    place = place.copy(title = s.toString())
                    save_button.isEnabled = true;
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ukladam latitude
        view.latitude_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                place = place.copy(latitude = s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ukladam longitude
        view.longitude_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                place = place.copy(longitude = s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ukladam note
        view.note_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                place = place.copy(note = s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        val poiArray = resources.getStringArray(R.array.array_points_of_interests)
        // val categoryArray = arrayOf("Doma", "Práce", "Osobní")
        context?.let { context ->
            view.category_spinner.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, poiArray)
            val spinnerPosition: Int = (view.category_spinner.adapter as ArrayAdapter<String>).getPosition(currentPoi)
            view.category_spinner.setSelection(spinnerPosition)

            view.category_spinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        place = place.copy(poi = poiArray[position])
                    }
                }
        }


        view.save_button.setOnClickListener {
            val intent = Intent()
            intent.putExtra(SaveActivity.ARG_PLACE, place)

            adapter?.addPlace(place)
            placeRepository?.insertPlace(place)
            Toast.makeText(context, "Place Saved", Toast.LENGTH_SHORT).show()
            activity?.setResult(Activity.RESULT_OK, intent)
            activity?.finish()
        }

        view.add_photo.setOnClickListener {
            context?.let { context ->
                if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    dispatchTakePictureIntent()
                } else {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.CAMERA),
                        REQUEST_CAMERA_PERMISSION
                    )
                }
            }
        }

        return view
    }

    private fun dispatchTakePictureIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) return

        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                val bitmap = data?.extras?.get("data") as Bitmap?
                if (bitmap != null) {
                    view?.image_view?.setImageBitmap(bitmap)
                    place = place.copy(imageByteArray = bitmap.toByteArray())
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CAMERA_PERMISSION && grantResults.isNotEmpty()
            && grantResults[0] == PackageManager.PERMISSION_GRANTED
        ) {
            dispatchTakePictureIntent()
        }
    }
}