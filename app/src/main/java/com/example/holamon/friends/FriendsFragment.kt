    package com.example.holamon.friends

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.holamon.AddFriend
import com.example.holamon.EstadisticasClass
import com.example.holamon.R
import com.example.holamon.stats.Friend
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_friends.*

    class FriendsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FriendAdapter
    private lateinit var viewFriend: View
    private lateinit var emptyText : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFriend = inflater.inflate(R.layout.fragment_friends, container, false)
        adapter = FriendAdapter(viewFriend.context, this::onFriendClick)
        emptyText = viewFriend.findViewById(R.id.textViewEmpty)

        getData()

        recyclerView = viewFriend.findViewById(R.id.recyclerViewTest)
        recyclerView.layoutManager = LinearLayoutManager(viewFriend.context)
        recyclerView.adapter = adapter

        val buttonAdd: FloatingActionButton = viewFriend.findViewById(R.id.buttonAddFriend)

        buttonAdd.setOnClickListener{
            val intent = Intent(viewFriend.context, AddFriend::class.java)
            startActivityForResult(intent, 123)
        }

        return viewFriend
    }

    fun onFriendClick(friend: Friend) {
        EstadisticasClass.deleteFriend(friend)
        getData()
        //Toast.makeText(viewFriend.context, "Delete", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123 && resultCode == Activity.RESULT_OK){
            //Toast.makeText(viewFriend.context, "Updated", Toast.LENGTH_LONG).show()
            getData()
        }
    }

    fun getData(){
        adapter.setData(

            EstadisticasClass.getAllFriends()
        )

        if (adapter.emptyDataset()){
            emptyText.setVisibility(View.VISIBLE)
        } else {
            emptyText.setVisibility(View.INVISIBLE)
        }
    }
}