package data.schema

import core.models.ComputerBooking
import org.jetbrains.exposed.sql.Table

object BookingsTable : Table("bookings") {
    val id  = integer("id").autoIncrement()
    val computerId  = text("computerId")
    val day = text("day")
    val timeSlot = text("timeSlot")
    val studentName = text("studentName")
    override val primaryKey = PrimaryKey(id)
}

interface BookingsDaoInterface {
    fun insertBooking(booking: ComputerBooking) : Int
}