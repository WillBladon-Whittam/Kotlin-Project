package classes

class University(val name: String) {
    private var buildings = mutableListOf<Building>(Building("Spark"),Building("Herbert Collins"))

    fun getBuildings(): List<Building> {
        return buildings
    }
}

class Building(val name:String) {
    private var rooms = mutableListOf<Room>()

    fun getRooms(): List<Room> {
        return rooms
    }

    fun addRoom(room: Room) {
        rooms.add(room)
    }
}

class Room(val name:String) {}