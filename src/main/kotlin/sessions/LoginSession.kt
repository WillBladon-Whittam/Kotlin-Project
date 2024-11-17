/**
 * @author  Charlie Clark
 */

package main.kotlin.sessions

import main.kotlin.classes.*

class LoginSession {
    val accounts =  UserAccounts()
    var university = University("Solent")

    init {
        /**
         * When the session is created there needs to be some hard-coded values to use
         * These can be updated as an admin user
         *
         * These are initialized in the LoginSession so the configuration is kept when logging in and out.
         * e.g. an admin user adds a room, that room needs to stay when they log out and a user logs in.
         */
        val john = RegularUser("John", "123", "john@outlook.com")
        val steve = AdminUser("Steve", "456", "steve@hotmail.co.uk")
        val bob = RegularUser("Bob", "789", "bob@solent.ac.uk")

        accounts.addUser(john)
        accounts.addUser(steve)
        accounts.addUser(bob)

        // Creating Building (Buildings must be created via the University - buildings cant exist without a university)
        val sparkBuilding = university.createBuilding("The Spark", "TS")
        val herbetCollinsBuilding = university.createBuilding("Herbert Collins", "HS")

        // Creating Rooms (Rooms must be created via Building - rooms cant exist without a building)
        val sparkBuildingRoom1 = sparkBuilding.createWindowsRoom(101)
        val sparkBuildingRoom2 = sparkBuilding.createLinuxRoom(202)
        val herbetCollinsBuildingRoom1 = herbetCollinsBuilding.createMacRoom(303)

        // Creating Computers - Adding Computers to Rooms
        for (i in 1..10) {
            val computer = Computer(i, sparkBuildingRoom1)
            sparkBuildingRoom1.addComputer(computer)
        }
        for (i in 1..8) {
            val computer = Computer(i, sparkBuildingRoom2)
            sparkBuildingRoom2.addComputer(computer)
        }
        for (i in 1..2) {
            val computer = Computer(i, herbetCollinsBuildingRoom1)
            herbetCollinsBuildingRoom1.addComputer(computer)
        }

        // Hard coded bookings
        sparkBuildingRoom1.getComputers()[0].addBooking(ComputerBooking
            (sparkBuildingRoom1.getComputers()[0].globalId, "Monday", "9am-11am", john))
        sparkBuildingRoom1.getComputers()[0].addBooking(ComputerBooking(
            sparkBuildingRoom1.getComputers()[0].globalId, "Monday", "11am-1pm", bob))
        sparkBuildingRoom1.getComputers()[2].addBooking(ComputerBooking
            (sparkBuildingRoom1.getComputers()[2].globalId, "Monday", "9am-11am", john))
        sparkBuildingRoom1.getComputers()[3].addBooking(ComputerBooking(
            sparkBuildingRoom1.getComputers()[3].globalId, "Monday", "11am-1pm", bob))
    }

    fun startMenu(): User? {
        /**
         * Start Menu
         */
        var running = true
        println("----- Booking System -----")
        while (running) {
            val choice = println("1. Login\n2. Signup\n3. Exit").let { readlnOrNull()?.toIntOrNull() ?: 0 }
            when (choice) {
                1 -> { return login() }
                2 -> { this.signup() }
                3 -> running = false
                else -> println("Invalid")
            }
        }
        return null
    }

    private fun login(): User {
        /**
         * Login to the booking system
         */
        var user: User? = null
        while (user == null) {
            val username = print("Username : ").let { readln() }
            val password = print("Password : ").let { readln() }
            user = accounts.getUsers().find { it.name == username && it.password == password }
            if (user != null) {
                println("Welcome ${user.name}!")
                user.loggedIn = true
            } else {
                println("Invalid username or password")
            }
        }
        return user
    }

    private fun signup() {
        /**
         * Signup / Create an account
         *
         * This was added during integration to allow users that don't have accounts already to create a regular account
         */
        println("Create an Account:")
        println("Enter a Name: ")
        val name = readlnOrNull()?.takeIf { it.isNotBlank() } ?: run {
            println("Invalid Username")
            return
        }
        val foundUsername = accounts.getUsers().find { it.name == name }
        if (foundUsername != null) {
            println("The username is already in use!")
            return
        }
        accounts.getUsers().find { it.name == name }
        println("Enter a password: ")
        val password = readlnOrNull()?.takeIf { it.isNotBlank() } ?: run {
            println("Invalid Password")
            return
        }
        println("Enter Your Contact E-mail: ")
        val contact = readlnOrNull()?.takeIf { it.isNotBlank() } ?: run {
            println("Invalid Email")
            return
        }
        println("Your details - name: $name, password: ${"*".repeat(password.length)}, E-mail: $contact, ")

        print(accounts.addUser(RegularUser(name, password, contact)))
    }

}