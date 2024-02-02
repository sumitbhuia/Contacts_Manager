package com.example.contactsmanager.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/*

    - Setting data class / model class as entity and table name is user
    - This is a Model class
    - Used like a template to segregate data
    - Here we don't put and code except the parameters
    - @PrimaryKey(autoGenerate = true)  will generate number fo the below parameters
    - Like indexing the parameters
    id->1
    name ->2
    email->3

  */

@Entity(tableName="user")
data class User(

    @PrimaryKey(autoGenerate = true)

    // Sets table column(1) name = user_ id
    @ColumnInfo(name ="user_id")
    val id : Int ,

    // Sets table column(2) name = user_ name
    @ColumnInfo(name ="user_name")
    var name : String ,

    // Sets table column(3) name = user_ email
    @ColumnInfo(name ="user_email")
    var email : String)
