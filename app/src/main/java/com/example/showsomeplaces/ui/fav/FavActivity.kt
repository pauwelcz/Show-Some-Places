package com.example.showsomeplaces.ui.fav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showsomeplaces.R
import com.example.showsomeplaces.util.PrefManager

class FavActivity : AppCompatActivity() {

    public lateinit var currentLatitude: String
    public lateinit var currentLongitude: String

    private val prefManager: PrefManager? by lazy { PrefManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)

        prefManager?.lastAppStartDate = System.currentTimeMillis()

        currentLatitude = intent.getStringExtra("curLat")
        currentLongitude = intent.getStringExtra("curLng")

        val fragment = FavFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}
