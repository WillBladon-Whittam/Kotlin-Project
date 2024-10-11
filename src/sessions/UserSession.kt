////////////////////////////// Session.kt ///////////////////////////////////////
///////////////////////// Author: Edward Kirr //////////////////////////////////
////// Description: Responsible for program session and state.  ///////////////
///

package sessions

import classes.User
import classes.Admin
import classes.Regular

class UserSession {
/// Creates a session for the user.
/// Responsible for:
/// - Creating a user either regular or an admin
/// - Modifying and deleting a room as an admin
/// - Modifying and deleting a user as an admin

    fun run() {
        var running = true

        while (running) {
            this.createUser()

            println("1. Modify Details of a Room")
            println("2. Delete a Room")
            println("3. Modify a User")
            println("4. Delete a User")
            println("5. Exit Program")
            print("Input: ")
            val input = readlnOrNull()?.toIntOrNull() ?: 0

            when(input) {
                1 -> this.modifyRoom()
                2 -> this.deleteRoom()
                3 -> this.modifyUser()
                4 -> this.deleteUser()
                5 -> running = false
                else -> println("Invalid Option. (Please choose a number between 1-5)")
            }
        }
    }

    private fun createUser(): User {
        println("Create an Account:")
        print("Enter a Name: ")
        val name = readlnOrNull() ?: "Guest"
        print("Enter a password: ")
        val password = readlnOrNull() ?: "password123"
        print("Enter Your Contact E-mail: ")
        val contact = readlnOrNull() ?: "N/A"
        print("Admin? (Y/N): ")
        val admin = readlnOrNull() ?: "N"
        if (admin == "Y" || admin == "y") {
            val user = Admin(name, password, contact)
            return user
        }
        else {
            val user = Regular(name, password, contact)
            return user
        }
    }

    private fun modifyRoom() {
        println("Coming soon.")
        TODO("Not yet implemented")
    }

    private fun deleteRoom() {
        println("Coming soon.")
        TODO("Not yet implemented")
    }

    private fun modifyUser() {
        println("Coming soon.")
        TODO("Not yet implemented")
    }

    private fun deleteUser() {
        println("Coming soon.")
        TODO("Not yet implemented")
    }






}