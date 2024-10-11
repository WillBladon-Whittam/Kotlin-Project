package session

import classes.User

class UserSession {

    fun mainLoop(user: User) {
        var running = true
        while (running) {
            println(user.printMenu())
            val choice = readlnOrNull()?.toIntOrNull() ?: 0
            when (user.optionMenu(choice)){
                "searchRoomByBuilding" -> this.searchRoomByBuilding()
                "searchRoomByOS" -> this.searchRoomByOS()
                "bookComputer" -> this.bookComputer()
                "viewBooking" -> this.viewBooking()
                "cancelBooking" -> this.cancelBooking()
                "displayAllBookings" -> this.displayAllBookings()
                "addRoom" -> this.addRoom()
                "Invalid" -> println("Invalid Input")
                "Exit" -> running = false
            }
        }
    }

    private fun searchRoomByBuilding() { println("searchRoomByBuilding") }
    private fun searchRoomByOS() { println("searchRoomByOS") }
    private fun bookComputer() { println("bookComputer") }
    private fun viewBooking() { println("viewBooking") }
    private fun cancelBooking() { println("cancelBooking") }
    private fun displayAllBookings() { println("displayAllBookings") }
    private fun addRoom() { println("addRoom") }
}