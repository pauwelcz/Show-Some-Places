package com.example.showsomeplaces.ui.fav

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showsomeplaces.MainActivity
import com.example.showsomeplaces.R
import com.example.showsomeplaces.util.PrefManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add.*

class FavActivity : AppCompatActivity() {

    private val prefManager: PrefManager? by lazy { PrefManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)

        prefManager?.lastAppStartDate = System.currentTimeMillis()

        val fragment = FavFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

}
