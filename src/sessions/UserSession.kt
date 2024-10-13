////////////////////////////// UserSession.kt ///////////////////////////////////
///////////////////////// Author: Edward Kirr //////////////////////////////////
///////// Description: Responsible for program session and state.  ////////////
//// The User Session class runs the main program and its private methods ////
/////// that access the building, rooms, accounts and users methods /////////
/////////////// to change their details or delete //////////////////////////
///////////////////////////////////////////////////////////////////////////
package sessions

import classes.User
import classes.Admin
import classes.Regular
import src.classes.*

class UserSession {
// Creates a session for the user.
// Responsible for:
// - Creating a user either regular or an admin as an admin
// - Modifying and deleting a room as an admin
// - Modifying and deleting a user as an admin

    // Test users as sign-in functionality is part of task B
    private val accounts = UserAccounts()
    private val loggedUser = Admin("Bob", "password123", "email@")
    private val regularUser = Regular("Mike", "pass", "email@gmail.com")

    private val building = Building("University")
    private val winRoom = WindowsRoom(205)
    private val linuxRoom = LinuxRoom(123)
    private val macRoom = MacRoom(318)

    init {
        // When the session is created, some objects are created and initialised to use,
        // as this functionality is covered by the other tasks.
        accounts.addUser(loggedUser)
        accounts.addUser(regularUser)
        building.addRoom(winRoom)
        building.addRoom(linuxRoom)
        building.addRoom(macRoom)
    }


    fun run() {
        // This is the main menu that the end-user interacts with,
        // contains access to all the functionality, menus 2-5 require an admin account
        var running = true

        while (running) {
            println("1. Create a User")
            println("2. Modify Details of a Room")
            println("3. Delete a Room")
            println("4. Modify a User")
            println("5. Delete a User")
            println("6. Return to Main Menu")
            print("Input: ")
            val input = readlnOrNull()?.toIntOrNull() ?: 0

            when(input) {
                1 -> this.createUser()
                2 -> this.modifyRoom(loggedUser)
                3 -> this.deleteRoom(loggedUser)
                4 -> this.modifyUser(loggedUser)
                5 -> this.deleteUser(loggedUser)
                6 -> running = false
                else -> println("Invalid Option. (Please choose a number between 1-6)")
                }
        }
    }

    private fun createUser() {
        // Collects the required information from the user and
        // interfaces with the add_user method to create the user,
        // in the account class
        println("Create an Account:")
        print("Enter a Name: ")
        val name = readlnOrNull() ?: "Guest"
        print("Enter a password: ")
        val password = readlnOrNull() ?: "password123"
        print("Enter Your Contact E-mail: ")
        val contact = readlnOrNull() ?: "N/A"
        print("Admin? (Y/N): ")
        val admin = readlnOrNull() ?: "N"
        println("Your details - name: $name, password: ${"*".repeat(password.length)}, E-mail: $contact, Admin Rights: $admin ")

        if (admin == "Y" || admin == "y") {
            print(accounts.addUser(Admin(name, password, contact)))
        } else {
            print(accounts.addUser(Regular(name, password, contact)))
        }
    }

    private fun modifyRoom(user : User) {
        // Interfaces with the building class' edit room method,
        // to modify the change the OS type of the room.
        // user input validation is partially handled by this method
        if (user is Admin) {
            println(building)
            if (building.isEmpty()) {
                println("No rooms have been added")
                println("Returning to menu...")
            } else {
                print("Pick a Room: ")
                val choice = readln().toInt()
                println("Room Types: ")
                println("1. Windows Room")
                println("2. Linux Room")
                println("3. Mac Room")
                print("Pick a type: ")
                val roomType = readln().toInt()
                val add = building.editRoomDetails(choice, roomType)
                println(add)
            }
        } else {
            println("Elevated Privileges Required. Please Contact Your Administrator")
            println("Returning to menu...")
        }
    }

    private fun deleteRoom(user: User) {
        // Interfaces with the building class' delete room method
        // to delete a specified room.
        // Validation is partially handled by this method, along with the building's method.
        if (user is Admin) {
            print(building)
            if (building.isEmpty()) {
                println("No rooms have been added")
                println("Returning to menu...")
            } else {
                print("Pick a Room: ")
                val choice = readln().toInt()
                val removeRoom = building.deleteRoom(choice)
                println(removeRoom)
            }
        } else {
            println("Elevated Privileges Required. Please Contact Your Administrator")
            println("Returning to menu...")
        }
    }

    private fun modifyUser(user: User) {
        // Interfaces with the account class' edit user method.
        // Responsible for modifying a user's name or email.
        // Validation is partially handled, more validation is handled by the class' method
        if (user is Admin) {
            if (accounts.isEmpty()) {
                println("No accounts have been created")
                println("Returning to menu...")
            } else {
                println(accounts)
                print("Pick an Account: ")
                val choice = readln().toInt()
                print("Enter The New User Name (Leave blank, if unchanged): ")
                val name = readln()
                print("Enter the new contact e-mail (Leave blank, if unchanged): ")
                val email = readln()
                val add = accounts.editUserDetails(choice, name, email)
                println(add)
            }
        } else {
            println("Elevated Privileges Required. Please Contact Your Administrator")
            println("Returning to menu...")
        }
    }

    private fun deleteUser(user: User) {
        // Interfaces with the account class' delete user method,
        // which deletes the user from its list.
        // Validation is the same as the above methods
        if (user is Admin) {
            if (accounts.isEmpty()) {
                println("No accounts have been created")
                println("Returning to menu...")
            } else {
                println(accounts)
                print("Pick an Account To Delete: ")
                val choice = readln().toInt()
                val delete = accounts.deleteUser(choice)
                print(delete)
            }
        } else {
            println("Elevated Privileges Required. Please Contact Your Administrator")
            println("Returning to menu...")
        }
    }

}