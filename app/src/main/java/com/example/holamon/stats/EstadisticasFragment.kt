package com.example.holamon.stats

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holamon.EstadisticasClass
import com.example.holamon.MainActivity
import com.example.holamon.R
import java.util.*

class EstadisticasFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewFrag: View
    private lateinit var adapter: RecordAdapter
    private lateinit var emptyText : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFrag = inflater.inflate(R.layout.fragment_estadisticas, container, false)
        adapter = RecordAdapter(viewFrag.context)
        recyclerView = viewFrag.findViewById(R.id.recyclerViewTest)
        emptyText = viewFrag.findViewById(R.id.textViewEmpty)
        recyclerView.layoutManager = LinearLayoutManager(viewFrag.context)
        recyclerView.adapter = adapter
        adapter.setData(
            EstadisticasClass.getAllRecords()
        )

        if (adapter.emptyDataset()){
            emptyText.setVisibility(View.VISIBLE)
        } else {
            emptyText.setVisibility(View.INVISIBLE)
        }

        return viewFrag
    }
}