package data.dao

import core.models.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

import data.schema.UsersTable

import data.schema.BookingsDaoInterface
import data.schema.BookingsTable
import data.schema.BookingsTable.text
import data.schema.RoomsTable
import org.jetbrains.exposed.sql.*
import java.awt.print.Book

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

    override fun getBookings(accounts: UserAccounts): List<ComputerBooking> {
        var bookingsList = listOf<ComputerBooking>()
        transaction {
            val results = BookingsTable.selectAll()
            bookingsList = results.map { row ->
                ComputerBooking(
                    row[BookingsTable.computerId],
                    row[BookingsTable.day],
                    row[BookingsTable.timeSlot],
                    accounts.getUsersByName(row[BookingsTable.studentName])!!
                )
            }
        }
        return bookingsList
    }

    override fun insertBooking(booking: ComputerBooking): Int {
        var bookingId = 0
        transaction {
            bookingId = BookingsTable.insert {
                it[computerId] = booking.computerId
                it[day] = booking.day
                it[timeSlot] = booking.timeSlot
                it[studentName] = booking.student.name
            }[BookingsTable.id]
        }
        return bookingId
    }

    override fun deleteBooking(booking: ComputerBooking): Int {
        return transaction {
            BookingsTable.deleteWhere { computerId eq booking.computerId }
        }
    }
}