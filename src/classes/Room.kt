////////////////////////////// Room.kt ////////////////////////////////////////
///////////////////////// Author: Edward Kirr ////////////////////////////////
////////////// Description: Simple Room base class //////////////////////////
////////////// with subclasses for mocking purposes ////////////////////////
///////////////////////////////////////////////////////////////////////////
package src.classes

abstract class Room(val roomNumber: Int) {
    // Attribute: roomNumber - this provides a room number to each room
    // Abstract base Room class, for the other room types to inherit from
    abstract fun getRoomType(): String
}

class WindowsRoom(roomNumber: Int) : Room(roomNumber){
    // Windows OS room subclass
    override fun getRoomType(): String {
        return "$roomNumber is a Windows Room"
    }
}

class LinuxRoom(roomNumber: Int) : Room(roomNumber){
    // Linux OS room subclass
    override fun getRoomType(): String {
        return "$roomNumber is a Linux Room"
    }
}

class MacRoom(roomNumber: Int) : Room(roomNumber){
    // MacOS room subclass
    override fun getRoomType(): String {
        return "$roomNumber is a Mac Room"
    }
}