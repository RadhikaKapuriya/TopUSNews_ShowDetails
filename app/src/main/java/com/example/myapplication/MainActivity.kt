package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.Adapter.ArticlesAdapter
import com.example.Model.Response
import com.example.Model.Utils.url
import com.example.Adapter.RecyclerItemClickListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.Serializable


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Get Tops News from Network Calls
        OkHttpHandler().execute(url)

        //Set Recyleview Layout
        articles.layoutManager = LinearLayoutManager(this)
        //Recyleview item click event
        articles.addOnItemTouchListener(
            RecyclerItemClickListener(
                applicationContext,
                articles,
                object :
                    RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        // do whatever
                        val i = Intent(this@MainActivity, ShowDetails::class.java)
                        i.putExtra(
                            "sampleObject",
                            latestNewsData.articles?.get(position) as Serializable
                        )
                        this@MainActivity.startActivity(i)
                    }

                    override fun onLongItemClick(view: View, position: Int) {
                        // do whatever
                    }
                })
        )
    }



    //Network call for API
    inner class OkHttpHandler : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg params: String?): String? {

            val client = OkHttpClient()
            val request = params[0]?.let { Request.Builder().url(it).build() }
            try {
                val response = request?.let { client.newCall(it).execute() }
                val result = response?.body?.string()
                return result
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            println("result: $result")
            val type = object : TypeToken<Response>() {}.type
            val latestNews: Response = Gson().fromJson(result, type)
            latestNewsData = latestNews
            //set data on adapter for view
            articles.adapter = latestNews.articles?.let { ArticlesAdapter(it) }
        }
    }


    companion object
    {
        lateinit var latestNewsData: Response
    }



}
