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

class MainActivity : AppCompatActivity() {
    private lateinit var userViewModel: UserViewModel
     private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        //ROOM

        val dao= UserDatabase.getInstance(application).userDAO
        val repository = UserRepository(dao)
        val factory =ViewModelFactory(repository)

        userViewModel = ViewModelProvider(this, factory)[UserViewModel::class.java]


            binding.viewModelVar = userViewModel
            binding.lifecycleOwner=this


        initRecyclerView()
    }

    private fun initRecyclerView() {
       binding.recyclerView.layoutManager = LinearLayoutManager(this)
        displayUserList()
    }
    private fun displayUserList() {
        userViewModel.user.observe(this) {
            binding.recyclerView.adapter = MyRecyclerViewAdapter(it) { selectedItem: User ->
                listItemClicked(
                    selectedItem
                )
            }
        }
    }
    private fun listItemClicked(selectedItem: User) {
        Toast.makeText(this,"Selected name is ${selectedItem.name}" , Toast.LENGTH_LONG).show()
        userViewModel.initUpdateAndDelete(selectedItem)
    }


}