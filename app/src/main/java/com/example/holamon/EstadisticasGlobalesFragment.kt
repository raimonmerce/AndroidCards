package com.example.holamon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holamon.stats.Record
import com.example.holamon.stats.RecordAdapter
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.*
import java.io.IOException
import java.util.*

class EstadisticasGlobalesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewFrag: View
    private lateinit var adapter: RecordAdapter
    private lateinit var emptyText : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFrag = inflater.inflate(R.layout.fragment_estadisticas_globales, container, false)
        adapter = RecordAdapter(viewFrag.context)
        recyclerView = viewFrag.findViewById(R.id.recyclerViewGlobales)
        emptyText = viewFrag.findViewById(R.id.textViewEmpty)
        recyclerView.layoutManager = LinearLayoutManager(viewFrag.context)
        recyclerView.adapter = adapter


        getAllRecords()

        return viewFrag
    }

    fun getAllRecords(){

        var list: List<Record> = emptyList<Record>()
        val requestGet = Request.Builder().get().url("http://${EstadisticasGlobalesClass.ip}:8080/scores").build()

        EstadisticasGlobalesClass.mainClient.newCall(requestGet).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body!!

                val json = Gson().fromJson(responseBody.string(), JsonObject::class.java)
                val jsonScores = json.getAsJsonArray("records")

                var newScores = mutableListOf<Record>()
                for (scoreJson in jsonScores) {
                    newScores.add(Gson().fromJson(scoreJson, Record::class.java))
                }

                if (newScores.isEmpty()){
                    emptyText.setVisibility(View.VISIBLE)
                } else {
                    emptyText.setVisibility(View.INVISIBLE)
                }

                // success

                    activity!!.runOnUiThread(Runnable {
                        adapter.setData(newScores)
                        }
                    )

            }

            override fun onFailure(call: Call, e: IOException) {
                println(e.toString())
            }
        })
    }
}