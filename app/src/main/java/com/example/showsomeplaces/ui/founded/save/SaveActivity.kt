package com.example.showsomeplaces.ui.founded.save

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showsomeplaces.R

class SaveActivity : AppCompatActivity() {

    public var currentLatitude = "0.0"
    public var currentLongitude = "0.0"
    public var title = ""
    public var poi = ""

    companion object {
        const val ARG_PLACE = "arg_place"

        fun newIntent(context: Context) = Intent(context, SaveActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)

        currentLatitude = intent.getStringExtra("latitude")
        currentLongitude = intent.getStringExtra("longitude")
        title = intent.getStringExtra("title")
        poi = intent.getStringExtra("poi")

        if (savedInstanceState == null) {
            val fragment = SaveFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}