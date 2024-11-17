/**
 * @author  William Bladon-Whittam
 */

package main.kotlin.sessions

import main.kotlin.classes.University
import main.kotlin.classes.ComputerBooking
import main.kotlin.classes.UserAccounts
import java.time.LocalDate
import java.time.DayOfWeek
import java.time.format.DateTimeFormatter

class UserSession(private var university: University, private var accounts: UserAccounts) {
    /**
     * Creates a session for the base user.
     * The user can do the following:
     * - Search for room by building
     * - Search for room by OS
     * - Book a computer
     * - View Bookings
     * - Cancel Bookings
     * - Quit out the program
     */

    fun mainLoop() {
        /**
         * Run the main loop of the user session
         */

        var running = true
        while (running) {
            println("1. Search for room by building\n" +
                    "2. Search for room by OS\n" +
                    "3. Book a computer\n" +
                    "4. View Bookings\n" +
                    "5. Cancel Bookings\n" +
                    "6. Logout")

            val number = readlnOrNull()?.toIntOrNull() ?: 0

            when(number) {
                1 -> this.searchRoomByBuilding()
                2 -> this.searchRoomByOS()
                3 -> this.bookComputer()
                4 -> this.viewBooking()
                5 -> this.cancelBooking()
                6 -> running = false
                else -> println("Invalid Option.")
            }
        }
    }

    private fun searchRoomByBuilding() {
        /**
         * Search for a room based on the building provided
         */
        println("Enter building name")
        val buildingName = readlnOrNull() ?: ""
        val rooms = university.findBuildingByName(buildingName)?.getRooms() ?: listOf()
        rooms.forEach { println(it) }
    }

    private fun searchRoomByOS() {
        /**
         * Search for a room based on the OS provided (Windows, Mac or Linux)
         */
        println("Enter Operating System\n" +
                "1. Windows\n" +
                "2. MAC\n" +
                "3. Linux")
        val number = readlnOrNull()?.toIntOrNull() ?: 0

        val os = when(number) {
            1 -> "Windows"
            2 -> "Mac"
            3 -> "Linux"
            else -> {
                println("Invalid Option")
                return
            }
        }

        // Loop through all the buildings - then check each rooms Operating Systems print matching ones.
        for (eachBuilding in university.getBuildings()) {
            for (room in eachBuilding.findRoomByOS(os)) {
                println(room)
            }
        }
    }

