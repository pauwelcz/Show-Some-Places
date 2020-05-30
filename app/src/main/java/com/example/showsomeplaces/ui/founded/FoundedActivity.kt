package com.example.showsomeplaces.ui.founded

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.showsomeplaces.R
import com.example.showsomeplaces.model.FoundedPlace
import com.example.showsomeplaces.util.PrefManager
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class FoundedActivity : AppCompatActivity() {
    private val prefManager: PrefManager? by lazy { PrefManager(applicationContext) }
    public lateinit var poi: String
    public lateinit var url: String
    public lateinit var currentLatitude: String
    public lateinit var currentLongitude: String
    companion object {
        const val ARG_PLACE = "arg_place"

        fun newIntent(context: Context) = Intent(context, FoundedActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_container)


        val client = OkHttpClient()
        poi = intent.getStringExtra("poi")
        url = intent.getStringExtra("url")
        println(url)
        // val url = "https://reqres.in/api/users?page=2"
        val request: Request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {


            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val myResponse = response.body!!.string()
                    val responseJSONArray = JSONObject(myResponse).getJSONArray("results")
                    println(responseJSONArray)
                    var foundedPlaces = mutableListOf<FoundedPlace>()


                    for (i in 0 until responseJSONArray.length()) {
                        var foundedPlace = FoundedPlace()
                        val title = responseJSONArray.getJSONObject(i).get("name")
                        var rating = "Not Rated Yet"
                        if (responseJSONArray.getJSONObject(i).has("rating")) {
                            rating = responseJSONArray.getJSONObject(i).get("rating").toString()
                        }
                        var latLongObject = responseJSONArray.getJSONObject(i).getJSONObject("geometry")
                        latLongObject = latLongObject.getJSONObject("location")
                        var latitude = latLongObject.get("lat")
                        var longitude = latLongObject.get("lng")
                        foundedPlace = foundedPlace.copy(id = i.toLong())
                        foundedPlace = foundedPlace.copy(title = title.toString())
                        foundedPlace = foundedPlace.copy(latitude = latitude.toString())
                        foundedPlace = foundedPlace.copy(longitude = longitude.toString())
                        foundedPlace = foundedPlace.copy(rating = rating)
                        foundedPlace = foundedPlace.copy(poi = poi)

                        foundedPlaces.add(foundedPlace)
                        println(foundedPlace)

                        // Your code here
                    }
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

