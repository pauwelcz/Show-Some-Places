package com.example.showsomeplaces.ui.founded

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showsomeplaces.R
import com.example.showsomeplaces.model.FoundedPlace
import com.example.showsomeplaces.util.PrefManager


class FoundedActivity : AppCompatActivity() {
    private val prefManager: PrefManager? by lazy { PrefManager(applicationContext) }
    public lateinit var poi: String
    public lateinit var url: String
    public lateinit var currentLatitude: String
    public lateinit var currentLongitude: String
    public var foundedPlaces = mutableListOf<FoundedPlace>()
    public var listOfFoundedPlaces = listOf<FoundedPlace>()

    companion object {
        const val ARG_PLACE = "arg_place"

        fun newIntent(context: Context) = Intent(context, FoundedActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)

        currentLatitude = intent.getStringExtra("curLat")
        currentLongitude = intent.getStringExtra("curLng")
        foundedPlaces = intent.getParcelableArrayListExtra<FoundedPlace>("foundedPlaces")

        prefManager?.lastAppStartDate = System.currentTimeMillis()

        val fragment = FoundedFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}

