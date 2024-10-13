//////////////////////////// UserAccounts.kt //////////////////////////////////
///////////////////////// Author: Edward Kirr ////////////////////////////////
///// Description: User Accounts class to handle storage of different ///////
//// user types (admin, regular) and operations i.e. deleting, editing /////
///////////////////////////////////////////////////////////////////////////

package src.classes

import classes.Admin
import classes.User


class UserAccounts {
    // User Accounts class, responsible for storing users in a list and all its operations,
    // editing the user's details and delete a room from the list
    private var users = mutableListOf<User>()
    // Use of aggregation to store users in the mutable list

    fun addUser(user: User) : String {
        // Parameter: user - takes an object of type user
        // Creates either an admin or regular user object,
        // based on the data entered. This is added to the accounts object's list
        // Returns: A string message to notify what user was added.
        users.add(user)
        return when (user) {
            is Admin -> "Admin user added: ${user.name} \n"
            else -> "Regular user added: ${user.name} \n"
        }
    }

    fun editUserDetails(index: Int, name: String?, email: String?): String {
        // Parameters: index - specify the user, name - to change the user's name, email - to change the user's contact details
        // Responsible for changing the user's name and contact details.
        // Returns: A string message to notify whether it was successful or not
        println("Total users: ${users.count()}")
        println("Given index: $index")

        if (index < 1 || index > users.count()) {
            return "WARNING: Please pick a user from within the list \n"
        } else {
            val user = users[index - 1]
            print(user.name)
            print(user.email)
            if (name != null) user.name = name
            if (email != null) user.email = email
            return "User details updated for ${user.name} \n"
        }
    }

    fun deleteUser(index: Int) : String {
        // Parameter: index - specifies the user you want to delete
        // Responsible for deleting the specified user from the list.
        // Returns: A string message to notify whether the user was removed or not
        if (index < 1 || index > users.count()) {
            return "WARNING: Please pick a user from within the list \n"
        } else {
            val user = users[index - 1]
            users.remove(user)
            return "${user.name} has been removed \n"
        }
    }

    override fun toString(): String {
        // Overrides the method to print an itemised list of all the users and its account types (i.e. admin, regular
        // Returns: A string list of users
        val userList = StringBuilder()
        for ((index, user) in users.withIndex()) {
            val userType = when (user) {
                is Admin -> "Admin"
                else -> "Regular"
            }
            userList.append("${index + 1}. Name: ${user.name} Type: $userType\n")
        }
        return userList.toString()
    }

    fun isEmpty(): Boolean {
        // Checks to see if list is empty
        // Returns: True if the list is empty or false if it has items
        return users.isEmpty()
    }

}