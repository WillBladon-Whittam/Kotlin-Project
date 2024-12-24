package core.services

import core.models.Computer
import core.models.University
import core.models.ComputerBooking
import core.models.User
import data.dao.BookingDao
import data.dao.UserDao

class BookingService(val roomService: RoomService, val userService: UserService) {
    val bookingDao: BookingDao = BookingDao()

    init {
        val bookings = bookingDao.getBookings(userService.accounts)
        val computers = roomService.university.getComputer()
        for (booking in bookings) {
            for (computer in computers) {
                if (computer.globalId == booking.computerId) {
                    computer.addBooking(booking)
                }
            }
        }
    }

    fun addBooking(computer: Computer, booking: ComputerBooking) {
        bookingDao.insertBooking(booking)
        computer.addBooking(booking)
    }

    fun cancelBooking(computer: Computer, booking: ComputerBooking) {
        bookingDao.deleteBooking(booking)
        computer.deleteBooking(booking)
    }
}