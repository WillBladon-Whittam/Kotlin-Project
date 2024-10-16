package classes

open class User (){

    open fun printMenu() : String {
        return "1. Search for room by building\n2. Search for room by OS\n3. Book a computer\n4. View Bookings\n5. Cancel Bookings\n6. Exit"
    }

    open fun optionMenu(choice:Int) : Boolean {
        var running = true
        when (choice) {
            1 -> searchRoomByBuilding()
            2 -> searchRoomByOS()
            3 -> bookComputer()
            4 -> viewBooking()
            5 -> cancelBooking()
            6 -> running = false
            else -> println("Invalid")
        }
        return running
    }

    private fun searchRoomByBuilding() { println("searchRoomByBuilding") }
    private fun searchRoomByOS() { println("searchRoomByOS") }
    private fun bookComputer() { println("bookComputer") }
    private fun viewBooking() { println("viewBooking") }
    private fun cancelBooking() { println("cancelBooking") }
    private fun exit() { println("exit") }
}