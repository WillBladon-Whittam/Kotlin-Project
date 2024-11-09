/**
 * @author  William Bladon-Whittam
 * @author  Charlie Clark
 * @author  Edward Kirr
 */

package main.kotlin.sessions

import main.kotlin.classes.*


class AdminSession(private var university: University, private var accounts: UserAccounts) {

    fun mainLoop() {
        /**
         * Run the main loop of the admin session
         */

        var running = true
        while (running) {
            println("1. View all Bookings\n" +
                    "2. Add a Room\n" +
                    "3. Modify a Room\n" +
                    "4. Delete a Room\n" +
                    "5. Add a User\n" +
                    "6. Modify a User\n" +
                    "7. Delete a User\n" +
                    "8. Logout\n")

            val number = readlnOrNull()?.toIntOrNull() ?: 0

            when(number) {
                1 -> this.showAllBookings()
                2 -> this.addRoom()
                3 -> this.modifyRoom()
                4 -> this.deleteRoom()
                5 -> this.addUser()
                6 -> this.modifyUser()
                7 -> this.deleteUser()
                8 -> running = false
                else -> println("Invalid Option.")
            }
        }
        return
    }

    private fun findRoom(): Room? {
        /**
         * Prompt the user for the building and the room number to find there room.
         */
        print("Enter a Building Name: ")
        val buildingName = readlnOrNull() ?: ""
        val building = university.findBuildingByName(buildingName) ?: run {
            println("Building Not Found!")
            return null
        }

        print("Pick a Room: ")
        val roomNumber = readlnOrNull()?.toIntOrNull() ?: run {
            println("Invalid Number")
            return null
        }
        val room = building.findRoomByNumber(roomNumber) ?: run {
            println("Room Not Found!")
            return null
        }

        println("Room Found!")
        println(room)
        return room
    }

    private fun addRoom() {
        /**
         * Add a new room
         */
        val building = buildingChoice()
        val roomNumber = print("Room number : ").let { readlnOrNull() ?: "Null" }
        val numComputers = print("Number of computers : ").let { readlnOrNull()?.toIntOrNull() ?: 0 }
        println("Enter time slots separated by commas (e.g., 9am-11am, 11am-1pm, 1pm-3pm):")
        val input = readlnOrNull()
        val timeSlots = if (input.isNullOrBlank()) {
            listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm")
        } else {
            input.split(",").map { it.trim() }
        }
        println("Enter Operating System for the room\n" +
                "1. Windows\n" +
                "2. MAC\n" +
                "3. Linux")

        val roomType: String
        while (true) {
            println("Enter the room type (1 for Windows, 2 for Mac, 3 for Linux):")
            val number = readlnOrNull()?.toIntOrNull()

            roomType = when (number) {
                1 -> "Windows"
                2 -> "Mac"
                3 -> "Linux"
                else -> {
                    println("Invalid Option. Please try again.")
                    continue  // Skip to the next iteration of the loop
                }
            }
            break  // Exit the loop if a valid option was selected
        }

        val room = when(roomType) {
            "Windows" -> building.createWindowsRoom(roomNumber.toInt(), timeSlots)
            "Linux" -> building.createLinuxRoom(roomNumber.toInt(), timeSlots)
            "Mac" -> building.createMacRoom(roomNumber.toInt(), timeSlots)
            else -> return
        }

        for (i in 1..numComputers) {
            val computer = Computer(i, room, timeSlots)
            room.addComputer(computer)
        }
    }

    private fun modifyRoom() {
        /**
         * Modify a Room
         *
         * During integration, the checks for the user type can be removed as the user is already logged into
         * their session. Added the modification of rooms timeslots and room numbers.
         */
        val room = this.findRoom() ?: return

        println("What details would you like to change?\n" +
                "1. Room Number\n" +
                "2. Timeslots\n" +
                "3. Room Type")
        val number = readlnOrNull()?.toIntOrNull() ?: 0

        when(number) {
            1 -> {  // Update Room Number
                print("Enter new room number: ")
                val newRoomNumber = readlnOrNull()?.toIntOrNull() ?: run {
                    println("Invalid Number")
                    return
                }
                val foundNumber = room.building.getRooms().find { it.roomNumber == newRoomNumber }
                if (foundNumber == null) {
                    room.roomNumber = newRoomNumber
                    room.updateComputersGlobalId()
                    println("Room Number Updated!")
                } else {
                    println("That room number is already in use!")
                    return
                }
            }
            2 -> {  // Update timeslots
                println("Enter new time slots separated by commas (e.g., 9am-11am, 11am-1pm, 1pm-3pm):")
                val newTimeSlotsString = readlnOrNull() ?: run {
                    println("No value entered")
                    return
                }
                val newTimeSlots = newTimeSlotsString.split(",").map { it.trim() }

                // Update the timeslots in both the room and the computers in that room
                room.timeSlots = newTimeSlots
                for (computer in room.getComputers()) {
                    computer.timeSlots = newTimeSlots
                }
                println("Timeslots Updated!")
            }
            3 -> {  // Update Room Type
                println("Enter the new room type (1 for Windows, 2 for Mac, 3 for Linux):")
                val newRoomType = readlnOrNull()?.toIntOrNull()
                when (newRoomType) {
                    1 -> room.building.updateRoomType(room, "Windows")
                    2 -> room.building.updateRoomType(room, "Mac")
                    3 -> room.building.updateRoomType(room, "Linux")
                    else -> {
                        println("Invalid Option.")
                        return
                    }
                }
                println("Room Type Updated!")
            }
            else -> {
                println("Invalid Option")
                return
            }
        }
    }

