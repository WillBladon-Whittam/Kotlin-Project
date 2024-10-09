/*
Brief state:
"All computers in a given lab have the same operating system  (Windows, Linux or Mac)."
Abit wierd as an OS should be an attribute of a computer but oh well that's what it says
 */


abstract class Room(val roomNumber: Int, val building: Building) {
    var computers = mutableListOf<Computer>()

    abstract fun getOperatingSystem(): String

    fun addComputer(computer: Computer) {
        computers.add(computer)
    }

    override fun toString(): String {
        return "Room $roomNumber in building ${building.name} OS: ${getOperatingSystem()} Computers: $computers"
    }
}

class WindowsRoom(roomNumber: Int, building: Building) : Room(roomNumber, building) {
    override fun getOperatingSystem(): String {
        return "Windows"
    }
}

class MacRoom(roomNumber: Int, building: Building) : Room(roomNumber, building) {
    override fun getOperatingSystem(): String {
        return "Mac"
    }
}

class LinuxRoom(roomNumber: Int, building: Building) : Room(roomNumber, building) {
    override fun getOperatingSystem(): String {
        return "Linux"
    }
}

