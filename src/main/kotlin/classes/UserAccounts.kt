//////////////////////////// UserAccounts.kt //////////////////////////////////
///////////////////////// Author: Edward Kirr ////////////////////////////////
///// Description: User Accounts class to handle storage of different ///////
//// user types (admin, regular) and operations i.e. deleting, editing /////
///////////////////////////////////////////////////////////////////////////

package main.kotlin.classes

class UserAccounts {
    // User Accounts class, responsible for storing users in a list and all its operations,
    // editing the user's details and delete a room from the list
    private var users = mutableListOf<User>()
    // Use of aggregation to store users in the mutable list

    fun getUsers(): List<User> {
        return users
    }

    fun addUser(user: User) : String {
        // Parameter: user - takes an object of type user
        // Creates either an admin or regular user object,
        // based on the data entered. This is added to the accounts object's list
        // Returns: A string message to notify what user was added.
        users.add(user)
        return when (user) {
            is AdminUser -> "Admin user added: ${user.name} \n"
            else -> "Regular user added: ${user.name} \n"
        }
    }

    fun deleteUser(user: User) {
        users.remove(user)
    }

    override fun toString(): String {
        // Overrides the method to print an itemised list of all the users and its account types (i.e. admin, regular
        // Returns: A string list of users
        val userList = StringBuilder()
        for ((index, user) in users.withIndex()) {
            val userType = when (user) {
                is AdminUser -> "Admin"
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