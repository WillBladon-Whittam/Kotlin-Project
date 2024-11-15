////////////////////////////// Room.kt ////////////////////////////////////////
///////////////////////// Author: Edward Kirr ////////////////////////////////
////////////// Description: Simple Room base class //////////////////////////
////////////// with subclasses for mocking purposes ////////////////////////
///////////////////////////////////////////////////////////////////////////
package src.classes

abstract class Room( var roomNumber: Int, var timeSlots: List<String> = listOf("9am-11am", "11am-1pm", "1pm-3pm", "3pm-5pm")) {
    // Attribute: roomNumber - this provides a room number to each room
    // Attribute: timeSlots - this provides a slot to each room
    // Abstract base Room class, for the other room types to inherit from
    abstract fun getOperatingSystem(): String
}


class WindowsRoom(roomNumber: Int, timeSlots: List<String>) : Room(roomNumber, timeSlots) {
    // Windows OS room subclass
    override fun getOperatingSystem(): String {
        return "Windows"
    }
}

class LinuxRoom(roomNumber: Int, timeSlots: List<String>) : Room(roomNumber, timeSlots) {
    // Linux OS room subclass
    override fun getOperatingSystem(): String {
        return "Linux"
    }
}

class MacRoom(roomNumber: Int, timeSlots: List<String>) : Room(roomNumber, timeSlots) {
    // MacOS room subclass
    override fun getOperatingSystem(): String {
        return "Mac"
    }
}