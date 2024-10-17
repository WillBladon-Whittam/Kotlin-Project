package userClasses

import classes.Room
import classes.University

class Admin(name: String) : User(name) {

    override fun menu(): String{
        return "1. All Bookings\n2. Add Room\n3. Logout"
    }

    fun allBookings(building: Int, room: Int, day:String, university: University) : MutableList<MutableList<String>>? {
        return try {
            university.getBuildings()[building].getRooms()[room].getBookings(day)
        } catch (e: Exception) {
            null
        }
    }

    fun addRoom(university: University, index: Int, name: String, computers: Int, timeSlots: List<String>) {
        university.getBuildings()[index].addRoom(Room(name, computers, timeSlots))
    }
}