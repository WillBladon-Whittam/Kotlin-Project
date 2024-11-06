/**
 * @author  William Bladon-Whittam
 * @author  Charlie Clark
 */

package main.kotlin.sessions

import main.kotlin.classes.Building
import main.kotlin.classes.University
import main.kotlin.classes.Computer
import main.kotlin.classes.Room
import main.kotlin.classes.ComputerBooking


class AdminSession(private var university: University) {

    fun mainLoop() {
        /**
         * Run the main loop of the admin session
         */

        var running = true
        while (running) {
            println("1. All Bookings\n" +
                    "2. Add Room\n" +
                    "3. Logout\n")

            val number = readlnOrNull()?.toIntOrNull() ?: 0

            when(number) {
                1 -> this.showAllBookings()
                2 -> this.addRoom()
                3 -> running = false
                else -> println("Invalid Option.")
            }
        }
        return
    }

    private fun addRoom() {
        /**
         * Run the main loop of the admin session
         *
         * This needed updating while integrating to include the
         * different types of rooms that are available (e.g. Windows, Mac...)
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
                val studentId = bookingsInSlot[computerNumber.toString()]?.studentId ?: "-"
                print(studentId.padEnd(10))
            }
            println()
        }
    }
}
