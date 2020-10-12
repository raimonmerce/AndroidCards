package com.example.holamon.friends

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.holamon.R
import com.example.holamon.stats.Friend

class FriendAdapter (val context: Context, val onFriendClick: (Friend) -> Unit): RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {
    private var dataset = mutableListOf<Friend>()

    class FriendViewHolder(val context: Context, itemView: View,  val onFriendClick: (Friend) -> Unit) : RecyclerView.ViewHolder(itemView){
        private val textName = itemView.findViewById<TextView>(R.id.textName)
        private val textSurname = itemView.findViewById<TextView>(R.id.textSurname)
        private val textAge = itemView.findViewById<TextView>(R.id.textAge)
        private val textGame = itemView.findViewById<TextView>(R.id.textGame)
        private val buttonDelete = itemView.findViewById<Button>(R.id.buttonDelete)
        fun bindFriend(friend: Friend){
            textName.text = friend.name
            textSurname.text = friend.surname
            textAge.text = friend.age.toString()
            textGame.text = friend.game
            buttonDelete.setOnClickListener{
                onFriendClick(friend)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_item, parent, false)
        return FriendViewHolder(
            context,
            view,
            onFriendClick
        )
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bindFriend(dataset[position])
    }

    fun setData(list: MutableList<Friend>){
        dataset = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    fun emptyDataset(): Boolean{
        if (dataset.isEmpty()) return true
        return false
    }

    fun addPerson(friend: Friend){
        dataset.add(friend)
    }



}
