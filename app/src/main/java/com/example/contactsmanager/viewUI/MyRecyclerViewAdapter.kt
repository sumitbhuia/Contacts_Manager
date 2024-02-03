package com.example.contactsmanager.viewUI

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsmanager.R
import com.example.contactsmanager.databinding.CardItemBinding
import com.example.contactsmanager.room.User



//      ADAPTER -> to pass data from storage to view , uses a view holder for efficiency
//      -------

// Custom Adapter class
//(var listName : List<Entity/model class> , clickListener(entity)->void) : adapter of recyclerView , the adapter creates an object called MyViewHolder()
//          takes a whole list on users     ,   take all the data of the selected item
class MyRecyclerViewAdapter(private val userList: List<User>, private val clickListener: (User)->Unit): RecyclerView.Adapter<MyViewHolder>() {

//implementing member functions

    // 1. Returns information , no. of items
    override fun getItemCount(): Int {
        return userList.size
    }

    // 2. create new instances of ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //Initializing inflater
        val layoutInflater = LayoutInflater.from(parent.context)
        // Initializing binding
        val binding : CardItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.card_item,parent,false)
        // making new viewHolder(binding)
        return MyViewHolder(binding)

    }

    // 3. populates cardView item with data
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //cardView -> has name and email
        //populates a view with (name,email) by calling bind function from MyViewHolder

        //holder.bind(userList(0)->user 1(0,name1 , email1) fields/location , clickListener(0,Jack,jack@gmail.com)real data )
        holder.bind(userList[position],clickListener)
        //Bind function later initialized in MyViewHolder
    }





}

//A custom viewHolder -> the idea of using less plates
// this has a function that helps binding in the onBindViewHolder
class MyViewHolder(val binding : CardItemBinding) : RecyclerView.ViewHolder(binding.root){

    // Its function is to map data in database to correct view item
    // Like serving burger to only empty plates
    // or Fries to plates already with burger

    // take a user(id,name,email) and a
    fun bind(user: User, clickListener: (User) -> Unit){


            // set the cardItem->name = user1 ->name
            binding.nameTextView.text = user.name
            // set the cardItem->email = user1 ->email
            binding.emailTextView.text = user.email


        // clickListener stores all the data of the clicked/observed/next in the Line item and returns nothing ,
        // observed data to be used in initUpdateAndDelete() and in onBindViewHolder()
            binding.listItemLayout.setOnClickListener {
                clickListener(user)
            }

    }
}
