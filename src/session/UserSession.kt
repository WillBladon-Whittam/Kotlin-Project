package session

import userClasses.User
import userClasses.Admin
import classes.University
import classes.Room
import userClasses.UserLogin

class UserSession {
    private var users = mutableListOf<User>()
    private val university = University("Solent")

    init {
        university.getBuildings()[0].addRoom(Room("J305",12, listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm")))
        university.getBuildings()[0].addRoom(Room("J312",15, listOf("9am-11am", "1pm-3pm", "3pm-5pm")))

        university.getBuildings()[0].getRooms()[0].book("Charlie","Monday",1,"9am-11am")
        university.getBuildings()[0].getRooms()[0].book("Jerry","Monday",2,"11am-1pm")
        university.getBuildings()[0].getRooms()[0].book("Peter","Monday",3,"3pm-5pm")
        university.getBuildings()[0].getRooms()[0].book("Carl","Monday",0,"9am-11am")
    }

    fun startMenu() {
        var running = true
        while (running) {
            val choice = println("1. Login\n2. Exit").let { readlnOrNull()?.toIntOrNull() ?: 0 }
            when (choice) {
                1 -> mainMenu()
                2 -> running = false
                else -> println("Invalid")
            }
        }
    }

    private fun mainMenu() {
        val inputUsername = print("Username : ").let { readln() }
        val inputPassword = print("Password : ").let { readln() }
        val user = UserLogin().login(inputUsername, inputPassword, users)
        if (user != null) {
            var running = true
            while (running) {
                println(user.menu())
                val choice = readlnOrNull()?.toIntOrNull() ?: 0
                running = if (user is Admin) {
                    adminMenu(choice, user)
                } else {
                    userMenu(choice, user)
                }
            }
        } else {
            println("Incorrect Username and Password")
        }
    }


    private fun adminMenu(choice: Int, user: Admin) : Boolean {
        when (choice) {
            1 -> {
                try {
                    val building = buildingChoice()
                    val room = roomChoice(building)
                    val day = print("Day : ").let { readln()}
                    val bookings = user.allBookings(building, room, day, university)
                    if (bookings != null) {
                        printBookings(bookings, building, room)
                    } else {
                        println("Invalid Inputs")
                    }
                } catch(e: Exception) {
                    println("Invalid Inputs")
                }
            }

            2 -> {
                val buildingChoice = buildingChoice()
                if (university.getBuildings().size > buildingChoice && buildingChoice >= 0) {
                    val roomName = print("Room name : ").let {readlnOrNull() ?: "Null"}
                    val numComputers = print("Number of computers : ").let {readlnOrNull()?.toIntOrNull() ?: 0}
                    val timeSlots = print("Enter time slots (comma-separated) : ").let { readlnOrNull()?.split(",") ?: listOf("Null") }
                    user.addRoom(university, buildingChoice, roomName, numComputers, timeSlots)
                } else {
                    println("Invalid Inputs")
                }
            }

            3 -> return false

            else -> println("Invalid Input")
        }
        return true
    }

    private fun userMenu(choice: Int, user: User) : Boolean {
        when (choice) {
            1 -> printRooms(buildingChoice())
            2 -> user.searchRoomByOS()
            3 -> user.bookComputer()
            4 -> user.viewBookings()
            5 -> user.cancelBooking()
            6 -> return false
            else -> println("Invalid Input")
        }
        return true
    }

    private fun buildingChoice() : Int {
        for ((i, building) in university.getBuildings().withIndex()) {
            println("${i+1}. ${building.name}")
        }
        return (readlnOrNull()?.toIntOrNull() ?: 0)-1
    }

    private fun roomChoice(building: Int) : Int {
        for ((i, room) in university.getBuildings()[building].getRooms().withIndex()) {
            println("${i + 1}. ${room.name}")
        }
        return (readlnOrNull()?.toIntOrNull() ?: 0) - 1
    }

    private fun printRooms(building: Int) {
        for (room in university.getBuildings()[building].getRooms()) {
            println(room.name)
        }
    }

    private fun printBookings(bookings: MutableList<MutableList<String>>?, building: Int, room: Int) {
        print("Computers".padEnd(10))
        for (i in 1..university.getBuildings()[building].getRooms()[room].computers) {
            print("$i".padEnd(10))
        }
        println()
        val timeSlots = university.getBuildings()[building].getRooms()[room].timeSlots

        for ((i,row) in bookings?.withIndex()!!) {
            print(timeSlots[i].padEnd(10))
            for (col in row) {
                print(col.padEnd(10))
            }
            println()
        }
    }
}