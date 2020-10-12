package com.example.holamon.stats

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holamon.R

class RecordAdapter (val context: Context): RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {
    private var dataset = mutableListOf<Record>()

    class RecordViewHolder(val context: Context, itemView: View) : RecyclerView.ViewHolder(itemView){
        private val textName = itemView.findViewById<TextView>(R.id.textName)
        private val textScore = itemView.findViewById<TextView>(R.id.textScore)
        private val textDate = itemView.findViewById<TextView>(R.id.textDate)
        fun bindRecord(record: Record){
            textName.text = record.name
            textScore.text = record.score
            textDate.text = record.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.record_item, parent, false)
        return RecordViewHolder(
            context,
            view
        )
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bindRecord(dataset[position])
    }

    fun setData(list: MutableList<Record>){
        dataset = list
        dataset.sortBy { it.score }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun emptyDataset(): Boolean{
        if (dataset.isEmpty()) return true
        return false
    }

    fun addPerson(record: Record){
        dataset.add(record)
    }
}


