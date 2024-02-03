package com.example.contactsmanager.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/*
        DataBase class
        --------------

        - This is the basic structure
        - Singleton pattern is used to make sure , at a time only one instance of database class exists.
        - Clean code
        - conditions
            - @Database
            - list all entities
            - abstract class
            - abstract instance of DAO

*/

@Database (entities = [User::class], version = 1)
abstract class UserDatabase:RoomDatabase() {

    // This is necessary
    abstract val userDAO: UserDAO

    //singleton design pattern
    companion object {
        @Volatile
        // Class to cast into singleton pattern
        private var INSTANCE: UserDatabase? = null

        // public function to retrieve the singleton instance
        fun getInstance(context: Context): UserDatabase {

            synchronized(this) {
                var instance = INSTANCE

                // Check if the instance is already created
                if (instance == null) {
                    // creating database object
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabase::class.java, "users_db"
                    ).build()
                }

                // return the singleton instance
                return instance

            }
        }

    }
}
