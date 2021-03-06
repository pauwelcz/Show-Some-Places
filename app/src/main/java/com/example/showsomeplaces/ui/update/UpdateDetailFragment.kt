package com.example.showsomeplaces.ui.update

// import com.example.showsomeplaces.repository.UserRepository
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.showsomeplaces.R
import com.example.showsomeplaces.extension.toBitmap
import com.example.showsomeplaces.extension.toByteArray
import com.example.showsomeplaces.model.Place
import com.example.showsomeplaces.model.REQUEST_CAMERA_PERMISSION
import com.example.showsomeplaces.model.REQUEST_IMAGE_CAPTURE
import com.example.showsomeplaces.repository.PlaceRepository
import com.example.showsomeplaces.ui.fav.PlaceAdapter
import com.example.showsomeplaces.util.PrefManager
import kotlinx.android.synthetic.main.fragment_detail_update.view.*

class UpdateDetailFragment : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_detail_update, container, false)

        val title = (activity as UpdateActivity).title
        place = place.copy(title = title)
        val latitude = (activity as UpdateActivity).latitude
        place = place.copy(latitude = latitude)
        val longitude = (activity as UpdateActivity).longitude
        place = place.copy(longitude = longitude)
        val note = (activity as UpdateActivity).note
        place = place.copy(note = note)
        val poi = (activity as UpdateActivity).poi
        place = place.copy(poi = poi)
        val imageByteArray = (activity as UpdateActivity).imageByteArray
        place = place.copy(imageByteArray = imageByteArray)
        val id = (activity as UpdateActivity).id
        place = place.copy(id = id)
        // ukladam title

        view.title_edit_text.setText(title)
        view.title_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                place = place.copy(title = s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ukladam latitude
        view.latitude_edit_text.setText(latitude)
        view.latitude_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                place = place.copy(latitude = s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ukladam longitude
        view.longitude_edit_text.setText(longitude)
        view.longitude_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                place = place.copy(longitude = s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // ukladam note
        view.note_edit_text.setText(note)
        view.note_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                place = place.copy(note = s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        /*
            Ukladam  poi
         */
        val poiArray = resources.getStringArray(R.array.array_points_of_interests)

        context?.let { context ->
            view.category_spinner.adapter =
                ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, poiArray)

            /*
                Ziskam si pozici spinneru, protoze jsem vul a neulozil si ji
             */
            val spinnerPosition: Int = (view.category_spinner.adapter as ArrayAdapter<String>).getPosition(poi)
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

        /*
            Ukladam obrazek
         */
        if (imageByteArray != null) {
            view.image_view.setImageBitmap(imageByteArray.toBitmap())
        }

        /*
            Save button
         */
        view.save_button.setOnClickListener {
           adapter?.updatePlace(place)
           placeRepository?.updatePlace(place)
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