    private fun bookComputer() {
        /**
         * Book a Computer for a specific day/timeslot for the following week.
         * Monday-Sunday can be selected with the following timeslots 9am-11am, 11am-1pm, 1pm-3pm, 3pm-5pm
         * The user will not be displayed or able to select booked timeslots - so they cannot double book.
         *
         * The brief doesn't really specify how the user should book. So I just ask the user for the computer
         * Global ID to book the specific computer. Another interpretation could be the user enters the room they
         * want a computer in and is assigned a Computer in that room.
         */
        println("Enter the Global ID of the computer to book (e.g. TS101-1)")
        val computerGlobalID = readlnOrNull() ?: ""

        // Find the computer the user has entered with the Global ID
        val foundComputer = university.getBuildings()
            .flatMap { it.getRooms() }.firstNotNullOfOrNull { it.findComputerByGlobalId(computerGlobalID) }

        // If the computer could not be found return an error
        if (foundComputer == null) {
            println("No Computer Found with Global ID $computerGlobalID")
            return
        }

        // Get the available dates/timeslots for the booking - Check if its empty
        val map = foundComputer.getAvailableBookingDates()
        if (map.isEmpty()) {
            println("No available slots for $computerGlobalID next week.")
            return
        }

        // Find out what date next week is to print to the user (e.g. 14th October - 20th October)
        val nextMonday = LocalDate.now().with(DayOfWeek.MONDAY).plusWeeks(1)
        val formatter = DateTimeFormatter.ofPattern("d'th' MMMM")
        val startDate = nextMonday.format(formatter)
        val endDate = nextMonday.plusDays(6).format(formatter)

        // Show the available days/timeslots
        println("Available Dates for $computerGlobalID for week $startDate - $endDate")
        map.forEach { entry ->
            println("${entry.key} : ${entry.value}")
        }

        // Select a day
        println("Please select one of the following available days")
        for ((i, key) in map.keys.withIndex()) {
            println("${i + 1}. $key")
        }
        val selectedDay = readlnOrNull()?.toIntOrNull() ?: 0

        // Check a valid range was entered
        if (selectedDay !in 1..map.size) {
            println("Invalid Option")
            return
        }

        // Select a timeslot
        println("Please select one of the following available timeslot")
        for ((i, timeslot) in map.entries.elementAt(selectedDay-1).value.withIndex()) {
            println("${i + 1}. $timeslot")
        }
        val selectedTimeslot = readlnOrNull()?.toIntOrNull() ?: 0

        // Check a valid range was entered
        if (selectedTimeslot !in 1..map.entries.elementAt(selectedDay-1).value.size) {
            println("Invalid Option")
            return
        }

        // Convert the integers entered from the user to the correct format (e.g. Monday 1pm-3pm)
        val timeslot = map.entries.elementAt(selectedDay-1).value[selectedTimeslot-1]
        val day = map.entries.elementAt(selectedDay-1).key

        // Get logged in User
        val user = accounts.getUsers().find { it.loggedIn } ?: return

        // Add booking
        foundComputer.addBooking(ComputerBooking(computerGlobalID, day, timeslot, user))

        println("$computerGlobalID booked for Computer ${foundComputer.computerNumber} in " +
                "${foundComputer.computerRoom.building.code}${foundComputer.computerRoom.roomNumber} " +
                "at $timeslot on $day")
    }

    private fun getBookings() : List<ComputerBooking> {
        val foundBookings = mutableListOf<ComputerBooking>()
        for (building in university.getBuildings()) {
            for (room in building.getRooms()) {
                for (computer in room.getComputers()) {
                    for (booking in computer.getBookings()) {
                        if (booking.student.loggedIn) {
                            foundBookings.add(booking)
                        }
                    }
                }
            }
        }
        return foundBookings
    }

    private fun viewBooking() : Boolean {
        /**
         * View all the bookings
         *
         * To find all the bookings - I just iterate over all computers in the university and display them.
         * When integrating it would make sense to add the bookings to a User object.
         *
         * During integration, only show bookings from the logged-in user
         */
        val allBookings = this.getBookings()
        if (allBookings.isEmpty()) {
            println("No bookings found.")
            return false
        }
        println("Your bookings:")
        for ((i, booking) in allBookings.withIndex()) {
            if (booking.student.loggedIn) {
                println("${i+1}. ${booking.computerId} on ${booking.day} at ${booking.timeSlot}")
            }
        }
        println("") // New line
        return true
    }

    private fun cancelBooking() {
        /**
         * Cancel a booking.
         *
         * During integration, only cancel bookings from the logged-in user
         */
        val allBookings = this.getBookings()

        val completed = this.viewBooking()
        if (!completed) {
            return
        }
        println("Please enter which booking you would like to cancel.")

        val deletedBooking = readlnOrNull()?.toIntOrNull() ?: 0

        // Check a valid range was entered
        if (deletedBooking !in 1..allBookings.size) {
            println("Invalid Option")
            return
        }

        var counter = 0
        for (building in university.getBuildings()) {
            for (room in building.getRooms()) {
                for (computer in room.getComputers()) {
                    for (booking in computer.getBookings()) {
                        counter += 1
                        if (deletedBooking == counter) {
                            computer.deleteBooking(booking)
                            println("Deleted booking for ${booking.computerId} " +
                                    "on ${booking.day} at ${booking.timeSlot}")
                       }
                    }
                }
            }
        }
    }
}