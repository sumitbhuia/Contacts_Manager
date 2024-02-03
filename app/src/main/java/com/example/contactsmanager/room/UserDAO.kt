package com.example.contactsmanager.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/*
        DAO -  Set of tools(fn) for the app to work and apply on database
        ---
        - not a class
        - its a interface
        - Add functions and query as many as the application demands to operate and apply on the database.
        - In Kotlin, an interface is a collection of abstract methods and properties that define a common contract for classes that implement the interface.
         An interface is similar to an abstract class, but it can be implemented by multiple classes, and it cannot have state.


        - This object provides functions that the app can use to query,update,delete,save  data in the database
        - Not necessary but for , efficiency of the code ,for long running background functions
          ,suspend function is called .

        - Suspending functions are at the center of everything coroutines.
         A suspending function is simply a function that can be paused and resumed at a later time.
         They can execute a long running operation and wait for it to complete without blocking.

         The syntax of a suspending function is similar to that of a regular function except for the addition of the suspend keyword.
         It can take a parameter and have a return type. However,
         suspending functions can only be invoked by another suspending function or within a coroutine.

*/
@Dao
interface UserDAO {

    //@Names are not custom , they are provided by the dependency
    @Insert
    // Function names are custom , takes entity class user as input
    suspend fun insertUser(user: User):Long

    @Update
    suspend fun updateUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT *FROM user")
    fun getAllUserInDB():LiveData<List<User>>

}
