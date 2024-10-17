package session

import classes.User
import classes.Admin
import classes.University
import classes.UserLogin

class UserSession {
    private var users = mutableListOf<User>()
    private val solent = University("Solent")

    fun mainLoop() {
        var program = true
        while (program) {
            println("1. Login\n2. Exit")
            val exitChoice = readlnOrNull()?.toIntOrNull() ?: 0
            when (exitChoice) {
                1 -> {
                    print("Username : ")
                    val inputUsername = readln()
                    print("Password : ")
                    val inputPassword = readln()
                    val user = UserLogin().login(inputUsername, inputPassword, users)
                    if (user != null) {
                        var running = true
                        while (running) {
                            println(user.menu())
                            val choice = readlnOrNull()?.toIntOrNull() ?: 0
                            if (user is Admin) {
                                when (choice) {
                                    1 -> {
                                        for (person in users) {
                                            for (item in person.getBookings()) {
                                                println(item)
                                            }
                                        }
                                    }

                                    2 -> {
                                        for ((i, room) in solent.getBuildings().withIndex()) {
                                            println("$i) ${room.name}")
                                        }
                                        val buildingChoice = readlnOrNull()?.toIntOrNull() ?: 0
                                        if (solent.getBuildings().size > buildingChoice && buildingChoice >= 0) {
                                            println("What is the name of the new room?")
                                            val roomName = readlnOrNull() ?: "Null"
                                            user.addRoom(solent, buildingChoice, roomName)
                                        } else {
                                            println("Invalid")
                                        }

                                    }

                                    3 -> running = false
                                    else -> println("Invalid")
                                }
                            } else {
                                when (choice) {
                                    1 -> {
                                        for ((i, buildings) in solent.getBuildings().withIndex()) {
                                            println("$i) ${buildings.name}")
                                        }
                                        val buildingChoice = readlnOrNull()?.toIntOrNull() ?: 0
                                        val building = user.searchRoomByBuilding(buildingChoice, solent)
                                        if (building != null) {
                                            for (rooms in building) {
                                                println(rooms.name)
                                            }
                                        } else {
                                            println("Invalid")
                                        }
                                    }

                                    2 -> user.searchRoomByOS()
                                    3 -> {
                                        print("Computer name : ")
                                        val computer = readlnOrNull().toString()
                                        print("Day : ")
                                        val day = readlnOrNull().toString()
                                        print("Time : ")
                                        val time = readlnOrNull().toString()
                                        user.bookComputer(computer, day, time)
                                    }

                                    4 -> {
                                        val bookings = user.viewBooking()
                                        for (item in bookings) {
                                            println(item)
                                        }
                                    }

                                    5 -> user.cancelBooking()
                                    6 -> running = false
                                    else -> println("Invalid")
                                }
                            }
                        }
                    } else {
                        println("Incorrect Username or Password")
                    }
                }
                2 -> program = false
                else -> println("Invalid")
            }
        }
    }
}