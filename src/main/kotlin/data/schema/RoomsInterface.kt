package data.schema

import core.models.Room
import core.models.University
import org.jetbrains.exposed.sql.Table

object RoomsTable : Table("rooms") {
    val id  = integer("id").autoIncrement()
    val roomNumber = integer("roomNumber")
    val building = text("building")
    val operatingSystem = text("operatingSystem")
    val timeSlots = text("timeSlots")
    val daysOfTheWeek = text("daysOfTheWeek")
    val numOfComputers = integer("numOfComputers")
    override val primaryKey = PrimaryKey(id)
}

interface RoomsDaoInterface {
    fun getRooms(university: University) : List<Room>
    fun insertRoom(room: Room) : Boolean
}