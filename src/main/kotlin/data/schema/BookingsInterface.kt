package data.schema

import core.models.ComputerBooking
import core.models.University
import core.models.UserAccounts
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
    fun getBookings(accounts: UserAccounts) : List<ComputerBooking>
    fun insertBooking(booking: ComputerBooking) : Int
    fun deleteBooking(booking: ComputerBooking) : Int
}