    private fun deleteRoom() {
        /**
         * Delete a room
         */
        val room = this.findRoom() ?: return

        println("Are you sure you want to delete this room? Y/N")
        val choice = readlnOrNull() ?: "N"
        when(choice) {
            "Y" -> {
                room.building.deleteRoom(room)
                println("Room successfully removed")
            }
            "N" -> return
        }
    }

    private fun addUser() {
        /**
         * Add a user
         *
         * During integration added some checks to make sure the user cannot create an account with the
         * same username
         */
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
        accounts.getUsers().find { it.name == name }
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

        println("Your details - name: $name, password: ${"*".repeat(password.length)}, " +
                "E-mail: $contact, Admin Rights: $admin ")

        if (admin.lowercase() == "y") {
            print(accounts.addUser(AdminUser(name, password, contact)))
        } else {
            print(accounts.addUser(RegularUser(name, password, contact)))
        }
    }

    private fun modifyUser() {
        /**
         * Modify a user
         *
         * During integration added some checks to make sure the user cannot modify an account with the
         * same username as someone that already exists.
         * */
        println("Enter Account Username:")
        val accountUsername = readlnOrNull() ?: return
        val account = accounts.getUsers().find { it.name == accountUsername } ?: run {
            println("No account found with that username")
            return
        }

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
                    account.name = newAccountUsername
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
                account.password = newAccountPassword
                println("Password Updated!")
            }
            3 -> {  // Update Contact
                print("Enter new email: ")
                val newAccountEmail = readlnOrNull() ?: run {
                    println("Invalid email")
                    return
                }
                account.email = newAccountEmail
                println("Email Updated!")
            }
        }

    }

    private fun deleteUser() {
        /**
         * Delete a User
         *
         * During integration added some checks to make sure the user cannot delete the account they are logged-in as
         */
        println("Enter Account Username:")
        val accountUsername = readlnOrNull() ?: return
        val account = accounts.getUsers().find { it.name == accountUsername } ?: run {
            println("No account found with that username")
            return
        }

        if (account.loggedIn) {
            println("This account is logged in, it cannot be deleted!")
            return
        }

        println("Are you sure you want to delete the account? Y/N")
        val choice = readlnOrNull() ?: "N"
        when(choice) {
            "Y" -> {
                accounts.deleteUser(account)
                println("User successfully removed")
            }
            "N" -> return
        }
    }

    private fun showAllBookings() {
        /**
         * Print all for a specific room on a specific day
         */
        val building = buildingChoice()
        val room = roomChoice(building)
        val day = print("Day : ").let { readln()}
        val bookings = room.getBookingsByDay(day)
        printBookings(bookings, room)
    }

    private fun buildingChoice() : Building {
        /**
         * Prints all the buildings in the university and returns the selected building
         */
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

    private fun roomChoice(building: Building) : Room {
        /**
         * Prints all the rooms in the building and returns the selected room
         */
        while (true) {
            for ((i, room) in building.getRooms().withIndex()) {
                println("${i + 1}. ${room.roomNumber}")
            }
            val index = (readlnOrNull()?.toIntOrNull() ?: 0) - 1
            try {
                return building.getRooms()[index]
            } catch (e: Exception) { println("Invalid Input") }
        }
    }

    private fun printBookings(bookings: List<ComputerBooking>, room: Room) {
        /**
         * Prints all the bookings for a selected room,day in a timetable format
         *
         * This needed updating while integrating, the booking implementation was different between Will and Charlie.
         */
        val computers = room.getComputers()
        val timeSlots = room.timeSlots

        val computerNumbers = computers.sortedBy { it.computerNumber }.map { it.computerNumber }

        print("Computers".padEnd(10))
        println(computerNumbers.joinToString("") { it.toString().padEnd(10) })

        val bookingsByTimeSlot = bookings.groupBy { it.timeSlot }

        for (timeSlot in timeSlots) {
            print(timeSlot.padEnd(10))

            val bookingsInSlot = bookingsByTimeSlot[timeSlot].orEmpty()
                .associateBy { it.computerId.split("-").toTypedArray()[1] }

            for (computerNumber in computerNumbers) {
                val studentId = bookingsInSlot[computerNumber.toString()]?.student?.name ?: "-"
                print(studentId.padEnd(10))
            }
            println()
        }
    }
}
