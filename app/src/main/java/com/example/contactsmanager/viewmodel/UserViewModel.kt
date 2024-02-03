package com.example.contactsmanager.viewmodel


import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactsmanager.room.User
import com.example.contactsmanager.room.UserRepository
import kotlinx.coroutines.launch

/*
            ViewModel
            ---------
            - Functions nad variables which need data persistence are implemented here.
            - Takes  repository as constructor

            - The functions which need data persistence , need to be present here .
              Function also holds certain states which decides the flow of logic.

            - The functions are present in a DAO , which are copied by a repository
            - Thus a repository is passed to access the functions(tools)

*/

class UserViewModel (private val repository: UserRepository) : ViewModel(),Observable{

    //Instance of the repository , to access its functions
    val user = repository.users


    // A boolean function for flow control , and widget logic
    // This var will decide -  if else  flow in the upcoming functions ,
    // allowing a single function to act as save() or update()
    // and another function to act as delete() or deleteAll()
    private var isUpdateOrDelete = false

    // late initialization of a instance of entity class user /model class /template to segregate data
    private lateinit var userToUpdateOrDelete :User


    //The @Bindable annotation is used in Kotlin to indicate that a getter method is observable.
    // This means that when the value of the property changes,
    // the Data Binding library will be notified and the UI will be updated automatically.


    // notifies the UI about change in these fields
    @Bindable
    val inputName = MutableLiveData<String?>()

    @Bindable
    val inputEmail = MutableLiveData<String?>()



    // notifiers the UI and sets the button text , automatically
    @Bindable
    val  saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val  clearAllOrDeleteButtonText = MutableLiveData<String>()


    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }


//Function deciding button logic

    // calls update() or insert()  from repository
    fun saveOrUpdate(){

        // Function logic for update() - when button shows UPDATE
        if(isUpdateOrDelete){

            //Collecting(name,email) and setting data to pass to update()
            // entity->user->name = userInput in the name section
            userToUpdateOrDelete.name = inputName.value!!
            // entity->user->email = userInput in the email section
            userToUpdateOrDelete.email = inputEmail.value!!

            // calling update(entity class->user(name,email))
            update(userToUpdateOrDelete)

        }

        // Function logic for insert() - when button shows SAVE
        else{
            //store user input  of name and email in a new val
            val name = inputName.value!!
            val email = inputEmail.value!!


            // passing the data insert(entity class ->user(id,name,email))
            // id updates automatically due to autogenerate= ture in entity class
            insert(User(0,name, email))

            // after saving them to the database , empty both fields for new entry
            // re-setting both inputs to null
            inputName.value = null
            inputEmail.value = null
        }
    }

    //calls delete() or deleteALL() from repository
    fun clearAllOrDelete(){
        if(isUpdateOrDelete){
            //Query logic already exists , for delete(user)
            delete(userToUpdateOrDelete)
        }
        else {
            //Query logic already exists for deleteAll()
            clearAll()
        }

    }



// Making data persistent versions of function from repository

    //As suspending functions can only be invoked by another suspending function or within a coroutine.
    // viewModelScope to makes the function call within a coroutine.
    private fun insert(user: User) = viewModelScope.launch {
        repository.insert(user)

        // Not writing anything here because , we have already set the fields empty after insert already
    }
    private fun update(user: User) = viewModelScope.launch {
        repository.update(user)

        //setting the fields empty again after update
        inputName.value=null
        inputEmail.value=null

        //reverting to the original state of the buttons i.e save & clear all form update & delete
        isUpdateOrDelete=false
        saveOrUpdateButtonText.value="Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }
   private fun delete(user: User) = viewModelScope.launch {
        repository.delete(user)

       //setting the fields empty again after deleting
        inputName.value=null
        inputEmail.value=null

       //reverting to the original state of the buttons i.e save & clear all form update & delete
       isUpdateOrDelete=false
        saveOrUpdateButtonText.value="Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }
   private fun clearAll() = viewModelScope.launch {
        repository.deleteAll()
       //this will clear the database as well as fields , query logic
    }


    // this fn changes the button text and logic , when a item is selected from list
    // this function is called in a another fn litItemCLicked()
    fun initUpdateAndDelete(user:User){
        //instance of lateinit  user(id,name,email)
        userToUpdateOrDelete = user

        // re-populating empty fields to selected item data (name,email)
        inputName.value=user.name
        inputEmail.value=user.email

        //changing the button text and logic
        isUpdateOrDelete=true
        saveOrUpdateButtonText.value="Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }



 // These member functions are necessary to implemented , but of no use
    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {}


}