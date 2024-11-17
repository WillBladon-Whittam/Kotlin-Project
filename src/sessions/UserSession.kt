////////////////////////////// UserSession.kt ///////////////////////////////////
///////////////////////// Author: Edward Kirr //////////////////////////////////
///////// Description: Responsible for program session and state.  ////////////
//// The User Session class runs the main program and its private methods ////
/////// that access the building, rooms, accounts and users methods /////////
/////////////// to change their details or delete //////////////////////////
///////////////////////////////////////////////////////////////////////////
package sessions

import classes.User
import classes.AdminUser
import classes.RegularUser
import src.classes.*


class UserSession {
// Creates a session for the user.
// Responsible for:
// - Creating a user either regular or an admin as an admin
// - Modifying and deleting a room as an admin
// - Modifying and deleting a user as an admin

    // Test users as sign-in functionality is part of task B
    private val accounts = UserAccounts()
    private val loggedUser = AdminUser("Bob", "password123", "email@")
    private val regularUser = RegularUser("Mike", "pass", "email@gmail.com")

    private val building = Building("University")
    private val winRoom = WindowsRoom(205, listOf("8am-10am", "10am-12pm"))
    private val linuxRoom = LinuxRoom(123, listOf("9am-10am", "11am-2pm"))
    private val macRoom = MacRoom(318, listOf("6am-9am", "11am-12pm"))

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
        val name = readlnOrNull() ?: run {
            println("Invalid Username")
            return
        }
        val foundUsername = accounts.getUsers().find { it.name == name }
        if (foundUsername != null) {
            println("The username is already in use!")
            return
        }
        print("Enter a password: ")
        val password = readlnOrNull() ?: run {
            println("Invalid Password")
            return
        }
        print("Enter Your Contact E-mail: ")
        val contact = readlnOrNull() ?: run {
            println("Invalid Email")
            return
        }
        print("Admin? (Y/N): ")
        val admin = readlnOrNull() ?: "N"
        println("Your details - name: $name, password: ${"*".repeat(password.length)}, E-mail: $contact, Admin Rights: $admin ")

