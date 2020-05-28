package com.example.showsomeplaces.ui.update

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showsomeplaces.R

class UpdateActivity : AppCompatActivity() {
    companion object {
        const val ARG_PLACE = "arg_place"

        fun newIntent(context: Context) = Intent(context, UpdateActivity::class.java)
    }

    public var title = ""
    public var latitude = ""
    public var longitude = ""
    public var note = ""
    public var poi = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)

        //val intent = intent

        //val mTextView = findViewById<View>(R.id.pokus) as TextView
        title = intent.getStringExtra("title")
        latitude = intent.getStringExtra("latitude")
        longitude = intent.getStringExtra("longitude")
        note = intent.getStringExtra("note")
        poi = intent.getStringExtra("poi")

        if (savedInstanceState == null) {
            val fragment = UpdateDetailFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
        }
    }
}