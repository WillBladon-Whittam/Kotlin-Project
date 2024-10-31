//Author - Charles Clark
package classes

class Building(val name:String) {
    //A list of all the rooms in a building
    private var rooms = mutableListOf<Room>()

    //Returns a list of all the rooms in a building
    fun getRooms(): List<Room> {
        return rooms
    }

    //Adds a room to the rooms list
    fun addRoom(room: Room) {
        rooms.add(room)
    }
}