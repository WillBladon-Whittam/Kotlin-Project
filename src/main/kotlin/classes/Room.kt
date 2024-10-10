/**
 * @author  William Bladon-Whittam
 */

package src.main.kotlin.classes

abstract class Room(val roomNumber: Int, val building: Building) {
    /**
     * Abstract room class to store the computers of the room
     *
     * Brief state:
     * "All computers in a given lab have the same operating system  (Windows, Linux or Mac)."
     *  So use Room as the abstract class - then use specific OS Room subclasses.
     *
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

    override fun toString(): String {
        return "Room $roomNumber in building ${building.name} OS: ${getOperatingSystem()} Computers: $computers"
    }
}

class WindowsRoom(roomNumber: Int, building: Building) : Room(roomNumber, building) {
    /**
     * Room with Windows Operating systems on the Computers
     */
    override fun getOperatingSystem(): String {
        return "Windows"
    }
}

class MacRoom(roomNumber: Int, building: Building) : Room(roomNumber, building) {
    /**
     * Room with MAC Operating systems on the Computers
     */
    override fun getOperatingSystem(): String {
        return "Mac"
    }
}

class LinuxRoom(roomNumber: Int, building: Building) : Room(roomNumber, building) {
    /**
     * Room with Linux Operating systems on the Computers
     */
    override fun getOperatingSystem(): String {
        return "Linux"
    }
}

