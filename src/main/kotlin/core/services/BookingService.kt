package core.services

import core.models.University
import core.models.ComputerBooking
import data.dao.BookingDao
import data.dao.UserDao

class BookingService(private val roomService: RoomService, private val userService: UserService){

    init {
        roomService.sparkBuildingRoom1.getComputers()[0].addBooking(ComputerBooking
            (roomService.sparkBuildingRoom1.getComputers()[0].globalId, "Monday", "9am-11am", userService.john))
        roomService.sparkBuildingRoom1.getComputers()[0].addBooking(ComputerBooking(
            roomService.sparkBuildingRoom1.getComputers()[0].globalId, "Monday", "11am-1pm", userService.bob))
        roomService.sparkBuildingRoom1.getComputers()[2].addBooking(ComputerBooking
            (roomService.sparkBuildingRoom1.getComputers()[2].globalId, "Monday", "9am-11am", userService.john))
        roomService.sparkBuildingRoom1.getComputers()[3].addBooking(ComputerBooking(
            roomService.sparkBuildingRoom1.getComputers()[3].globalId, "Monday", "11am-1pm", userService.bob))
    }
}