        if (admin.lowercase() == "y") {
            print(accounts.addUser(AdminUser(name, password, contact)))
        } else {
            print(accounts.addUser(RegularUser(name, password, contact)))
        }
    }

    private fun modifyRoom(user: User) {
        // Interfaces with the building class' edit room method,
        // to modify the change the OS type of the room.
        // user input validation is partially handled by this method
        if (user is AdminUser) {
            println(building)
            if (building.isEmpty()) {
                println("No rooms have been added")
                println("Returning to menu...")
            } else {
                print("Pick a Room: ")
                val choice = readlnOrNull()?.toIntOrNull() ?: -1
                if (choice < 1 || choice > building.getRooms().size) {
                    println("Invalid room selection. Please pick a valid room number.")
                } else {
                    val room = building.getRooms()[choice - 1] // Retrieve the existing room
                    val existingRoomNumber = room.roomNumber
                    val existingTimeSlot = room.timeSlots
                    val existingRoomType = when (room) {
                        is WindowsRoom -> 1
                        is LinuxRoom -> 2
                        is MacRoom -> 3
                        else -> -1
                    }
                    println("What details would you like to change?\n" +
                            "1. Room Number\n" +
                            "2. Timeslots\n" +
                            "3. Room Type")
                    val detailChoice = readlnOrNull()?.toIntOrNull() ?: 0

                    val result = when (detailChoice) {
                        1 -> {
                            print("Enter new room number: ")
                            val newRoomNumber = readlnOrNull()?.toIntOrNull() ?: existingRoomNumber
                            building.editRoomDetails(choice, updatedType =  existingRoomType,  updatedRoomNumber = newRoomNumber, updatedTimeSlot = existingTimeSlot )
                        }
                        2 -> {
                            print("Enter new timeslots comma-separated (e.g., 9am-11am, 11am-1pm, 1pm-3pm): ")
                            val newTimeSlotsString = readlnOrNull() ?: run {
                                println("No value entered")
                                return
                            }
                            val newTimeSlots = newTimeSlotsString.split(",").map { it.trim() }
                            building.editRoomDetails(choice, updatedType =  existingRoomType, updatedRoomNumber = existingRoomNumber, updatedTimeSlot = newTimeSlots )
                        }
                        3 -> {
                            println("1. Windows Room")
                            println("2. Linux Room")
                            println("3. Mac Room")
                            print("Pick a type (0 to return to menu): ")
                            val newRoomType = readln().toIntOrNull() ?: -1
                            building.editRoomDetails(choice, updatedType  = newRoomType, updatedRoomNumber = existingRoomNumber, updatedTimeSlot = existingTimeSlot )
                        }
                        else -> "Invalid choice. Returning to menu..."
                    }
                    println(result)
                }
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
        if (user is AdminUser) {
            print(building)
            if (building.isEmpty()) {
                println("No rooms have been added")
                println("Returning to menu...")
            } else {
                print("Pick a Room (0 to return to menu): ")
                val choice = readln().toInt()
                if (choice < 1 || choice > building.getRooms().size) {
                    println("Invalid room selection. Please pick a valid room number.")
                } else {
                    val removeRoom = building.deleteRoom(choice)
                    println(removeRoom)
                }
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
        if (user is AdminUser) {
            if (accounts.isEmpty()) {
                println("No accounts have been created")
                println("Returning to menu...")
            } else {
                println(accounts)
                print("Pick an Account: ")
                val choice = readlnOrNull()?.toIntOrNull() ?: - 1
                val currentUser = accounts.getUsers()[choice - 1] // Retrieve the existing user

                val existingUserName = currentUser.name
                val existingUserPassword = currentUser.password
                val existingUserContact = currentUser.email
                println("What details would you like to change?\n" +
                        "1. Username\n" +
                        "2. Password\n" +
                        "3. Contact")
                val number = readlnOrNull()?.toIntOrNull() ?: 0
                when(number) {
                    1 -> {  // Update Username
                        print("Enter new username: ")
                        val newAccountUsername = readlnOrNull() ?: run {
                            println("Invalid Username")
                            return
                        }
                        val foundUsername = accounts.getUsers().find { it.name == newAccountUsername }
                        if (foundUsername == null) {
                            accounts.editUserDetails(index = choice, name = newAccountUsername, password = existingUserPassword, email = existingUserContact)
                            println("Username Updated!")
                        } else {
                            println("The username is already in use!")
                            return
                        }
                    }
                    2 -> {  // Update Password
                        print("Enter new password: ")
                        val newAccountPassword = readlnOrNull() ?: run {
                            println("Invalid Password")
                            return
                        }
                        accounts.editUserDetails(index = choice, name = existingUserName, password = newAccountPassword, email = existingUserContact)
                        println("Password Updated!")
                    }
                    3 -> {  // Update Contact
                        print("Enter new email: ")
                        val newAccountEmail = readlnOrNull() ?: run {
                            println("Invalid email")
                            return
                        }
                        accounts.editUserDetails(index = choice, name = existingUserName, password = existingUserPassword, email = newAccountEmail )
                        println("Email Updated!")
                    }
                }
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
        if (user is AdminUser) {
            if (accounts.isEmpty()) {
                println("No accounts have been created")
                println("Returning to menu...")
            } else {
                println(accounts)
                print("Pick an Account To Delete: ")
                val choice = readln().toInt()
                val currentUser = accounts.getUsers()[choice - 1]
                if (currentUser == user) {
                    println("Unable to delete your account whilst logged in.")
                } else {
                    val delete = accounts.deleteUser(choice)
                    print(delete)
                }
            }
        } else {
            println("Elevated Privileges Required. Please Contact Your Administrator")
            println("Returning to menu...")
        }
    }

}
