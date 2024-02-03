package com.example.contactsmanager.room

/*
            Repository class
            ----------------

            - A Repository manages queries and allows you to use multiple backends.
             In the most common example, the Repository implements the logic for deciding whether
             to fetch data from a network or use results cached in a local database.

             - Best practice
             - suspend functions are taken care by room in a separate thread
             - Takes DAO as constructor
             - Needs to be implemented when working with viewModel

*/

class UserRepository(private val dao : UserDAO) {

    // instance of  query getAllUserInDB(), which provides list of all entity / data class / data in database
    val users = dao.getAllUserInDB()


    // Returning functions of DAO, in  a new function
    // Call a repository function will in return use a tool(fn) of DAO ,
    // that works on database and return desired output from database
    // returned to DAO function
    // returned to repository function
    // repository functions are given same name as DAO fn to avoid confusion
    suspend fun insert(user:User):Long{
        return dao.insertUser(user)
    }

    suspend fun delete(user: User){
        return dao.deleteUser(user)
    }

    suspend fun update(user: User){
        return dao.updateUser(user)
    }

    suspend fun deleteAll(){
        return dao.deleteAll()
    }
}