/**
 * @author  William Bladon-Whittam
 */

package main.kotlin.classes

class Building(val name: String, val code: String, private val university: University) {
    /**
     * Building object to store the rooms of the building
     */
    private var rooms = mutableListOf<Room>()

    fun getRooms() : List<Room> {
        return rooms
    }

    private fun addRoom(room: Room) {
        rooms.add(room)
    }

    fun createWindowsRoom(roomNumber: Int,
                          timeslots: List<String> = listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm")): WindowsRoom {
        val room = WindowsRoom(roomNumber, this, timeslots)
        this.addRoom(room)
        return room
    }

    fun createMacRoom(roomNumber: Int,
                      timeslots: List<String> = listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm")): MacRoom {
        val room = MacRoom(roomNumber, this, timeslots)
        this.addRoom(room)
        return room
    }

    fun createLinuxRoom(roomNumber: Int,
                        timeslots: List<String> = listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm")): LinuxRoom {
        val room = LinuxRoom(roomNumber, this, timeslots)
        this.addRoom(room)
        return room
    }

    fun findRoomByOS (searchOS: String) : List<Room> {
        val roomsFound = mutableListOf<Room>()
        for (currentRoom in rooms) {
            if(currentRoom.getOperatingSystem() == searchOS) {
                roomsFound.add(currentRoom)
            }
        }
        return roomsFound
    }

    override fun toString(): String {
        return "Building: $name University Name: ${university.name} Building Code: $code Rooms: $rooms"
    }
}