package com.example.contactsmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.contactsmanager.room.UserRepository
import java.lang.IllegalArgumentException


        // Factory


class ViewModelFactory(private val repository: UserRepository):ViewModelProvider.Factory {

    // The repository used as constructor in viewModel  is handled by the ViewModelFactory

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(UserViewModel::class.java)){
            return UserViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown Model Class")
    }
}