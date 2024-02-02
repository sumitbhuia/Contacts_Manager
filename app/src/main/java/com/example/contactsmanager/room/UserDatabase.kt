package com.example.contactsmanager.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [User::class], version = 1)
abstract class UserDatabase:RoomDatabase() {

    abstract val userDAO: UserDAO

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
