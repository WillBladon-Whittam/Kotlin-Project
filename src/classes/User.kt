package classes

open class User (name:String){

    open fun printMenu() : String {
        return "1. Search for room by building\n2. Search for room by OS\n3. Book a computer\n4. View Bookings\n5. Cancel Bookings\n6. Exit"
    }

    open fun optionMenu(choice:Int) : Boolean {
        when (choice) {
            1 -> println("searchRoomByBuilding")
            2 -> println("searchRoomByOS")
            3 -> println("bookComputer")
            4 -> println("viewBooking")
            5 -> println("cancelBooking")
            6 -> return false
        }
        return true
    }
}