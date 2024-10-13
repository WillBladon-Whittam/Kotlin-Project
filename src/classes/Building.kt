////////////////////////////// Building.kt ////////////////////////////////////
///////////////////////// Author: Edward Kirr ////////////////////////////////
///////// Description: Simple building class to create methods to ///////////
//////////////// edit room details and delete in the list //////////////////
///////////////////////////////////////////////////////////////////////////

package src.classes

class Building(val name: String) {
    // Attribute: Name - this specifies the building's name
    // Building class, responsible for creating the rooms list,
    // editing the room's details and delete a room from the list
    private var rooms = mutableListOf<Room>()
    // Use of aggregation to store rooms in the mutable list

    fun addRoom(room: Room) : String {
        // Basic method to add a room to the rooms list
        rooms.add(room)
        return "Room Added Successfully"
    }

    fun editRoomDetails(index: Int, newType: Int): String {
        // Parameters: index to specify the room, newType to change the type to a different type
        // Responsible for changing the room type by changing what subclass it is.
        // Returns: A string message to notify whether it was successful or not

        // 1 = Windows Room, 2 = Linux Room, 3 = Mac Room
        println("Total rooms: ${rooms.count()}")
        println("Given index: $index")

        if (index < 1 || index > rooms.count()) {
            return "Please pick a room from within the range \n"
        } else {
            val room = rooms[index - 1]
            val newRoom = when {
                newType == 1 && room is LinuxRoom || room is MacRoom -> WindowsRoom(room.roomNumber)
                newType == 2 && room is WindowsRoom || room is MacRoom -> LinuxRoom(room.roomNumber)
                newType == 3 && room is WindowsRoom || room is LinuxRoom -> MacRoom(room.roomNumber)
                else -> return "Invalid OS or Room is already of that OS type \n"
            }
            rooms[index - 1] = newRoom
            return "Room details updated for ${room.roomNumber} \n"
        }
    }

    fun deleteRoom(index: Int) : String {
        // Parameter: index - specifies the room you want to delete
        // Responsible for deleting the specified room from the list.
        // Returns: A string message to notify whether delete was successful or not
        if (index < 1 || index > rooms.count()) {
            return "WARNING: Please pick a room from within the list \n"
        } else {
            val room = rooms[index - 1]
            rooms.remove(room)
            return "${room.roomNumber} has been removed \n"
        }
    }

    override fun toString(): String {
        // Overrides the method to print an itemised list of all the rooms and its types
        // Returns: A string list of rooms
        val roomList = StringBuilder()
        for ((index, room) in rooms.withIndex()) {
            val roomType = when (room) {
                is WindowsRoom -> "WindowsRoom"
                is LinuxRoom -> "LinuxRoom"
                else -> "MacRoom"
            }
            roomList.append("${index + 1}. Room Number: ${room.roomNumber} Room Type: $roomType \n")
        }
        return roomList.toString()
    }

    fun isEmpty(): Boolean {
        // Checks to see if list is empty
        // Returns: True if the list is empty or false if it has items
        return rooms.isEmpty()
    }


}