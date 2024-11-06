/**
 * @author  William Bladon-Whittam
 * @author  Charlie Clark
 */

package main.kotlin.sessions

import main.kotlin.classes.Computer
import main.kotlin.classes.ComputerBooking
import main.kotlin.classes.University
import main.kotlin.classes.User

class LoginSession {
    private val users = mutableListOf<User>()
    var university = University("Solent")

    init {
        /**
         * When the session is created there needs to be some hard-coded values to use
         * These can be updated as an admin user
         *
         * These are initialized in the LoginSession so the configuration is kept when logging in and out.
         * e.g. an admin user adds a room, that room needs to stay when they log out and a user logs in.
         */
        users.add(User("John", "123", false))
        users.add(User("Steve", "456", true))
        users.add(User("Bob", "789", false))

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
            (sparkBuildingRoom1.getComputers()[0].globalId, "Monday", "9am-11am", "Charlie"))
        sparkBuildingRoom1.getComputers()[0].addBooking(ComputerBooking(
            sparkBuildingRoom1.getComputers()[0].globalId, "Monday", "11am-1pm", "Jerry"))
        sparkBuildingRoom1.getComputers()[2].addBooking(ComputerBooking
            (sparkBuildingRoom1.getComputers()[2].globalId, "Monday", "9am-11am", "Charlie"))
        sparkBuildingRoom1.getComputers()[3].addBooking(ComputerBooking(
            sparkBuildingRoom1.getComputers()[3].globalId, "Monday", "11am-1pm", "Jerry"))
    }

    //Start Menu Screen
    fun startMenu(): Boolean? {
        var running = true
        println("----- Booking System -----")
        while (running) {
            val choice = println("1. Login\n2. Exit").let { readlnOrNull()?.toIntOrNull() ?: 0 }
            when (choice) {
                1 -> { return login() }
                2 -> running = false
                else -> println("Invalid")
            }
        }
        return null
    }

    //Login loop
    private fun login(): Boolean {
        var user: User? = null
        while (user == null) {
            val username = print("Username : ").let { readln() }
            val password = print("Password : ").let { readln() }
            user = users.find { it.name == username && it.password == password }
            if (user != null) {
                println("Welcome ${user.name}!")
            } else {
                println("Invalid username or password")
            }
        }
        return user.admin
    }

}