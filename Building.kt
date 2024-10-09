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

    fun createWindowsRoom(roomNumber: Int): WindowsRoom {
        val room = WindowsRoom(roomNumber, this)
        this.addRoom(room)
        return room
    }

    fun createMacRoom(roomNumber: Int): MacRoom {
        val room = MacRoom(roomNumber, this)
        this.addRoom(room)
        return room
    }

    fun createLinuxRoom(roomNumber: Int): LinuxRoom {
        val room = LinuxRoom(roomNumber, this)
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