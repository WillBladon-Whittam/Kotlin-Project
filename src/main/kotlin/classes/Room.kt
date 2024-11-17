/**
 * @author  William Bladon-Whittam
 * @author  Charlie Clark
 */

package main.kotlin.classes

abstract class Room(
    var roomNumber: Int, val building: Building,
    var timeSlots: List<String> = listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm"),
    val daysOfTheWeek: List<String> = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday")
) {
    /**
     * Abstract room class to store the computers of the room
     *
     * Brief state:
     * "All computers in a given lab have the same operating system  (Windows, Linux or Mac)."
     *  So use Room as the abstract class - then use specific OS Room subclasses.
     *
     * While integrating, the brief describes that timeslots should not be hard coded and need to be set per room,
     * not per computer. So this has been updated to reflect that
     */
    private var computers = mutableListOf<Computer>()

    abstract fun getOperatingSystem(): String

    fun addComputer(computer: Computer) {
        computers.add(computer)
    }

    fun getComputers() : List<Computer> {
        return computers
    }

    fun findComputerByGlobalId(searchId: String) : Computer? {
        for(currentComputer in computers) {
            if(currentComputer.globalId == searchId) {
                return currentComputer
            }
        }
        return null
    }

    fun getBookingsByDay(day: String): MutableList<ComputerBooking> {
        val bookings = mutableListOf<ComputerBooking>()
        for (computer in computers) {
            for (booking in computer.getBookings()) {
                if (booking.day == day) {
                    bookings.add(booking)
                }
            }
        }
        return bookings
    }

    fun updateComputersGlobalId() {
        for (computer in computers) {
            computer.updateGlobalId()
        }
    }

    override fun toString(): String {
        return "Room $roomNumber in building ${building.name} OS: ${getOperatingSystem()} Computers: $computers"
    }
}

class WindowsRoom(roomNumber: Int,
                  building: Building,
                  timeSlots: List<String>,
                  daysOfTheWeek: List<String>) : Room(roomNumber, building, timeSlots, daysOfTheWeek) {
    /**
     * Room with Windows Operating systems on the Computers
     */
    override fun getOperatingSystem(): String {
        return "Windows"
    }
}

class MacRoom(roomNumber: Int,
              building: Building,
              timeSlots: List<String>,
              daysOfTheWeek: List<String>) : Room(roomNumber, building, timeSlots, daysOfTheWeek) {
    /**
     * Room with MAC Operating systems on the Computers
     */
    override fun getOperatingSystem(): String {
        return "Mac"
    }
}

class LinuxRoom(roomNumber: Int,
                building: Building,
                timeSlots: List<String>,
                daysOfTheWeek: List<String>) : Room(roomNumber, building, timeSlots, daysOfTheWeek) {
    /**
     * Room with Linux Operating systems on the Computers
     */
    override fun getOperatingSystem(): String {
        return "Linux"
    }
}

