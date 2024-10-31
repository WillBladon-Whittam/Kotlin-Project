//Author - Charles Clark
package session
import classes.*

class User (val name: String, val password: String, val admin: Boolean) {
    //Returns the menu based on if the user is an admin or normal user
    fun menu(): String {
        return if (admin){
            "1. All Bookings\n2. Add Room\n3. Logout"
        } else {
            "--TEMP USER MENU--\n1. Display Rooms\n2. Exit"
        }
    }

    //Returns a 2D list of the bookings for that room on a selected day
    fun allBookings(room: Room, day:String) : MutableList<MutableList<String>>? {
        return try {
            room.getBookings(day)
        } catch (e: Exception) {
            null
        }
    }

    //Adds a new room to a selected building
    fun addRoom(building: Building, name: String, computers: Int, timeSlots: List<String>) {
        building.addRoom(Room(name, computers, timeSlots))
    }
}