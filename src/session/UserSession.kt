//Author - Charles Clark
package session
import classes.*

class UserSession {
    private val university = University("Solent")
    private val users = mutableListOf<User>()

    init {
        //Hard coded users
        users.add(User("John", "123", false))
        users.add(User("Steve", "456", true))
        users.add(User("Bob", "789", false))

        //Hard Coded rooms
        val theSpark = university.getBuildings()[0]
        val herbertCollins = university.getBuildings()[1]
        theSpark.addRoom(Room("J305",12, listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm")))
        theSpark.addRoom(Room("J312",15, listOf("9am-11am", "1pm-3pm", "3pm-5pm")))
        herbertCollins.addRoom(Room("HC12",20, listOf("9am-11am", "1pm-3pm", "3pm-5pm")))

        //Hard coded bookings
        val j305 = university.getBuildings()[0].getRooms()[0]
        j305.book("Charlie","Monday",1,"9am-11am")
        j305.book("Jerry","Monday",7,"11am-1pm")
        j305.book("Peter","Monday",3,"3pm-5pm")
        j305.book("Carl","Monday",10,"9am-11am")
    }

    //Start Menu Screen
    fun startMenu() {
        var running = true
        println("----- Booking System -----")
        while (running) {
            val choice = println("1. Login\n2. Exit").let { readlnOrNull()?.toIntOrNull() ?: 0 }
            when (choice) {
                1 -> login()
                2 -> running = false
                else -> println("Invalid")
            }
        }
    }

    //Login loop
    private fun login() {
        var user:User? = null
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
        mainMenu(user)
    }

    //Main loop for when a user is logged in
    private fun mainMenu(user: User) {
        var running = true
        while (running) {
            println(user.menu())
            val choice = readlnOrNull()?.toIntOrNull() ?: 0
            running = if (user.admin) {
                adminMenu(choice, user)
            } else {
                userMenu(choice)
            }
        }
    }

    //Normal User menu
    private fun userMenu(choice: Int) : Boolean {
        when (choice) {
            1 -> printRooms(buildingChoice())
            2 -> return false
            else -> println("Invalid Input")
        }
        return true
    }

    //Admin Menu
    private fun adminMenu(choice: Int, user: User) : Boolean {
        when (choice) {
            //Print Bookings
            1 -> {
                try {
                    val building = buildingChoice()
                    val room = roomChoice(building)
                    val day = print("Day : ").let { readln()}
                    val bookings = user.allBookings(room, day)
                    printBookings(bookings, room)
                } catch (e: Exception) {
                    println("Invalid Input")
                }
            }

            //Add Room
            2 -> {
                val building = buildingChoice()
                val roomName = print("Room name : ").let { readlnOrNull() ?: "Null" }
                val numComputers = print("Number of computers : ").let { readlnOrNull()?.toIntOrNull() ?: 0 }
                val timeSlots = print("Enter time slots (comma-separated) : ").let { readlnOrNull()?.split(",") ?: listOf("Null") }
                user.addRoom(building, roomName, numComputers, timeSlots)
            }

            3 -> return false

            else -> println("Invalid Input")
        }
        return true
    }

    //Prints all the buildings in the university and returns the selected building
    private fun buildingChoice() : Building {
        while (true) {
            for ((i, building) in university.getBuildings().withIndex()) {
                println("${i + 1}. ${building.name}")
            }
            val index = (readlnOrNull()?.toIntOrNull() ?: 0) - 1
            try {
                return university.getBuildings()[index]
            } catch (e: Exception) { println("Invalid Input") }
        }
    }

    //Prints all the rooms in the building and returns the selected room
    private fun roomChoice(building: Building) : Room {
        while (true) {
            for ((i, room) in building.getRooms().withIndex()) {
                println("${i + 1}. ${room.name}")
            }
            val index = (readlnOrNull()?.toIntOrNull() ?: 0) - 1
            try {
                return building.getRooms()[index]
            } catch (e: Exception) { println("Invalid Input") }
        }
    }

    //Prints all the rooms in the building
    private fun printRooms(building: Building) {
        for (room in building.getRooms()) {
            println(room.name)
        }
    }

    //Prints all the bookings for a selected room,day in a timetable format
    private fun printBookings(bookings: MutableList<MutableList<String>>?, room: Room) {
        print("Computers".padEnd(10))
        for (i in 1..room.computers) {
            print("$i".padEnd(10))
        }
        println()
        val timeSlots = room.timeSlots

        for ((i,row) in bookings?.withIndex()!!) {
            print(timeSlots[i].padEnd(10))
            for (col in row) {
                print(col.padEnd(10))
            }
            println()
        }
    }
}