package com.example.contactsmanager

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsmanager.databinding.ActivityMainBinding
import com.example.contactsmanager.room.User
import com.example.contactsmanager.room.UserDatabase
import com.example.contactsmanager.room.UserRepository
import com.example.contactsmanager.viewUI.MyRecyclerViewAdapter
import com.example.contactsmanager.viewmodel.UserViewModel
import com.example.contactsmanager.viewmodel.ViewModelFactory


/*
        Save data in a local database using Room
        ----------------------------------------

       - What and Why ?
            Apps that handle non-trivial amounts of structured data can benefit greatly from persisting that data locally.
            The most common use case is to cache relevant pieces of data so that when the device cannot access the network,
            the user can still browse that content while they are offline.
            The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite.

       - Install dependencies

       - Components
            There are three major components in Room:

           - The   [database class]  that holds the database and serves as the main access point for the underlying connection to your app's persisted data.
           - [Data entities]   that represent tables in your app's database.
           - [Data access objects (DAOs)]    that provide methods that your app can use to query, update, insert, and delete data in the database.
*/

class MainActivity : AppCompatActivity() {

    //instances of viewModel & binding
    private lateinit var userViewModel: UserViewModel
     private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //ROOM - setup

        //instance of abstract DAO initialized in the Database class
        // initializing  DAO inside a database , like tools inside a mine , tools alone are of no use
        val dao = UserDatabase.getInstance(application).userDAO

        //initializing  repository
        val repository = UserRepository(dao)
        // initializing  factory
        val factory =ViewModelFactory(repository)


        // initializing viewModel
        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]


        // initializing binding - connecting to data-variable , enable widget functionality setup in xml itself
            binding.viewModelVar = userViewModel
        //making everything observable
            binding.lifecycleOwner=this


        //calling fn to initializing  recyclerview
        initRecyclerView()
    }


// 1. AdapterView - RecyclerView

    private fun initRecyclerView() {

        // basic syntax to initialize recyclerview
       binding.recyclerView.layoutManager = LinearLayoutManager(this)

        //when an empty recycler view in initialized , populate it by fill/displaying the user list function
        displayUserList()
    }


// 2. Adapter - passes data from storage to view
    private fun displayUserList() {
        //user(id,name,email)
        userViewModel.user.observe(this) {
            // Initializing the adapter
            // recyclerView.adapter =      MyRecyclerViewAdapter{it(i.e user) , clickListener:User -> void }
            //                                                                                       any function whose return type is void/does not return anything
            binding.recyclerView.adapter = MyRecyclerViewAdapter(it) { selectedItem: User -> listItemClicked(selectedItem) }
        }
    }

    //fn when list item is clicked
    private fun listItemClicked(selectedItem: User) {
        // 1. display toast message
        Toast.makeText(this,"Selected name is ${selectedItem.name}" , Toast.LENGTH_LONG).show()

        // 2. call this function from viewModel->initUpdateAndDelete(user(0,Jack,jack@gmail.com))
        userViewModel.initUpdateAndDelete(selectedItem)
    }


}