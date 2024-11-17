/**
 * @author  William Bladon-Whittam
 */

package main.kotlin.classes

data class ComputerBooking(
    val computerId: String,
    val day: String,
    val timeSlot: String,
    val student: User
)

class Computer(val computerNumber: Int, val computerRoom: Room,
               var timeSlots: List<String> = listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm"),
               private val daysOfTheWeek: List<String> = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
) {
    /**
     * Computer object to store the bookings of the Computer
     * Bookings are stored in a mutable set (using a set over a list as there can't be duplicate bookings)
     * The set will store a ComputerBooking data class with the booking information
     * The booking information includes the user that booked, so other users cannot see/edit another users booking.
     *
     */
    var globalId: String = "${computerRoom.building.code}${computerRoom.roomNumber}-$computerNumber"

    private val bookings: MutableSet<ComputerBooking> = mutableSetOf()

    fun addBooking(booking: ComputerBooking): Boolean {
        /**
         * Add a booking to the Computer. Make sure that there is not already a booking for that computer.
         */
        return if (bookings.none {
                it.computerId == booking.computerId &&
                        it.day == booking.day &&
                        it.timeSlot == booking.timeSlot
            }) {
            bookings.add(booking)
            true
        } else {
            false
        }
    }

    fun deleteBooking(booking: ComputerBooking): Boolean {
        /**
         * Delete a Computer booking. Make sure that the booking exists before trying to delete it.
         */
        return if (bookings.none {
                it.computerId == booking.computerId &&
                        it.day == booking.day &&
                        it.timeSlot == booking.timeSlot
            }) {
            false
        } else {
            bookings.remove(booking)
            true
        }
    }

    fun getBookings() : MutableSet<ComputerBooking> {
        return bookings
    }

    private fun isDateTimeBooked(day: String, timeSlot: String, computerId: String): Boolean {
        /**
         * Checks if a particular date and timeslot is already booked for a specific computer
         */
        return bookings.any { it.day == day && it.timeSlot == timeSlot && it.computerId == computerId }
    }

    fun getAvailableBookingDates(): Map<String, List<String>> {
        /**
         * Gets the available booking dates for the following week for the Computer
         */
        // Map each day of the week to its available time slots
        val availableBookingSlots = mutableMapOf<String, MutableList<String>>()

        // Filter out all booked timeslots per day.
        for (day in daysOfTheWeek) {
            val availableTimeSlotsForDate = timeSlots.filter { timeSlot -> !isDateTimeBooked(day, timeSlot, globalId) }

            // Check if there are any timeslots for that day
            if (availableTimeSlotsForDate.isNotEmpty()) {
                availableBookingSlots[day] = availableTimeSlotsForDate.toMutableList()
            }
        }
        return availableBookingSlots
    }

    fun updateGlobalId() {
        globalId = "${computerRoom.building.code}${computerRoom.roomNumber}-$computerNumber"
    }

    override fun toString() : String {
        return "Global ID: $globalId"
    }
}