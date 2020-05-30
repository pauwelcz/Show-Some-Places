package com.example.showsomeplaces.ui.founded

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showsomeplaces.R
import com.example.showsomeplaces.util.PrefManager
import okhttp3.*
import java.io.IOException


class FoundedActivity : AppCompatActivity() {
    private val prefManager: PrefManager? by lazy { PrefManager(applicationContext) }

    companion object {
        const val ARG_PLACE = "arg_place"

        fun newIntent(context: Context) = Intent(context, FoundedActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)


        val client = OkHttpClient()
        var url = intent.getStringExtra("url")
        // val url = "https://reqres.in/api/users?page=2"
        val request: Request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {


            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val myResponse = response.body!!.string()
                    println(myResponse)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }
        })
        prefManager?.lastAppStartDate = System.currentTimeMillis()

        val fragment = FoundedFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}