package com.example.showsomeplaces.ui.founded.save

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showsomeplaces.R
import com.example.showsomeplaces.ui.detail.DetailActivity
import com.example.showsomeplaces.ui.detail.DetailFragment

class SaveActivity : AppCompatActivity() {

    public var currentLatitude = "0.0"
    public var currentLongitude = "0.0"
    companion object {
        const val ARG_PLACE = "arg_place"

        fun newIntent(context: Context) = Intent(context, DetailActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)

        currentLatitude = intent.getStringExtra("CURRENT_LATITUDE")
        currentLongitude = intent.getStringExtra("CURRENT_LONGITUDE")

        if (savedInstanceState == null) {
            val fragment = DetailFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}