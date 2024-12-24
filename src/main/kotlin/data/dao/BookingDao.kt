package data.dao

import org.jetbrains.exposed.sql.transactions.transaction

import data.schema.UsersTable
import core.models.ComputerBooking

import data.schema.BookingsDaoInterface
import data.schema.BookingsTable
import org.jetbrains.exposed.sql.*

class BookingDao : BookingsDaoInterface {
    /**
     * Booking Data Access Object
     *
     * WILL NOTE ->
     *
     * Project Structure Plan ->
     *
     */
    init {
        transaction {
            SchemaUtils.create(BookingsTable)
        }
    }

    override fun insertBooking(booking: ComputerBooking): Int {
        return -1
    }
}